package pl.edu.agh.footprint.tree.provider;

/**
 * This enum defines tags used by the {@link FootprintTreeProvider} to transform the XML representation of the footprint
 * tree to an instance of {@link pl.edu.agh.footprint.tree.model.FootprintTree FootprintTree}.
 *
 * @author Bartłomiej Grochal
 */
public enum FootprintTreeTag {

	ACTION("/CarbonFootprint/Actions/Action"),
	ACTION_TYPE("@type"),
	ACTION_TITLE("@title"),
	ACTION_FOOTPRINT_METHOD("@method"),
	ACTION_PARAMETER("Parameters/Parameter"),
	ACTION_PARAMETER_TYPE("@type"),
	ACTION_PARAMETER_NAME("@name"),
	ACTION_PARAMETER_VALUE("@value"),
	ACTION_LIST_PARAMETER("List/Value"),
	ACTION_RANGE_PARAMETER_MIN("Range/@min"),
	ACTION_RANGE_PARAMETER_MAX("Range/@max"),
	ACTION_FOOTPRINT("Footprints/Footprint"),
	TARGET("/CarbonFootprint/Target");


	/**
	 * Relative path to a tag defined in accordance with the XPath (XML Path Language) specification.
	 */
	private final String tagPath;

	FootprintTreeTag(String tagPath) {
		this.tagPath = tagPath;
	}


	public String getTagPath() {
		return tagPath;
	}

}
