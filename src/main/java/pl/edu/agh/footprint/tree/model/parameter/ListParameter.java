package pl.edu.agh.footprint.tree.model.parameter;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>This class represents the {@link ConfigurableParameter}, which <strong>value</strong> may be one of defined by the
 * <strong>values</strong> list. This class represents also the &lt;Parameter&gt; tag with the &lt;List&gt; child.</p>
 *
 * <p>Note: Create instances of this class using dedicated {@link pl.edu.agh.footprint.tree.builder.ListParameterBuilder
 * builder}.</p>
 *
 * @author Bartłomiej Grochal
 */
public class ListParameter extends ConfigurableParameter {

	// Optional attributes.
	private final List<Double> values;


	public ListParameter(String name, double value) {
		super(name, value);
		values = new LinkedList<>();
	}


	public void addValue(double value) {
		values.add(value);
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
