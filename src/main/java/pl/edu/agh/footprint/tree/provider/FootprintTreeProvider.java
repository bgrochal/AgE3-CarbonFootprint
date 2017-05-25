package pl.edu.agh.footprint.tree.provider;

import com.google.common.base.Preconditions;
import org.dom4j.Document;
import org.dom4j.Node;
import pl.edu.agh.age.compute.stream.problem.ProblemDefinition;
import pl.edu.agh.footprint.age.problem.CarbonFootprintProblem;
import pl.edu.agh.footprint.tree.builder.ActionBuilder;
import pl.edu.agh.footprint.tree.builder.FootprintTreeBuilder;
import pl.edu.agh.footprint.tree.factory.ParameterFactory;
import pl.edu.agh.footprint.tree.methods.container.MethodsContainer;
import pl.edu.agh.footprint.tree.model.FootprintTree;
import pl.edu.agh.footprint.tree.parser.FileReader;
import pl.edu.agh.footprint.tree.parser.XMLFileParser;
import pl.edu.agh.footprint.tree.util.ReflectionUtil;

import java.util.List;

import static pl.edu.agh.footprint.tree.provider.FootprintTreeTag.*;

/**
 * <p>This class is responsible for creating an instance of {@link FootprintTree} based on XML file represented by the
 * {@link Document} instance.</p>
 *
 * <p>Note: This class is defined as a singleton bean, as well as its dependencies (injected via constructor).</p>
 *
 * @author Bartłomiej Grochal
 */
public class FootprintTreeProvider {

	private final ProblemDefinition problemDefinition;
	private final MethodsContainer methodsContainer;
	private final ParameterFactory parameterFactory;


	public FootprintTreeProvider(ProblemDefinition problemDefinition, MethodsContainer methodsContainer,
								 ParameterFactory parameterFactory) {
		Preconditions.checkState(problemDefinition instanceof CarbonFootprintProblem);
		this.problemDefinition = problemDefinition;
		this.methodsContainer = methodsContainer;
		this.parameterFactory = parameterFactory;
	}


	/**
	 * Returns an instance of the {@link FootprintTree} read from the XML file supplied with given {@link
	 * CarbonFootprintProblem#filePath}.
	 */
	public FootprintTree getFootprintTree() {
		final String filePath = ((CarbonFootprintProblem) problemDefinition).getFilePath();
		Document xmlDocument = XMLFileParser.parseXMLFile(FileReader.readFile(filePath));
		FootprintTreeBuilder treeBuilder = visitTree(xmlDocument);

		return treeBuilder.build();
	}


	/**
	 * Returns an instance of {@link FootprintTreeBuilder} containing a mapping between the XML tree defined by the
	 * {@code xmlDocument} and the structure of Java objects.
	 */
	private FootprintTreeBuilder visitTree(Document xmlDocument) {
		FootprintTreeBuilder treeBuilder = visitTarget(xmlDocument);
		visitActions(xmlDocument, treeBuilder);
		return treeBuilder;
	}

	/**
	 * Processes the &lt;Target&gt; tag defined in the {@code xmlDocument}.
	 */
	private FootprintTreeBuilder visitTarget(Document xmlDocument) {
		Node targetNode = xmlDocument.selectSingleNode(TARGET.getTagPath());
		return new FootprintTreeBuilder(targetNode.getText());
	}

	/**
	 * Processes all &lt;Action&gt; tags defined in the {@code xmlDocument}.
	 */
	private void visitActions(Document xmlDocument, FootprintTreeBuilder treeBuilder) {
		List<Node> actionNodes = xmlDocument.selectNodes(ACTION.getTagPath());
		actionNodes.forEach(node -> {
			ActionBuilder actionBuilder = new ActionBuilder(
				node.valueOf(ACTION_TITLE.getTagPath()),
				node.valueOf(ACTION_TYPE.getTagPath()),
				ReflectionUtil.getMethodByName(node.valueOf(ACTION_FOOTPRINT_METHOD.getTagPath()), methodsContainer));

			node.selectNodes(ACTION_PARAMETER.getTagPath()).forEach(parameter ->
				actionBuilder.addParameter(parameterFactory.createParameter(parameter)));

			node.selectNodes(ACTION_FOOTPRINT.getTagPath()).forEach(footprintActionType ->
				actionBuilder.addFootprintActionType(footprintActionType.getText()));

			treeBuilder.addAction(actionBuilder.build());
		});
	}

}
