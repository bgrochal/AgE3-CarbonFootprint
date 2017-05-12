package pl.edu.agh.footprint.age.evaluator;

import com.google.common.base.Preconditions;
import pl.edu.agh.age.compute.stream.problem.Evaluator;
import pl.edu.agh.age.compute.stream.problem.EvaluatorCounter;
import pl.edu.agh.footprint.age.solution.CarbonFootprintSolution;
import pl.edu.agh.footprint.tree.methods.container.ContainerMethodAttribute;
import pl.edu.agh.footprint.tree.methods.container.MethodsContainer;
import pl.edu.agh.footprint.tree.model.Action;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * This class is responsible for evaluating fitness value of a {@link CarbonFootprintSolution solution} of the Carbon
 * Footprint problem.
 *
 * @author Bartłomiej Grochal
 */
public class CarbonFootprintEvaluator implements Evaluator<CarbonFootprintSolution> {

	private final EvaluatorCounter counter;
	private final MethodsContainer methodsContainer;


	/**
	 * Instantiates a new fitness evaluator for the CARBON FOOTPRINT problem.
	 *
	 * @param counter          counts how many times was the evaluator called.
	 * @param methodsContainer container for implementations of methods defined in the XML footprint tree.
	 */
	public CarbonFootprintEvaluator(final EvaluatorCounter counter, final MethodsContainer methodsContainer) {
		this.counter = Objects.requireNonNull(counter);
		this.methodsContainer = Objects.requireNonNull(methodsContainer);
	}


	/**
	 * Returns a fitness value of given {@link CarbonFootprintSolution solution}.
	 */
	@Override
	public double evaluate(CarbonFootprintSolution solution) {
		counter.increment();
		return evaluate(solution.getSolutionTree());
	}

	/**
	 * Returns a footprint value associated with given {@link CarbonFootprintSolution.SolutionTree solutionTree}. Note
	 * that the footprint value equals the fitness value.
	 */
	private double evaluate(CarbonFootprintSolution.SolutionTree solutionTree) {
		List<ContainerMethodAttribute> evaluationResult = evaluateTree(solutionTree.getRoot());
		Preconditions.checkArgument(evaluationResult.size() == 1);

		return evaluationResult.get(0).getValue();
	}

	/**
	 * <p>Computes a value returned by chain-call of all functions defined in the XML footprint tree and associated with
	 * given {@link CarbonFootprintSolution solution}.</p>
	 *
	 * <p>This method contains implementation-specific details, so it is highly recommended not to change its visibility
	 * level. If - for any reason - visibility level of this method is broaden, it is furthermore recommended to declare
	 * this method as a {@code final} one.</p>
	 */
	@SuppressWarnings("unchecked")
	private List<ContainerMethodAttribute> evaluateTree(CarbonFootprintSolution.SolutionTreeNode treeNode) {
		final Action nodeAction = treeNode.getAction();
		List<ContainerMethodAttribute> footprintValues = treeNode.getChildren().stream()
			.map(this::evaluateTree)
			.flatMap(List::stream)
			.collect(Collectors.toList());

		try {
			return (List<ContainerMethodAttribute>) nodeAction.getMethod()
				.invoke(methodsContainer, footprintValues, nodeAction.getParameters());
		} catch (IllegalAccessException | InvocationTargetException exc) {
			Logger.getAnonymousLogger()
				.warning("An error occurred while invoking method: " + nodeAction.getMethod().getName());
			exc.printStackTrace();
			return Collections.emptyList();
		}
	}

}
