package pl.edu.agh.footprint.tree.util;

import pl.edu.agh.footprint.tree.methods.container.ContainerMethodAttribute;
import pl.edu.agh.footprint.tree.model.parameter.Parameter;

import java.util.List;

/**
 * This class contains utility methods used for getting values from objects passed as arguments of functions defined in
 * the {@link pl.edu.agh.footprint.tree.methods.container.MethodsContainer MethodsContainer}
 *
 * @author Bartłomiej Grochal
 */
public class MethodsContainerUtil {

	private MethodsContainerUtil() {
	}


	/**
	 * Returns a value of the {@link ContainerMethodAttribute attribute} defined in the {@code attributes} list and
	 * identified by the {@code attributeKey} complying with the {@link ContainerMethodAttribute#key}.
	 *
	 * @throws IllegalArgumentException when the {@code attributes} list does not contain any {@link
	 *                                  ContainerMethodAttribute attribute} with the {@link ContainerMethodAttribute#key}
	 *                                  corresponding with given {@code attributeKey}.
	 */
	public static double getAttributeValueByKey(List<ContainerMethodAttribute> attributes, String attributeKey) throws IllegalArgumentException {
		return attributes.stream()
			.filter(attribute -> attribute.getKey().equals(attributeKey))
			.mapToDouble(ContainerMethodAttribute::getValue)
			.findAny()
			.orElseThrow(IllegalArgumentException::new);
	}

	/**
	 * Returns a value of the {@link Parameter} defined in the {@code parameters} list and identified by the {@code
	 * parameterKey} complying with the {@link Parameter#name}.
	 *
	 * @throws IllegalArgumentException when the {@code parameters} list doe not containt any {@link Parameter} with the
	 *                                  {@link Parameter#name} corresponding with given {@code parameterKey}.
	 */
	public static double getParameterValueByName(List<Parameter> parameters, String parameterKey) throws IllegalArgumentException {
		return parameters.stream()
			.filter(parameter -> parameter.getName().equals(parameterKey))
			.mapToDouble(Parameter::getValue)
			.findAny()
			.orElseThrow(IllegalArgumentException::new);
	}

}
