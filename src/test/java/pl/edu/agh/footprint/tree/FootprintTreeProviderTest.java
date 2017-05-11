package pl.edu.agh.footprint.tree;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.edu.agh.footprint.tree.methods.container.MethodsContainer;
import pl.edu.agh.footprint.tree.provider.FootprintTreeProvider;

import java.util.Arrays;

/**
 * @author Bartłomiej Grochal
 */
public class FootprintTreeProviderTest {

	@Test
	public void printFootprintTree() {
		ConfigurableApplicationContext context =
			new ClassPathXmlApplicationContext("pl/edu/agh/footprint/age/carbon-footprint-config.xml");
		FootprintTreeProvider footprintTreeProvider = context.getBean(FootprintTreeProvider.class);
		System.out.println(footprintTreeProvider.getFootprintTree());
	}

}
