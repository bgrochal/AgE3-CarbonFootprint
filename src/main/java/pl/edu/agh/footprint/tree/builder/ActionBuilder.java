package pl.edu.agh.footprint.tree.builder;

import pl.edu.agh.footprint.tree.model.Action;
import pl.edu.agh.footprint.tree.model.parameter.Parameter;

import java.lang.reflect.Method;

/**
 * This class defines the builder abstraction for the {@link Action} model class.
 *
 * @author Bartłomiej Grochal
 */
public class ActionBuilder {

	private final Action action;


	public ActionBuilder(String title, String type, Method method) {
		this.action = new Action(title, type, method);
	}


	public ActionBuilder addParameter(Parameter parameter) {
		action.addParameter(parameter);
		return this;
	}

	public ActionBuilder addFootprintActionType(String footprintActionType) {
		action.addFootprintActionType(footprintActionType);
		return this;
	}

	public Action build() {
		return action;
	}

}
