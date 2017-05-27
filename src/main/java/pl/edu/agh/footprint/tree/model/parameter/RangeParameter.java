package pl.edu.agh.footprint.tree.model.parameter;

import com.google.common.base.Preconditions;

/**
 * <p>This class represents the {@link ConfigurableParameter}, which <strong>value</strong> may be any floating-point
 * number such that: <strong>min</strong> &le; <strong>value</strong> &le; <strong>max</strong>. This class represents
 * also the &lt;Parameter&gt; tag with the &lt;Range&gt; child.</p>
 *
 * <p>Note that the following condition must be fulfilled: <strong>min < max</strong>.</p>
 *
 * @author Bartłomiej Grochal
 */
public class RangeParameter extends ConfigurableParameter {

	// Obligatory attributes.
	private final double min;
	private final double max;


	public RangeParameter(String name, double min, double max) {
		super(name);

		Preconditions.checkState(max > min);
		this.min = min;
		this.max = max;
	}


	@Override
	public void setRandomValue() {
		double newValue;
		do {
			newValue = min + (max - min) * randomGenerator.nextDouble();
		} while (newValue == value);

		value = newValue;
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
