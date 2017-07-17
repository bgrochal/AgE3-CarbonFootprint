package pl.edu.agh.footprint.age.solution;

import com.google.common.base.Preconditions;
import pl.edu.agh.footprint.age.evaluator.CarbonFootprintEvaluator;
import pl.edu.agh.footprint.age.util.ObjectClonerService;
import pl.edu.agh.footprint.age.util.TreeUtil;
import pl.edu.agh.footprint.tree.model.Action;
import pl.edu.agh.footprint.tree.model.FootprintTree;
import pl.edu.agh.footprint.tree.model.parameter.ConfigurableParameter;
import pl.edu.agh.footprint.tree.model.parameter.Parameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class defines a factory method for creating individuals corresponding to solutions of the Carbon Footprint
 * problem. This factory creates solutions randomly, i.e.:
 * <ul>
 *     <li>if there are multiple {@link Action actions} with the same {@link Action#type type}, the solution contains
 *     exactly one, randomly chosen {@link Action action} of this {@link Action#type type};</li>
 *     <li>if an {@link Action action} contains at least one {@link ConfigurableParameter configurable parameter}, then
 *     this parameter's {@link Parameter#value value} is randomly chosen out of available values.</li>
 * </ul>
 *
 * @author Bartłomiej Grochal
 */
public class CarbonFootprintSolutionFactory {

	private final FootprintTree footprintTree;
	private final CarbonFootprintEvaluator evaluator;
	private final ObjectClonerService objectClonerService;

	private final Random randomGenerator;
	private final Map<String, List<Action>> actionsByType;


	/**
	 * Instantiates a new factory method creating solutions of the CARBON FOOTPRINT problem.
	 *
	 * @param footprintTree       representation of the footprint tree defined as an input of the algorithm.
	 * @param evaluator           mechanism for calculating fitness value of a solution.
	 * @param objectClonerService service used for cloning (a.k.a. copying) other objects.
	 */
	public CarbonFootprintSolutionFactory(final FootprintTree footprintTree, final CarbonFootprintEvaluator evaluator,
										  final ObjectClonerService objectClonerService) {
		this.footprintTree = Preconditions.checkNotNull(footprintTree);
		this.evaluator = Preconditions.checkNotNull(evaluator);
		this.objectClonerService = Preconditions.checkNotNull(objectClonerService);

		randomGenerator = ThreadLocalRandom.current();
		actionsByType = TreeUtil.getActionsByType(this.footprintTree.getActions());
	}


	/**
	 * Returns a new {@link CarbonFootprintSolution solution} of the Carbon Footprint problem. Nodes of the {@link
	 * pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTree SolutionTree} are composed of the randomly
	 * selected {@link Action}s defined in the XML footprint tree.
	 */
	public CarbonFootprintSolution create() {
		return create(actionsByType, true);
	}

	/**
	 * <p>Returns a new {@link CarbonFootprintSolution solution} of the Carbon Footprint problem. Nodes of the {@link
	 * pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTree SolutionTree} are composed of the randomly
	 * selected {@link Action}s defined by the {@code actionByType} map.</p>
	 *
	 * <p>The {@code actionsByType} parameter contains a {@code String -> List<Action>} mapping between the all possible
	 * {@link Action#type}s and the {@link List}s containing (not necessarily all possible) {@link Action}s with given
	 * {@link Action#type}.</p>
	 *
	 * <p>Note that this method may return a {@link CarbonFootprintSolution solution}, which {@link
	 * pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTreeNode nodes} are composed only from a subset
	 * of available {@link Action}s of some {@link Action#type}, unlike when invoking the {@link #create()} method
	 * (which always selects {@link Action}s forming a {@link
	 * pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTree Solution Tree} from all available actions
	 * defined in the XML footprint tree).</p>
	 *
	 * @see TreeUtil#getActionsByType(List)
	 */
	public CarbonFootprintSolution create(Map<String, List<Action>> actionsByType, boolean modifyParameters) {
		CarbonFootprintSolution solution =
			new CarbonFootprintSolution(createSolutionTree(actionsByType, modifyParameters));
		return solution.updateFitness(evaluator.evaluate(solution));
	}

	/**
	 * Returns a {@link pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTreeNode root node} of a
	 * newly-generated {@link CarbonFootprintSolution.SolutionTree solution (sub)tree}.
	 *
	 * The {@code createdNodesByType} parameter contains mappings between the types of all nodes created during the
	 * recursive call and these nodes. Keeping this mapping is necessary to prevent from creating the same node (i.e. a
	 * node with given {@link Action#type}) more than once in the tree.
	 */
	public CarbonFootprintSolution.SolutionTreeNode createSolutionTreeNode(String nodeActionType, Map<String, List<Action>> actionsByType,
																		   Map<String, CarbonFootprintSolution.SolutionTreeNode> createdNodesByType, boolean modifyParameters) {
		if (createdNodesByType.containsKey(nodeActionType)) {
			return createdNodesByType.get(nodeActionType);
		}

		List<Action> correspondingActions = actionsByType.get(nodeActionType);
		Action originalAction = correspondingActions.get(randomGenerator.nextInt(correspondingActions.size()));
		Action copiedAction = objectClonerService.deepClone(originalAction);

		if (modifyParameters) {
			copiedAction.getParameters().stream()
				.filter(Parameter::isConfigurable)
				.map(parameter -> (ConfigurableParameter) parameter)
				.forEach(ConfigurableParameter::setRandomValue);
		}

		CarbonFootprintSolution.SolutionTreeNode treeNode = new CarbonFootprintSolution.SolutionTreeNode(copiedAction);
		createdNodesByType.put(nodeActionType, treeNode);

		copiedAction.getFootprintActionTypes().forEach(footprintActionType ->
			treeNode.addChild(createSolutionTreeNode(footprintActionType, actionsByType, createdNodesByType, modifyParameters)));
		return treeNode;
	}


	/**
	 * <p>Returns an instance of the {@link CarbonFootprintSolution.SolutionTree} class being a newly-generated
	 * footprint tree forming the solution of the Carbon Footprint problem.</p>
	 *
	 * <p>Note that all available {@link Action}s of each {@link Action#type} which could form a {@link
	 * pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTreeNode} must be defined by the {@code
	 * actionsByType} map.</p>
	 */
	private CarbonFootprintSolution.SolutionTree createSolutionTree(Map<String, List<Action>> actionsByType,
																	boolean modifyParameters) {
		final CarbonFootprintSolution.SolutionTreeNode rootNode =
			createSolutionTreeNode(footprintTree.getTargetActionType(), actionsByType, new HashMap<>(), modifyParameters);
		return new CarbonFootprintSolution.SolutionTree(rootNode);
	}

}
