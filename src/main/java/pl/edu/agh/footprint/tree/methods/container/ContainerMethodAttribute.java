package pl.edu.agh.footprint.tree.methods.container;

import java.util.AbstractMap;

/**
 * This class defines the type for attributes and returning values of methods defined in a {@link MethodsContainer}.
 * This type simply consists of a ({@link String key}, {@link Double value}) pair.
 *
 * @author Bartłomiej Grochal
 */
public class ContainerMethodAttribute extends AbstractMap.SimpleEntry<String, Double> {

	public ContainerMethodAttribute(String key, Double value) {
		super(key, value);
	}

}
