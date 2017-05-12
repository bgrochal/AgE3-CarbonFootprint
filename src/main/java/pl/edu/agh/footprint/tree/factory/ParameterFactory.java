package pl.edu.agh.footprint.tree.factory;

import org.dom4j.Node;
import pl.edu.agh.footprint.tree.builder.ListParameterBuilder;
import pl.edu.agh.footprint.tree.model.parameter.NonConfigurableParameter;
import pl.edu.agh.footprint.tree.model.parameter.Parameter;
import pl.edu.agh.footprint.tree.model.parameter.RangeParameter;
import pl.edu.agh.footprint.util.StringValueParser;

import java.util.List;

import static pl.edu.agh.footprint.tree.provider.FootprintTreeTag.*;

/**
 * <p>This class defines the abstract factory method for creating objects of proper subclasses of the {@link Parameter}
 * class.</p>
 *
 * <p>Note: This class is defined as a singleton bean.</p>
 *
 * @author Bartłomiej Grochal
 */
public class ParameterFactory {

	/**
	 * Returns an instance of a proper subclass of the {@link Parameter} class corresponding with the {@code
	 * parameterNode}'s type (e.g. if the {@code parameterNode} has a &lt;List&gt; child, then an instance of the {@link
	 * pl.edu.agh.footprint.tree.model.parameter.ListParameter ListParameter} class is returned).
	 */
	public Parameter createParameter(Node parameterNode) {
		String parameterType = parameterNode.valueOf(ACTION_PARAMETER_TYPE.getTagPath());
		if (parameterType.length() == 0) {
			return new NonConfigurableParameter(getParameterName(parameterNode),
				StringValueParser.parseDouble(parameterNode.valueOf(ACTION_PARAMETER_VALUE.getTagPath())));
		}

		return ConfigurableParameterType.fromString(parameterType).getParameter(parameterNode);
	}


	/**
	 * <p>This is a utility method returning value of the &lt;Parameter name="..." ...&gt; attribute.</p>
	 *
	 * <p>Note: This method is static because it is used by the {@link ConfigurableParameterType enum} types.</p>
	 */
	private static String getParameterName(Node parameterNode) {
		return parameterNode.valueOf(ACTION_PARAMETER_NAME.getTagPath());
	}


	/**
	 * This enum defines all possible subtypes of the node represented by the
	 * {@link pl.edu.agh.footprint.tree.model.parameter.ConfigurableParameter ConfigurableParameter} class.
	 */
	@SuppressWarnings("unused")
	private enum ConfigurableParameterType {

		/**
		 * Defines a type of node corresponding with the {@link pl.edu.agh.footprint.tree.model.parameter.ListParameter
		 * ListParameter} class.
		 */
		LIST("list") {
			@Override
			public Parameter getParameter(Node parameterNode) {
				ListParameterBuilder listParameterBuilder =
					new ListParameterBuilder(getParameterName(parameterNode));

				List<Node> listParameters = parameterNode.selectNodes(ACTION_LIST_PARAMETER.getTagPath());
				listParameters.forEach(value ->
					listParameterBuilder.addValue(StringValueParser.parseDouble(value.getText())));

				return listParameterBuilder.build();
			}

		},
		/**
		 * Defines a type of node corresponding with the {@link RangeParameter} class.
		 */
		RANGE("range") {
			@Override
			public Parameter getParameter(Node parameterNode) {
				return new RangeParameter(getParameterName(parameterNode),
					StringValueParser.parseDouble(parameterNode.valueOf(ACTION_RANGE_PARAMETER_MIN.getTagPath())),
					StringValueParser.parseDouble(parameterNode.valueOf(ACTION_RANGE_PARAMETER_MAX.getTagPath())));
			}

		};


		/**
		 * Contains a possible value of the &lt;Parameter type="..." ...&gt; attribute.
		 */
		private String type;

		ConfigurableParameterType(String type) {
			this.type = type;
		}


		/**
		 * Returns an instance of a specific subclass of the {@link Parameter} class corresponding with the {@link
		 * #type}.
		 */
		public abstract Parameter getParameter(Node parameterNode);

		public String getType() {
			return type;
		}

		/**
		 * Returns an instance of the {@link ConfigurableParameterType}, which {@link #type} corresponds with the {@code
		 * requestedType}. The {@code requestedType} is a value of the &lt;Parameter type="..." ...&gt; attribute
		 * read from the XML footprint tree.
		 *
		 * @throws IllegalArgumentException when the {@code requestedType} does not conform with any of defined {@link
		 * 									ConfigurableParameterType#type parameter type}.
		 */
		public static ConfigurableParameterType fromString(String requestedType) throws IllegalArgumentException {
			for (ConfigurableParameterType parameterType : ConfigurableParameterType.values()) {
				if (parameterType.getType().equalsIgnoreCase(requestedType)) {
					return parameterType;
				}
			}

			throw new IllegalArgumentException("Unsupported parameter type: " + requestedType + ".");
		}

	}

}
