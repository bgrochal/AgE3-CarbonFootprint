package pl.edu.agh.footprint.age.solution;

import com.google.common.base.Preconditions;
import com.rits.cloning.Cloner;
import pl.edu.agh.footprint.age.evaluator.CarbonFootprintEvaluator;
import pl.edu.agh.footprint.tree.model.Action;
import pl.edu.agh.footprint.tree.model.FootprintTree;
import pl.edu.agh.footprint.tree.model.parameter.ConfigurableParameter;
import pl.edu.agh.footprint.tree.model.parameter.Parameter;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * This class defines a factory method for creating individuals corresponding to solutions of the Carbon Footprint
 * problem. This factory creates solutions randomly, i.e.:
 * <ul>
 *     <li>if there are multiple {@link Action actions} with the same {@link Action#type type}, the solution contains
 *     exactly one, randomly chosen {@link Action action} of this {@link Action#type type};</li>
 *     <li>if an {@link Action action} contains at least one {@link ConfigurableParameter configurable parameter}, then
 *     this parameter's {@link Parameter#value value} is randomly chosen out of available values.</li>
 * </ul>
 *
 * @author Bartłomiej Grochal
 */
public class CarbonFootprintSolutionFactory {

	private final FootprintTree footprintTree;
	private final Cloner objectCloner;
	private final CarbonFootprintEvaluator evaluator;

	private final Random randomGenerator;
	private final Map<String, List<Action>> actionsByType;


	/**
	 * Instantiates a new factory method creating solutions of the CARBON FOOTPRINT problem.
	 *
	 * @param footprintTree representation of the footprint tree defined as an input of the algorithm.
	 * @param objectCloner  service object used for deep cloning other objects.
	 * @param evaluator     mechanism for calculating fitness value of a solution.
	 */
	public CarbonFootprintSolutionFactory(final FootprintTree footprintTree, final Cloner objectCloner,
										  final CarbonFootprintEvaluator evaluator) {
		this.footprintTree = Preconditions.checkNotNull(footprintTree);
		this.objectCloner = Preconditions.checkNotNull(objectCloner);
		this.evaluator = Preconditions.checkNotNull(evaluator);

		randomGenerator = ThreadLocalRandom.current();
		actionsByType = getActionsByType();
	}


	/**
	 * Returns a new {@link CarbonFootprintSolution solution} of the Carbon Footprint problem.
	 */
	public CarbonFootprintSolution create() {
		CarbonFootprintSolution solution = new CarbonFootprintSolution(createSolutionTree());
		return solution.updateFitness(evaluator.evaluate(solution));
	}


	/**
	 * Returns a mapping between a {@link Action#type types} and a list of {@link Action actions} containing this {@link
	 * Action#type}.
	 */
	private Map<String, List<Action>> getActionsByType() {
		return footprintTree.getActions().stream().collect(Collectors.groupingBy(Action::getType));
	}

	/**
	 * Returns an instance of the {@link CarbonFootprintSolution.SolutionTree} class being a newly-generated solution of
	 * the Carbon Footprint problem.
	 */
	private CarbonFootprintSolution.SolutionTree createSolutionTree() {
		final CarbonFootprintSolution.SolutionTreeNode rootNode =
			createSolutionTreeNode(footprintTree.getTargetActionType());
		return new CarbonFootprintSolution.SolutionTree(rootNode);
	}

	/**
	 * Returns a node of a newly-generated {@link CarbonFootprintSolution.SolutionTree solution tree}.
	 */
	private CarbonFootprintSolution.SolutionTreeNode createSolutionTreeNode(String nodeActionType) {
		List<Action> correspondingActions = actionsByType.get(nodeActionType);
		Action originalAction = correspondingActions.get(randomGenerator.nextInt(correspondingActions.size()));
		Action copiedAction = objectCloner.deepClone(originalAction);
		copiedAction.getParameters().stream()
			.filter(Parameter::isConfigurable)
			.map(parameter -> (ConfigurableParameter) parameter)
			.forEach(ConfigurableParameter::setRandomValue);

		CarbonFootprintSolution.SolutionTreeNode treeNode = new CarbonFootprintSolution.SolutionTreeNode(copiedAction);
		copiedAction.getFootprintActionTypes().forEach(footprintActionType ->
			treeNode.addChild(createSolutionTreeNode(footprintActionType)));
		return treeNode;
	}

}
