package pl.edu.agh.footprint.tree.model.parameter;

/**
 * This class represents the common part of all &lt;Parameter&gt; tags in the XML footprint tree.
 *
 * @author Bartłomiej Grochal
 */
public abstract class Parameter {

	// Obligatory attributes.
	private final String name;
	private final double value;
	private final boolean configurable;


	public Parameter(String name, double value, boolean configurable) {
		this.name = name;
		this.value = value;
		this.configurable = configurable;
	}


	@Override
	public String toString() {
		return new StringBuilder()
			.append("param :\n\t\t\tname\t: ")
			.append(name)
			.append("\n\t\t\tvalue\t: ")
			.append(value)
			.append("\n\t\t\tconfig\t: ")
			.append(configurable)
			.append("\n")
			.toString();
	}

}
