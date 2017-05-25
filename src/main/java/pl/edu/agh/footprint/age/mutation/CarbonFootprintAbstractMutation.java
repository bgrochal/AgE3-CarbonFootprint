package pl.edu.agh.footprint.age.mutation;

import pl.edu.agh.age.compute.stream.emas.reproduction.mutation.Mutation;
import pl.edu.agh.footprint.age.solution.CarbonFootprintSolution;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class contains common attributes used by every mutation strategy for the Carbon Footprint problem.
 *
 * @author Bartłomiej Grochal
 */
public abstract class CarbonFootprintAbstractMutation implements Mutation<CarbonFootprintSolution> {

	final double mutationProbability;
	final Random randomGenerator;


	/**
	 * Instantiates a new mutation manager for the CARBON FOOTPRINT problem.
	 *
	 * @param mutationProbability floating-point number belonging to the [0, 1) interval defining the probability of
	 *                            performing mutation (in other words, this parameter defines the average percentage of
	 *                            genes which will be selected to mutate).
	 */
	CarbonFootprintAbstractMutation(final double mutationProbability) {
		this.mutationProbability = mutationProbability;
		randomGenerator = ThreadLocalRandom.current();
	}

}
