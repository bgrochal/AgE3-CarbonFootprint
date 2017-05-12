package pl.edu.agh.footprint.age.population;

import com.google.common.base.Preconditions;
import javaslang.collection.Stream;
import pl.edu.agh.age.compute.stream.emas.EmasAgent;
import pl.edu.agh.age.compute.stream.emas.PopulationGenerator;
import pl.edu.agh.footprint.age.solution.CarbonFootprintSolution;
import pl.edu.agh.footprint.age.solution.CarbonFootprintSolutionFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is responsible for generating initial population. Each individual's genotype consists of an instance of
 * the {@link CarbonFootprintSolution solution} class.
 *
 * @author Bartłomiej Grochal
 */
public class CarbonFootprintPopulationGenerator implements PopulationGenerator<EmasAgent> {

	private final CarbonFootprintSolutionFactory solutionFactory;
	private final int agentsCount;
	private final double initialEnergy;


	/**
	 * Instantiates a new population generator for the CARBON FOOTPRINT problem.
	 *
	 * @param solutionFactory factory method for creating individuals belonging to the initial population.
	 * @param agentsCount     number of individuals to be generated.
	 * @param initialEnergy   amount of energy deposited to every newly-generated individual.
	 */
	public CarbonFootprintPopulationGenerator(final CarbonFootprintSolutionFactory solutionFactory,
											  final int agentsCount, final double initialEnergy) {
		Preconditions.checkArgument(agentsCount > 0);
		Preconditions.checkArgument(Double.compare(initialEnergy, 0.0) > 0);
		this.solutionFactory = Preconditions.checkNotNull(solutionFactory);
		this.agentsCount = agentsCount;
		this.initialEnergy = initialEnergy;
	}


	/**
	 * Returns a list containing an initial population of {@link EmasAgent agents}.
	 */
	@Override
	public List<EmasAgent> createPopulation() {
		return Stream.range(0, agentsCount)
			.toJavaStream()
			.map(agentNum -> createAgent())
			.collect(Collectors.toList());
	}


	/**
	 * Returns single {@link EmasAgent agent} belonging to the initial population.
	 */
	private EmasAgent createAgent() {
		CarbonFootprintSolution solution = solutionFactory.create();
		return EmasAgent.create(initialEnergy, solution);
	}

}
