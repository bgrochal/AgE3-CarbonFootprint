package pl.edu.agh.footprint.age.recombination;

import com.google.common.base.Preconditions;
import pl.edu.agh.age.compute.stream.emas.reproduction.recombination.Recombination;
import pl.edu.agh.footprint.age.solution.CarbonFootprintSolution;
import pl.edu.agh.footprint.age.solution.CarbonFootprintSolutionFactory;
import pl.edu.agh.footprint.tree.model.Action;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class represents the recombination algorithm, which produces one offspring {@link CarbonFootprintSolution
 * solution} from two parent {@link CarbonFootprintSolution solutions}. The algorithm of recombination breaks down
 * parent trees and builds a new offspring tree, which nodes are selected randomly from corresponding nodes belonging to
 * its parents. Note that - in particular - the offspring tree may be the same as one of parent trees.
 *
 * @author Bartłomiej Grochal
 */
public class CarbonFootprintRandomTreeRecombination implements Recombination<CarbonFootprintSolution> {

	private final CarbonFootprintSolutionFactory solutionFactory;


	/**
	 * Creates a new instance of the randomly-acting recombination manager for the CARBON FOOTPRINT problem.
	 *
	 * @param solutionFactory a factory method producing solutions for the Carbon Footprint problem.
	 */
	public CarbonFootprintRandomTreeRecombination(final CarbonFootprintSolutionFactory solutionFactory) {
		this.solutionFactory = Preconditions.checkNotNull(solutionFactory);
	}


	/**
	 * Returns a new offspring {@link CarbonFootprintSolution solution} combined from two parent {@link
	 * CarbonFootprintSolution solution}s by breaking down the parent {@link
	 * pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTree solution tree}s and creating a new one by
	 * picking one {@link pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTreeNode solution tree node}
	 * randomly for each pair of corresponding nodes.
	 */
	@Override
	public CarbonFootprintSolution recombine(final CarbonFootprintSolution firstSolution,
											 final CarbonFootprintSolution secondSolution) {
		Map<String, List<Action>> actionsByType = Stream
			.concat(firstSolution.getSolutionTree().getAllPreOrder().stream(),
					secondSolution.getSolutionTree().getAllPreOrder().stream())
			.map(CarbonFootprintSolution.SolutionTreeNode::getAction)
			.collect(Collectors.groupingBy(Action::getType));
		return solutionFactory.create(actionsByType, false);
	}

}
