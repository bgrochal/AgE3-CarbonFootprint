package pl.edu.agh.footprint.age.mutation;

import com.google.common.base.Preconditions;
import pl.edu.agh.footprint.age.solution.CarbonFootprintSolution;
import pl.edu.agh.footprint.age.util.ObjectClonerService;

/**
 * <p>This class defines the mutation algorithm acting on either {@link
 * pl.edu.agh.footprint.tree.model.Action#parameters parameters} or {@link
 * pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTreeNode}s. This mutation strategy decides which
 * kind of mutation should be applied (according to given  probabilities: {@link #parameterMutationProbability} and
 * {@link #nodeMutationProbability}) and performs a mutation of the selected type.</p>
 *
 * <p>Note that a value of the {@link #mutationProbability} parameter is not used to decide which kind of mutation will
 * be used, but it is used when applying a concrete mutation strategy. Note also that it is required to satisfy the
 * following condition: {@link #parameterMutationProbability} + {@link #nodeMutationProbability} = 1. This condition
 * should be naturally modified when the new mutation strategy is added.</p>
 *
 * @author Bartłomiej Grochal
 */
public class CarbonFootprintCombinedRandomMutation extends CarbonFootprintAbstractMutation {

	private final double parameterMutationProbability;
	private final double nodeMutationProbability;

	private final CarbonFootprintParameterRandomMutation parameterMutation;
	private final CarbonFootprintNodeRandomMutation nodeMutation;


	/**
	 * @param parameterMutationProbability probability of selecting the {@link CarbonFootprintParameterRandomMutation}
	 *                                     as a mutation operator.
	 * @param nodeMutationProbability      probability of selecting the {@link CarbonFootprintNodeRandomMutation} as a
	 *                                     mutation operator.
	 * @param parameterMutation            instance of the {@link CarbonFootprintParameterRandomMutation} operator.
	 * @param nodeMutation                 instance of the {@link CarbonFootprintNodeRandomMutation} operator.
	 */
	CarbonFootprintCombinedRandomMutation(final double mutationProbability, final ObjectClonerService objectClonerService,
										  final double parameterMutationProbability, final double nodeMutationProbability,
										  final CarbonFootprintParameterRandomMutation parameterMutation,
										  final CarbonFootprintNodeRandomMutation nodeMutation) {
		super(mutationProbability, objectClonerService);
		Preconditions.checkArgument(parameterMutationProbability + nodeMutationProbability == 1.0);

		this.parameterMutationProbability = parameterMutationProbability;
		this.nodeMutationProbability = nodeMutationProbability;

		this.parameterMutation = parameterMutation;
		this.nodeMutation = nodeMutation;
	}


	/**
	 * This method selects the mutation strategy according to given values of probability and applies the selected
	 * mutation operator on given {@code solution}.
	 */
	@Override
	public CarbonFootprintSolution mutate(CarbonFootprintSolution solution) {
		// There is no need to copy the solution object, because it is done by proper mutation manager.
		return randomGenerator.nextDouble() < parameterMutationProbability ?
			parameterMutation.mutate(solution) : nodeMutation.mutate(solution);
	}

}
