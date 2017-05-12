package pl.edu.agh.footprint.tree.model.parameter;

/**
 * This class represents the {@link ConfigurableParameter}, which <strong>value</strong> may be any floating-point
 * number such that: <strong>min</strong> &le; <strong>value</strong> &le; <strong>max</strong>. This class represents
 * also the &lt;Parameter&gt; tag with the &lt;Range&gt; child.
 *
 * @author Bartłomiej Grochal
 */
public class RangeParameter extends ConfigurableParameter {

	// Obligatory attributes.
	private final double min;
	private final double max;


	public RangeParameter(String name, double min, double max) {
		super(name);

		this.min = min;
		this.max = max;
	}


	@Override
	public void setRandomValue() {
		value = min + (max - min) * randomGenerator.nextDouble();
	}

	@Override
	public String toString() {
		return new StringBuilder()
			.append(super.toString())
			.append("\t\t\ttype\t: Range\n")
			.append("\t\t\t\tMin : ")
			.append(min)
			.append("\n\t\t\t\tMax : ")
			.append(max)
			.append("\n")
			.toString();
	}

}
