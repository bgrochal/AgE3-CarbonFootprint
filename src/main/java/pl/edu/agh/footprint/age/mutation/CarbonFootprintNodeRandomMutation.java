package pl.edu.agh.footprint.age.mutation;

import pl.edu.agh.footprint.age.solution.CarbonFootprintSolution;
import pl.edu.agh.footprint.age.util.ObjectClonerService;
import pl.edu.agh.footprint.age.util.TreeUtil;
import pl.edu.agh.footprint.tree.model.Action;
import pl.edu.agh.footprint.tree.model.parameter.ConfigurableParameter;
import pl.edu.agh.footprint.tree.model.parameter.Parameter;

import java.util.List;
import java.util.Map;

/**
 * <p>This class defines the mutation algorithm acting on the {@link
 * pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTreeNode}s. This mutation strategy selects some of
 * {@link pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTreeNode}s (according to a value of the
 * {@link #mutationProbability} parameter) and exchanges the {@link
 * pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTreeNode#action}s with given {@link
 * Action#type} to another, randomly selected {@link Action}s with corresponding {@link Action#type}.</p>
 *
 * <p>Note that it is acceptable to mutate multiple nodes in a single mutation process. Note also that a mutated {@link
 * Action} may be the same as the previous one if and only if this {@link Action} does not have any corresponding {@link
 * Action} (e.g. another {@link Action} with the same {@link Action#type}). If there exist at least two {@link Action}s
 * with the same {@link Action#type}, the mutated {@link Action} will be different than original one (i.e. the mutated
 * {@link Action} will have different {@link Action#title}).
 *
 * @author Bartłomiej Grochal
 */
public class CarbonFootprintNodeRandomMutation extends CarbonFootprintAbstractMutation {

	private final Map<String, List<Action>> actionsByType;


	/**
	 * @param actions       list of all {@link Action}s defined in the XML footprint tree (parsed as an instance of the
	 *                      {@link pl.edu.agh.footprint.tree.model.FootprintTree FootprintTree} class).
	 */
	public CarbonFootprintNodeRandomMutation(final double mutationProbability, final ObjectClonerService objectClonerService,
											 final List<Action> actions) {
		super(mutationProbability, objectClonerService);
		actionsByType = TreeUtil.getActionsByType(actions);
	}


	/**
	 * Returns a {@link CarbonFootprintSolution new solution} after performing mutation of the {@link
	 * pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTreeNode}s forming the {@link
	 * pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTree} on the {@code solution} given as an
	 * argument. This method collects all {@link
	 * pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTreeNode}s belonging to the {@link
	 * pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTree}. Then, it selects a subset of these nodes
	 * and exchanges their {@link pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTreeNode#action}s
	 * with the corresponding, randomly selected ones. If a selected node's {@link Action} does not have any
	 * corresponding {@link Action} with the same {@link Action#type} or the node has not been selected to the mutation,
	 * it remains unchanged.
	 */
	@Override
	public CarbonFootprintSolution mutate(CarbonFootprintSolution solution) {
		// TODO: Should be a copy of the solution made here? Why (if so)?
		CarbonFootprintSolution copiedSolution = objectClonerService.deepClone(solution);

		copiedSolution.getSolutionTree().getAllPreOrder()
			.forEach(treeNode -> {
				if (randomGenerator.nextDouble() < mutationProbability) {
					performMutation(treeNode);
				}
			});

		return copiedSolution;
	}


	/**
	 * This method performs a random mutation on a {@code treeNode} given as an argument. This type of mutation involves
	 * exchanging given {@code treeNode}'s {@link Action} with a corresponding, randomly selected one. The two {@link
	 * Action}s are corresponding when they have the same {@link Action#type}.
	 */
	private void performMutation(CarbonFootprintSolution.SolutionTreeNode treeNode) {
		Action originalAction = treeNode.getAction();
		List<Action> correspondingActions = actionsByType.get(originalAction.getType());

		// Drawing an action, which is different than the original action (if it's possible, e.g. if there exists more
		// than one action of given type.
		while (correspondingActions.size() > 1 && originalAction.getTitle().equals(treeNode.getAction().getTitle())) {
			originalAction = correspondingActions.get(randomGenerator.nextInt(correspondingActions.size()));
		}
		Action copiedAction = objectClonerService.deepClone(originalAction);

		// If a new action has been drawn, values of its parameters will be Double.NaN.
		copiedAction.getParameters().stream()
			.filter(Parameter::isConfigurable)
			.filter(parameter -> !parameter.hasValue())
			.map(parameter -> (ConfigurableParameter) parameter)
			.forEach(ConfigurableParameter::setRandomValue);

		treeNode.setAction(copiedAction);
	}

}
