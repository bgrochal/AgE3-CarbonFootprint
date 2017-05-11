package pl.edu.agh.footprint.age.problem;

import pl.edu.agh.age.compute.stream.logging.DefaultLoggingService;
import pl.edu.agh.age.compute.stream.problem.ProblemDefinition;

import java.util.Objects;

/**
 * <p>This class contains the definition of the Carbon Footprint problem.</p>
 *
 * <p>The Carbon Footprint problem is defined simply by the path to a file containing the XML footprint tree.</p>
 *
 * @author Bartłomiej Grochal
 */
public class CarbonFootprintProblem implements ProblemDefinition {

	private final String filePath;


	/**
	 * Instantiates a new CARBON FOOTPRINT problem.
	 *
	 * @param filePath path to a file containing the definition of the XML footprint tree.
	 */
	public CarbonFootprintProblem(final String filePath) {
		this.filePath = filePath;
	}


	public String getFilePath() {
		return filePath;
	}

	@Override
	public String representation() {
		return String.join(DefaultLoggingService.DELIMITER, "CARBON FOOTPRINT", filePath);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof CarbonFootprintProblem)) {
			return false;
		}
		final CarbonFootprintProblem that = (CarbonFootprintProblem) object;
		return Objects.equals(filePath, that.filePath);
	}

	@Override
	public int hashCode() {
		return Objects.hash(filePath);
	}

}
