package pl.edu.agh.footprint.tree.model;

import pl.edu.agh.footprint.tree.model.parameter.Parameter;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>This class represents the &lt;Action&gt; tag in the XML footprint tree.</p>
 *
 * <p>Note: Create instances of this class using dedicated {@link pl.edu.agh.footprint.tree.builder.ActionBuilder
 * builder}.</p>
 *
 * @author Bartłomiej Grochal
 */
public class Action {

	// Obligatory attributes.
	private final String title;
	private final String type;
	private final Method method;

	// Optional attributes.
	private final List<Parameter> parameters;
	private final List<String> footprintActionTypes;


	public Action(String title, String type, Method method) {
		this.title = title;
		this.type = type;
		this.method = method;

		parameters = new LinkedList<>();
		footprintActionTypes = new LinkedList<>();
	}


	public void addParameter(Parameter parameter) {
		parameters.add(parameter);
	}

	public void addFootprintActionType(String footprintActionType) {
		footprintActionTypes.add(footprintActionType);
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	public Method getMethod() {
		return method;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public List<String> getFootprintActionTypes() {
		return footprintActionTypes;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder()
			.append("Action :\n\ttitle\t: ")
			.append(title)
			.append("\n\ttype\t: ")
			.append(type)
			.append("\n\tmethod\t: ")
			.append(method.getName())
			.append("\n");

		if (parameters.size() > 0) {
			builder.append("\tparams\t:\n");
			parameters.forEach(parameter -> builder.append("\t\t").append(parameter));
		}

		if (footprintActionTypes.size() > 0) {
			builder.append("\tfootprints\t:\n");
			footprintActionTypes.forEach(footprintAction -> builder.append("\t\t").append(footprintAction).append("\n"));
		}

		return builder.toString();
	}

}
