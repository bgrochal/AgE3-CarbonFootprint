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
 * <p>Note that it is acceptable to mutate multiple nodes in a single mutation process. Moreover, a new {@link Action}
 * selected for given node may be the same as a previous one.</p>
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
		List<Action> correspondingActions = actionsByType.get(treeNode.getAction().getType());
		Action originalAction = correspondingActions.get(randomGenerator.nextInt(correspondingActions.size()));
		Action copiedAction = objectClonerService.deepClone(originalAction);

		// TODO: Unify with duplicated code in CarbonFootprintSolutionFactory#createSolutionTreeNode(String, Map, boolean).
		copiedAction.getParameters().stream()
			.filter(Parameter::isConfigurable)
			.map(parameter -> (ConfigurableParameter) parameter)
			.forEach(ConfigurableParameter::setRandomValue);

		treeNode.setAction(copiedAction);
	}

}
