package pl.edu.agh.footprint.age.util;

import com.rits.cloning.Cloner;

/**
 * <p>This is an utility class containing service methods used for cloning (a.k.a. copying) objects of any type.</p>
 *
 * <p>Note: This class is defined as a singleton bean, as well as its dependencies (injected via constructor).</p>
 *
 * @author Bartłomiej Grochal
 */
public class ObjectClonerService {

	private final Cloner objectCloner;


	/**
	 * Instantiates a new service for cloning (a.k.a. copying) objects.
	 *
	 * @param objectCloner a singleton bean handling the objects cloning process, defined by the
	 *                     <a href="https://github.com/kostaskougios/cloning">Apache deep cloning library</a>.
	 */
	public ObjectClonerService(Cloner objectCloner) {
		this.objectCloner = objectCloner;
	}


	/**
	 * Returns a deep clone of the {@code objectToClone} of type {@code T}. A deep clone of the object is a new object,
	 * which attributes are also copied recursively (excluding JDK's immutable classes).
	 */
	public <T> T deepClone(final T objectToClone) {
		return objectCloner.deepClone(objectToClone);
	}

}
