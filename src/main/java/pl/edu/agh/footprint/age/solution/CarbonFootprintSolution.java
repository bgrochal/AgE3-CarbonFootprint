package pl.edu.agh.footprint.age.solution;

import pl.edu.agh.age.compute.stream.emas.solution.Solution;
import pl.edu.agh.footprint.tree.model.Action;

import java.util.LinkedList;
import java.util.List;

/**
 * This class implements a solution for the Carbon Footprint problem.
 *
 * @author Bartłomiej Grochal
 */
public class CarbonFootprintSolution implements Solution<CarbonFootprintSolution.SolutionTree> {

	private static final long serialVersionUID = 8900189756201776100L;

	private final SolutionTree solutionTree;
	private double fitness;


	/**
	 * Instantiates a new solution for the CARBON FOOTPRINT problem.
	 *
	 * @param solutionTree structure containing a footprint tree, which stands for a solution of the Carbon Footprint
	 *                     problem.
	 */
	public CarbonFootprintSolution(final SolutionTree solutionTree) {
		// TODO: Should be a copy of the solutionTree made here? Why (if so)?
		this.solutionTree = solutionTree;
		fitness = Double.NaN;
	}


	public SolutionTree getSolutionTree() {
		return solutionTree;
	}

	/**
	 * {@link #fitness} is a measure of "goodness" of the solution - the less the {@link #fitness} value, the better is
	 * the {@link #solutionTree solution}. Semantically, the {@link #fitness} value is the footprint value associated
	 * with given {@link #solutionTree solution tree}.
	 */
	@Override
	public double fitnessValue() {
		return fitness;
	}

	@Override
	public CarbonFootprintSolution updateFitness(double fitness) {
		this.fitness = fitness;
		return this;
	}

	@Override
	public SolutionTree unwrap() {
		// TODO: Should be a copy of the solutionTree made here? Why (if so)?
		return solutionTree;
	}

	@Override
	public String toString() {
		return CarbonFootprintSolutionPrinter.getSolutionStringRepresentation(this);
	}


	/**
	 * This class represents a single node in the {@link SolutionTree}. The node consists of an {@link #action} defined
	 * in the XML footprint tree and {@link #children} nodes defining direct footprint actions.
	 *
	 * @see SolutionTree
	 */
	public static class SolutionTreeNode {

		private final Action action;
		private final List<SolutionTreeNode> children;


		public SolutionTreeNode(final Action action) {
			this.action = action;
			children = new LinkedList<>();
		}


		public void addChild(SolutionTreeNode childNode) {
			children.add(childNode);
		}

		public Action getAction() {
			return action;
		}

		public List<SolutionTreeNode> getChildren() {
			return children;
		}

	}

	/**
	 * <p>This class represents a structure of the footprint tree, which stands for a solution of the Carbon Footprint
	 * problem.</p>
	 * <p>
	 * <p>The solution tree consists of {@link SolutionTreeNode nodes}. If there is more than one {@link Action} of
	 * given {@link Action#type type}, the solution tree contains only one of these {@link Action actions}. Moreover,
	 * the solution tree must contain exactly one {@link Action} of every defined {@link Action#type type} to form the
	 * correct solution for the Carbon Footprint problem.</p>
	 */
	public static class SolutionTree {

		private final SolutionTreeNode root;


		/**
		 * Instantiates a new footprint tree.
		 *
		 * @param root root of the footprint tree (cannot be updated).
		 */
		public SolutionTree(SolutionTreeNode root) {
			this.root = root;
		}


		public SolutionTreeNode getRoot() {
			return root;
		}

	}

}
