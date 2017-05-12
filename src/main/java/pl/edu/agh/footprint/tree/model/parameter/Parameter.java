package pl.edu.agh.footprint.tree.model.parameter;

/**
 * This class represents the common part of all &lt;Parameter&gt; tags in the XML footprint tree.
 *
 * @author Bartłomiej Grochal
 */
public abstract class Parameter {

	// Obligatory attributes.
	private final String name;
	private final boolean configurable;

	// Optional attributes.
	double value;


	Parameter(String name, boolean configurable) {
		this(name, configurable, Double.NaN);
	}

	Parameter(String name, boolean configurable, double value) {
		this.name = name;
		this.configurable = configurable;

		this.value = value;
	}


	public String getName() {
		return name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public boolean hasValue() {
		return !Double.isNaN(value);
	}

	public boolean isConfigurable() {
		return configurable;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder()
			.append("param :\n\t\t\tname\t: ")
			.append(name)
			.append("\n\t\t\tconfig\t: ")
			.append(configurable);
		if (hasValue()) {
			builder
				.append("\n\t\t\tvalue\t: ")
				.append(value);
		}
		return builder
			.append("\n")
			.toString();
	}

}
