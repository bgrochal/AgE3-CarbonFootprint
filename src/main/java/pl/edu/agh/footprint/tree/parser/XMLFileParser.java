package pl.edu.agh.footprint.tree.parser;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.logging.Logger;

/**
 * This class is responsible for parsing read {@link File} to the {@link Document XML format}.
 *
 * @author Bartłomiej Grochal
 */
public class XMLFileParser {

	private XMLFileParser() {
	}


	/**
	 * Returns an instance of the {@link Document} class which is an XML-representation of given {@code file}.
	 */
	public static Document parseXMLFile(File file) {
		SAXReader saxReader = new SAXReader();

		try {
			return saxReader.read(file);
		} catch (DocumentException exc) {
			Logger.getAnonymousLogger().warning("An error occurred while reading XML file.");
			exc.printStackTrace();
			return null;
		}
	}

}
