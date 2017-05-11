package pl.edu.agh.footprint.tree.model.parameter;

/**
 * This class represents the &lt;Parameter&gt; tag, which value may be configured (e.g. by the mutation operator).
 *
 * @author Bartłomiej Grochal
 */
public abstract class ConfigurableParameter extends Parameter {

	public ConfigurableParameter(String name, double value) {
		super(name, value, true);
	}

}
