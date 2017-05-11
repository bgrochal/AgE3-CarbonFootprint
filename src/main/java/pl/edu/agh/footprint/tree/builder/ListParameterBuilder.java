package pl.edu.agh.footprint.tree.builder;

import pl.edu.agh.footprint.tree.model.parameter.ListParameter;

/**
 * This class defines the builder abstraction for the {@link ListParameter} model class.
 *
 * @author Bartłomiej Grochal
 */
public class ListParameterBuilder {

	private final ListParameter listParameter;


	public ListParameterBuilder(String name, double value) {
		listParameter = new ListParameter(name, value);
	}


	public ListParameterBuilder addValue(double value) {
		listParameter.addValue(value);
		return this;
	}

	public ListParameter build() {
		return listParameter;
	}

}
