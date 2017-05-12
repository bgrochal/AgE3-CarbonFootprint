package pl.edu.agh.footprint.tree.methods.container;

import pl.edu.agh.footprint.tree.model.parameter.Parameter;

import java.util.Collections;
import java.util.List;

/**
 * This class defines the container for methods defined for the Test Problem in the TestProblem.xml file.
 *
 * @author Bartłomiej Grochal
 */
@SuppressWarnings("unused")
public class TestProblemMethodsContainer implements MethodsContainer {

	public List<ContainerMethodAttribute> calculateAction1(List<ContainerMethodAttribute> footprintValues,
														   List<Parameter> parameters) {
		return Collections.singletonList(new ContainerMethodAttribute("result", 0.0));
	}

	public List<ContainerMethodAttribute> calculateAction2(List<ContainerMethodAttribute> footprintValues,
														   List<Parameter> parameters) {
		return Collections.emptyList();
	}

	public List<ContainerMethodAttribute> calculateAction3(List<ContainerMethodAttribute> footprintValues,
														   List<Parameter> parameters) {
		return Collections.emptyList();
	}

}
