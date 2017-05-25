package pl.edu.agh.footprint.tree.util;

import pl.edu.agh.footprint.tree.methods.container.MethodsContainer;

import java.lang.reflect.Method;
import java.util.List;

/**
 * This class contains utility methods used for invoking functions defined in the {@link MethodsContainer} by
 * reflection.
 *
 * @author Bartłomiej Grochal
 */
public class ReflectionUtil {

	private ReflectionUtil() {
	}


	/**
	 * Returns the {@link Method} instance defined in the {@link MethodsContainer methodsContainer} and corresponding to
	 * given {@code methodName}.
	 *
	 * @throws IllegalArgumentException when given method name is not defined in given methods container.
	 */
	public static Method getMethodByName(String methodName, MethodsContainer methodsContainer) throws IllegalArgumentException {
		try {
			return methodsContainer.getClass().getMethod(methodName, List.class, List.class);
		} catch (NoSuchMethodException exc) {
			throw new IllegalArgumentException("Unsupported method name: " + methodName + ".");
		}
	}

}
