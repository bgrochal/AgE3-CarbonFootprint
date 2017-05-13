package pl.edu.agh.footprint.tree;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.edu.agh.footprint.age.population.CarbonFootprintPopulationGenerator;
import pl.edu.agh.footprint.tree.model.FootprintTree;

/**
 * @author Bartłomiej Grochal
 */
public class FootprintTreeProviderTest {

	@Test
	public void printFootprintTree() {
		ConfigurableApplicationContext context =
			new ClassPathXmlApplicationContext("pl/edu/agh/footprint/age/carbon-footprint-config.xml");
		FootprintTree footprintTree = context.getBean(FootprintTree.class);
		System.out.println(footprintTree);
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

}
