package pl.edu.agh.footprint.tree;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.edu.agh.age.compute.stream.emas.EmasAgent;
import pl.edu.agh.footprint.age.mutation.CarbonFootprintAbstractMutation;
import pl.edu.agh.footprint.age.mutation.CarbonFootprintCombinedRandomMutation;
import pl.edu.agh.footprint.age.mutation.CarbonFootprintNodeRandomMutation;
import pl.edu.agh.footprint.age.mutation.CarbonFootprintParameterRandomMutation;
import pl.edu.agh.footprint.age.population.CarbonFootprintPopulationGenerator;
import pl.edu.agh.footprint.age.recombination.CarbonFootprintRandomTreeRecombination;
import pl.edu.agh.footprint.age.solution.CarbonFootprintSolution;
import pl.edu.agh.footprint.tree.model.FootprintTree;

import java.util.List;

/**
 * @author Bartłomiej Grochal
 */
public class FootprintTreeProviderTest {

	@Test
	public void printGenotypesWithCombinedParametersAndNodes() {
		printMutatedGenotypes(CarbonFootprintCombinedRandomMutation.class);
	}

	@Test
	public void printGenotypesWithMutatedNodes() {
		printMutatedGenotypes(CarbonFootprintNodeRandomMutation.class);
	}

	@Test
	public void printGenotypesWithMutatedParameters() {
		printMutatedGenotypes(CarbonFootprintParameterRandomMutation.class);
	}

	private void printMutatedGenotypes(Class<? extends CarbonFootprintAbstractMutation> mutationManagerClass) {
		ConfigurableApplicationContext context =
			new ClassPathXmlApplicationContext("pl/edu/agh/footprint/age/carbon-footprint-config.xml");
		CarbonFootprintPopulationGenerator populationGenerator =
			context.getBean(CarbonFootprintPopulationGenerator.class);
		CarbonFootprintAbstractMutation mutationManager =
			context.getBean(mutationManagerClass);

		List<EmasAgent> population = populationGenerator.createPopulation();
		StringBuilder builder = new StringBuilder();
		population.stream()
			.map(emasAgent -> emasAgent.solution)
			.forEach(solution -> builder
				.append(solution.toString())
				.append("\n")
				.append(mutationManager.mutate((CarbonFootprintSolution) solution))
				.append("\n\n"));

		System.out.println(builder.toString());

	}

	@Test
	public void printRecombinatedOffspring() {
		ConfigurableApplicationContext context =
			new ClassPathXmlApplicationContext("pl/edu/agh/footprint/age/carbon-footprint-config.xml");
		CarbonFootprintPopulationGenerator populationGenerator =
			context.getBean(CarbonFootprintPopulationGenerator.class);
		CarbonFootprintRandomTreeRecombination recombinationManager =
			context.getBean(CarbonFootprintRandomTreeRecombination.class);

		List<EmasAgent> population = populationGenerator.createPopulation();
		CarbonFootprintSolution offspring = recombinationManager.recombine(
			(CarbonFootprintSolution) population.get(0).solution, (CarbonFootprintSolution) population.get(1).solution);

		StringBuilder builder = new StringBuilder()
			.append(population.get(0).solution.toString())
			.append("\n")
			.append(population.get(1).solution.toString())
			.append("\n")
			.append(offspring.toString());

		System.out.println(builder.toString());
	}

	@Test
	public void printInitialSolutions() {
		ConfigurableApplicationContext context =
			new ClassPathXmlApplicationContext("pl/edu/agh/footprint/age/carbon-footprint-config.xml");
		CarbonFootprintPopulationGenerator populationGenerator =
			context.getBean(CarbonFootprintPopulationGenerator.class);

		StringBuilder builder = new StringBuilder();
		populationGenerator.createPopulation().forEach(emasAgent -> builder
			.append(emasAgent.solution)
			.append("\n"));

		System.out.println(builder.toString());
	}

	@Test
	public void printFootprintTree() {
		ConfigurableApplicationContext context =
			new ClassPathXmlApplicationContext("pl/edu/agh/footprint/age/carbon-footprint-config.xml");
		FootprintTree footprintTree = context.getBean(FootprintTree.class);
		System.out.println(footprintTree);
	}

}
