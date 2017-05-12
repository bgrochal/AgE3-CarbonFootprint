package pl.edu.agh.footprint.tree.model.parameter;

/**
 * This class represents the &lt;Parameter&gt; tag, which value is constant and may not be configured.
 *
 * @author Bartłomiej Grochal
 */
public class NonConfigurableParameter extends Parameter {

	public NonConfigurableParameter(String name, double value) {
		super(name, false, value);
	}

}
