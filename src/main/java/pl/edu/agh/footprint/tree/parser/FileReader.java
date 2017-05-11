package pl.edu.agh.footprint.tree.parser;

import com.hazelcast.util.Preconditions;
import com.sun.istack.internal.NotNull;

import java.io.File;

/**
 * This class is a utility class used for reading files by the Java Platform.
 *
 * @author Bartłomiej Grochal
 */
public class FileReader {

	private FileReader() {
	}


	/**
	 * Returns an instance of the {@link File} class which corresponds with given file {@code path}.
	 *
	 * @throws NullPointerException when given {@code path} is null.
	 */
	public static File readFile(@NotNull String path) throws NullPointerException {
		Preconditions.checkNotNull(path);
		return new File(path);
	}

}
