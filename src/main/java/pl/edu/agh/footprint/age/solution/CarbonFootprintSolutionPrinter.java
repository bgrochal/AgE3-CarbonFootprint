package pl.edu.agh.footprint.age.solution;

import pl.edu.agh.footprint.tree.model.Action;
import pl.edu.agh.footprint.tree.model.parameter.Parameter;

import java.util.List;
import java.util.stream.IntStream;

/**
 * This utility class defines a String-representation of a {@link CarbonFootprintSolution solution} of the Carbon
 * Footprint problem.
 *
 * @author Bartłomiej Grochal
 */
public class CarbonFootprintSolutionPrinter {

	private CarbonFootprintSolutionPrinter() {
	}


	/**
	 * Returns a human-readable String-representation of given {@link CarbonFootprintSolution solution} of the Carbon
	 * Footprint problem.
	 */
	public static String getSolutionStringRepresentation(CarbonFootprintSolution solution) {
		StringBuilder builder = new StringBuilder()
			.append("Solution fitness: ")
			.append(solution.fitnessValue())
			.append("\nSolution tree:\n\n");
		appendSolutionTreeStringRepresentation(solution.getSolutionTree(), builder);

		return builder.toString();
	}

	/**
	 * Returns a String-representation of a {@link CarbonFootprintSolution.SolutionTree solution tree} being a part of
	 * given {@link CarbonFootprintSolution solution}.
	 */
	private static void appendSolutionTreeStringRepresentation(CarbonFootprintSolution.SolutionTree solutionTree,
															   StringBuilder builder) {
		appendSolutionTreeNodeStringRepresentation(solutionTree.getRoot(), builder, 0);
	}

	/**
	 * Returns a String-representation of a {@link CarbonFootprintSolution.SolutionTreeNode solution tree node} and its
	 * children.
	 */
	private static void appendSolutionTreeNodeStringRepresentation(CarbonFootprintSolution.SolutionTreeNode treeNode,
																   StringBuilder builder, int indentCount) {
		String indent = getIndent(indentCount);
		Action nodeAction = treeNode.getAction();
		List<Parameter> nodeParameters = nodeAction.getParameters();

		builder
			.append(indent)
			.append("Title: ")
			.append(nodeAction.getTitle())
			.append("\n")
			.append("Type: ")
			.append(nodeAction.getType())
			.append("\n");
		nodeParameters.forEach(parameter ->
			builder
				.append("  Parameter name: ")
				.append(parameter.getName())
				.append("; value: ")
				.append(parameter.getValue())
				.append("\n")
		);
		builder.append("\n");

		final int childIndentCount = indentCount + 1;
		treeNode.getChildren().forEach(child ->
			appendSolutionTreeNodeStringRepresentation(child, builder, childIndentCount));
	}

	/**
	 * Returns an indentation corresponding to a {@link CarbonFootprintSolution.SolutionTreeNode solution tree node}
	 * with given {@code indentCount}.
	 */
	private static String getIndent(int indentCount) {
		StringBuilder indentBuilder = new StringBuilder();
		IntStream.range(0, indentCount).forEach(i -> indentBuilder.append("\t"));
		return indentBuilder.toString();
	}

}
