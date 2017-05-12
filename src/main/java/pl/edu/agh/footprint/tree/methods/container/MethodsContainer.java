package pl.edu.agh.footprint.tree.methods.container;

/**
 * <p>This class defines the marker interface used for all classes, which act as containers for methods defined in the
 * XML footprint tree.</p>
 *
 * <p>Note: Every method defined in a specific {@link MethodsContainer container} should be declared according to the
 * following contract:
 * <ul>
 *     <li>its return value should be {@code List<ContainerMethodAttribute>};</li>
 *     <li>its first argument of type {@code List<ContainerMethodAttribute>} should be a list of values returned by
 *     methods belonging to the node's direct footprint actions;</li>
 *     <li>its second argument of type {@code List<Parameter>} should be a list of the node's parameters.
 *     </li>
 * </ul>
 * </p>
 *
 * <p>Note: Container classes should be defined as a singleton bean.</p>
 *
 * @see ContainerMethodAttribute
 * @see pl.edu.agh.footprint.tree.model.parameter.Parameter
 *
 * @author Bartłomiej Grochal
 */
public interface MethodsContainer {
}
