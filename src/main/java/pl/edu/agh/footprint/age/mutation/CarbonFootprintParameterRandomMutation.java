package pl.edu.agh.footprint.age.mutation;

import pl.edu.agh.footprint.age.solution.CarbonFootprintSolution;
import pl.edu.agh.footprint.age.util.ObjectClonerService;
import pl.edu.agh.footprint.tree.model.Action;
import pl.edu.agh.footprint.tree.model.parameter.ConfigurableParameter;

import java.util.List;

/**
 * <p>This class defines the mutation algorithm acting on the {@link Action}'s parameters. This mutation strategy takes
 * all parameters defined by the {@link Action}s forming the {@link
 * pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTreeNode}s and draws some of them (according to a
 * value of the {@link #mutationProbability} parameter). Then, each of selected parameters is mutated by setting a
 * random value belonging to the domain (either list or range) of this parameter.</p>
 *
 * <p>Note that it is acceptable to mutate parameters of multiple nodes in a single mutation process. Moreover, a new
 * value of the mutated parameter may be the same as a previous value. Furthermore, there is no limit for number of
 * parameters which may be mutated in a single node during a single mutation process (i.e. for given {@link Action},
 * there may be no mutation, as well as either exactly one, more than one or all parameters may be mutated).</p>
 *
 * @see ConfigurableParameter#setRandomValue()
 *
 * @author Bartłomiej Grochal
 */
public class CarbonFootprintParameterRandomMutation extends CarbonFootprintAbstractMutation {

	public CarbonFootprintParameterRandomMutation(final double mutationProbability, final ObjectClonerService objectClonerService) {
		super(mutationProbability, objectClonerService);
	}


	/**
	 * Returns a {@link CarbonFootprintSolution new solution} after performing mutation of the parameters on the {@code
	 * solution} given as an argument. This method collects all {@link Action#parameters} from {@link Action}s belonging
	 * to the {@link pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTreeNode}s forming a {@link
	 * pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTree}. Then, it selects a subset of these
	 * parameters and performs a random mutation on each of them. If a {@link
	 * pl.edu.agh.footprint.tree.model.parameter.Parameter parameter} is not configurable or has not been selected to
	 * the mutation, its value remains unchanged.
	 *
	 * @see ConfigurableParameter
	 */
	@Override
	public CarbonFootprintSolution mutate(CarbonFootprintSolution solution) {
		// TODO: Should be a copy of the solution made here? Why (if so)?
		CarbonFootprintSolution copiedSolution = objectClonerService.deepClone(solution);

		copiedSolution.getSolutionTree().getAllPreOrder().stream()
			.map(CarbonFootprintSolution.SolutionTreeNode::getAction)
			.map(Action::getParameters)
			.flatMap(List::stream)
			.filter(parameter -> parameter instanceof ConfigurableParameter)
			.forEach(parameter -> {
				if (randomGenerator.nextDouble() < mutationProbability) {
					performMutation((ConfigurableParameter) parameter);
				}
			});

		return copiedSolution;
	}


	/**
	 * This method performs a random mutation on a {@code parameter} given as an argument. This type of mutation
	 * involves setting a random value of the {@code parameter} belonging to its domain (either list or range).
	 *
	 * @see ConfigurableParameter#setRandomValue()
	 */
	private void performMutation(ConfigurableParameter parameter) {
		parameter.setRandomValue();
	}

}
