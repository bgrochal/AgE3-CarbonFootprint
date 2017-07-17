package pl.edu.agh.footprint.age.mutation;

import pl.edu.agh.footprint.age.solution.CarbonFootprintSolution;
import pl.edu.agh.footprint.age.solution.CarbonFootprintSolutionFactory;
import pl.edu.agh.footprint.age.util.ObjectClonerService;
import pl.edu.agh.footprint.age.util.TreeUtil;
import pl.edu.agh.footprint.tree.model.Action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>This class defines the mutation algorithm acting on the {@link
 * pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTreeNode}s. This mutation strategy selects a {@link
 * pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTreeNode} (according to a value of the {@link
 * #mutationProbability} parameter) and exchanges the {@link
 * pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTreeNode#action} with given {@link Action#type} to
 * another, randomly selected {@link Action} with corresponding {@link Action#type}. Then, the subtree rooted in the
 * mutated {@link pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTreeNode} is generated randomly by
 * the {@link #solutionFactory}.</p>
 *
 * <p>Note that it is acceptable to change the whole subtree rooted in the selected {@link
 * pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTreeNode}. Note also that a mutated {@link
 * Action} may be the same as the previous one if and only if this {@link Action} does not have any corresponding {@link
 * Action} (e.g. another {@link Action} with the same {@link Action#type}). If there exist at least two {@link Action}s
 * with the same {@link Action#type}, the mutated {@link
 * pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTreeNode#action} will be different than the
 * original one (i.e. the mutated {@link Action} will have different {@link Action#title}).
 *
 * @author Bartłomiej Grochal
 */
public class CarbonFootprintNodeRandomMutation extends CarbonFootprintAbstractMutation {

	private final CarbonFootprintSolutionFactory solutionFactory;
	private final Map<String, List<Action>> actionsByType;


	/**
	 * @param actions       list of all {@link Action}s defined in the XML footprint tree (parsed as an instance of the
	 *                      {@link pl.edu.agh.footprint.tree.model.FootprintTree FootprintTree} class).
	 */
	public CarbonFootprintNodeRandomMutation(final double mutationProbability, final ObjectClonerService objectClonerService,
											 final CarbonFootprintSolutionFactory solutionFactory, final List<Action> actions) {
		super(mutationProbability, objectClonerService);
		this.solutionFactory = solutionFactory;

		actionsByType = TreeUtil.getActionsByType(actions);
	}


	/**
	 * Returns a {@link CarbonFootprintSolution new solution} after performing mutation of the {@link
	 * pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTreeNode}s forming the {@link
	 * pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTree} on the {@code solution} given as an
	 * argument. This method collects all {@link
	 * pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTreeNode}s belonging to the {@link
	 * pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTree}. Then, it selects a node which may be
	 * mutated and exchanges its {@link pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTreeNode#action}
	 * with the corresponding, randomly selected one. Afterwards, this method generates randomly the footprint subtree
	 * rooted in the mutated {@link pl.edu.agh.footprint.age.solution.CarbonFootprintSolution.SolutionTreeNode}. If a
	 * selected node's {@link Action} does not have any corresponding {@link Action} with the same {@link Action#type}
	 * or the node has not been selected to the mutation, it remains unchanged.
	 */
	@Override
	public CarbonFootprintSolution mutate(CarbonFootprintSolution solution) {
		// TODO: Should be a copy of the solution made here? Why (if so)?
		CarbonFootprintSolution copiedSolution = objectClonerService.deepClone(solution);

		for (CarbonFootprintSolution.SolutionTreeNode treeNode : copiedSolution.getSolutionTree().getAllPreOrder()) {
			if (randomGenerator.nextDouble() < mutationProbability) {
				CarbonFootprintSolution.SolutionTreeNode mutatedSubtreeRootNode = performMutation(treeNode);

				if (!mutatedSubtreeRootNode.getAction().getTitle().equals(treeNode.getAction().getTitle())) {
					TreeUtil.replaceTreeNodes(treeNode, mutatedSubtreeRootNode, copiedSolution.getSolutionTree());
					break;
				}
			}
		}

		return copiedSolution;
	}


	/**
	 * This method performs a random mutation on a {@code treeNode} given as an argument. This type of mutation involves
	 * exchanging the subtree rooted in the {@code treeNode} with the new, randomly created one.
	 */
	private CarbonFootprintSolution.SolutionTreeNode performMutation(CarbonFootprintSolution.SolutionTreeNode treeNode) {
		Action nodeAction = treeNode.getAction();
		List<Action> correspondingActions = actionsByType.get(nodeAction.getType());

		if (correspondingActions.size() == 1) {
			return objectClonerService.deepClone(treeNode);
		}

		// Drawing an action, which is different than the original action.
		while (nodeAction.getTitle().equals(treeNode.getAction().getTitle())) {
			nodeAction = correspondingActions.get(randomGenerator.nextInt(correspondingActions.size()));
		}

		Action copiedAction = objectClonerService.deepClone(nodeAction);
		CarbonFootprintSolution.SolutionTreeNode newTreeNode = new CarbonFootprintSolution.SolutionTreeNode(copiedAction);
		copiedAction.getFootprintActionTypes().forEach(footprintActionType ->
			newTreeNode.addChild(solutionFactory.createSolutionTreeNode(footprintActionType, actionsByType, new HashMap<>(), true)));

		return newTreeNode;
	}

}
