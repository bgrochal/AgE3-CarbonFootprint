package pl.edu.agh.footprint.tree.model.parameter;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class represents the &lt;Parameter&gt; tag, which value may be configured (e.g. by the mutation operator).
 *
 * @author Bartłomiej Grochal
 */
public abstract class ConfigurableParameter extends Parameter {

	Random randomGenerator = ThreadLocalRandom.current();


	ConfigurableParameter(String name) {
		super(name, true);
	}


	public abstract void setRandomValue();

}
