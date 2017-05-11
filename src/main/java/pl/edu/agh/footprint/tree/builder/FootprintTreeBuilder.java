package pl.edu.agh.footprint.tree.builder;

import pl.edu.agh.footprint.tree.model.Action;
import pl.edu.agh.footprint.tree.model.FootprintTree;

/**
 * This class defines the builder abstraction for the {@link FootprintTree} model class.
 *
 * @author Bartłomiej Grochal
 */
public class FootprintTreeBuilder {

	private final FootprintTree footprintTree;


	public FootprintTreeBuilder(String targetActionTitle) {
		footprintTree = new FootprintTree(targetActionTitle);
	}


	public FootprintTreeBuilder addAction(Action footprintAction) {
		footprintTree.addAction(footprintAction);
		return this;
	}

	public FootprintTree build() {
		return footprintTree;
	}

}
