package pl.edu.agh.footprint.tree.model.parameter;

import com.google.common.base.Preconditions;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>This class represents the {@link ConfigurableParameter}, which <strong>value</strong> may be one of defined by the
 * <strong>values</strong> list. This class represents also the &lt;Parameter&gt; tag with the &lt;List&gt; child.</p>
 *
 * <p>Note: Create instances of this class using dedicated {@link pl.edu.agh.footprint.tree.builder.ListParameterBuilder
 * builder}. Note also that it is illegal to create an instance of the {@link ListParameter} with the only one value
 * present in the {@link #values} list (in this case, use the {@link NonConfigurableParameter} instead).</p>
 *
 * @author Bartłomiej Grochal
 */
public class ListParameter extends ConfigurableParameter {

	// Optional attributes.
	private final List<Double> values;


	public ListParameter(String name) {
		super(name);
		values = new LinkedList<>();
	}


	public void addValue(double value) {
		values.add(value);
	}

	@Override
	public void setRandomValue() {
		Preconditions.checkState(values.size() > 1);

		double newValue;
		do {
			newValue = values.get(randomGenerator.nextInt(values.size()));
		} while (newValue == value);

		value = newValue;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder()
			.append(super.toString())
			.append("\t\t\ttype\t: List\n");
		values.forEach(value -> builder.append("\t\t\t\tvalue\t: ").append(value).append("\n"));

		return builder.toString();
	}

}
