package pl.edu.agh.footprint.age.util;

import pl.edu.agh.footprint.tree.model.Action;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class contains utility methods used for processing data defined in the {@link
 * pl.edu.agh.footprint.tree.model.FootprintTree FootprintTree}.
 *
 * @author Bartłomiej Grochal
 */
public class TreeUtil {

	private TreeUtil() {
	}


	/**
	 * Returns a mapping between {@link Action#type Action.type}s and a list of {@link Action actions} containing this {@link
	 * Action#type}.
	 */
	public static final Map<String, List<Action>> getActionsByType(List<Action> actions) {
		return actions.stream().collect(Collectors.groupingBy(Action::getType));
	}

}
