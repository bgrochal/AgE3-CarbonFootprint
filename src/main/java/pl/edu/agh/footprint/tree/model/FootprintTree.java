package pl.edu.agh.footprint.tree.model;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>This class represents whole footprint tree defined as an input of the algorithm in the XML file. In particular,
 * this class contains full definitions of all available actions and a title of the target footprint action.</p>
 *
 * <p>Note: Create instances of this class using dedicated {@link pl.edu.agh.footprint.tree.builder.FootprintTreeBuilder
 * builder}.</p>
 *
 * @author Bartłomiej Grochal
 */
public class FootprintTree {

	// Obligatory attributes.
	private final String targetActionType;

	// Optional attributes.
	private final List<Action> actions;


	public FootprintTree(String targetActionType) {
		this.targetActionType = targetActionType;
		actions = new LinkedList<>();
	}


	public void addAction(Action footprintAction) {
		actions.add(footprintAction);
	}

	public String getTargetActionType() {
		return targetActionType;
	}

	public List<Action> getActions() {
		return actions;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder()
			.append("Target action type: ")
			.append(targetActionType)
			.append("\n\nAvailable actions:\n");
		actions.forEach(action -> builder.append(action).append("\n"));

		return builder.toString();
	}

}
