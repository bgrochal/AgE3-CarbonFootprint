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


	/**
	 * <p>Sets a random {@link #value} to this parameter.</p>
	 *
	 * <p>A newly-generated value will always be different than the original value (see general notes for child
	 * classes specifying proper contracts).</p>
	 */
	public abstract void setRandomValue();

}
