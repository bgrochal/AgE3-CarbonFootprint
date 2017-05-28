package pl.edu.agh.footprint.age.evaluator;

import javaslang.collection.Seq;
import pl.edu.agh.age.compute.stream.emas.EmasAgent;
import pl.edu.agh.age.compute.stream.emas.PopulationEvaluator;
import pl.edu.agh.age.compute.stream.emas.reproduction.improvement.Improvement;
import pl.edu.agh.age.compute.stream.emas.solution.Solution;
import pl.edu.agh.age.compute.stream.problem.Evaluator;

import java.util.Objects;
import java.util.Optional;


/**
 * This class is responsible for evaluating all {@link Solution}s belonging to a population with given {@link
 * #evaluator}.
 *
 * @author Bartłomiej Grochal
 */
public class MemeticPopulationEvaluatorWithImprovement<S extends Solution<?>> implements PopulationEvaluator<EmasAgent> {

	private final Evaluator<S> evaluator;
	private final Improvement<S> improvement;


	/**
	 * Creates a new instance of the population evaluator for the CARBON FOOTPRINT problem.
	 *
	 * @param evaluator		service for evaluating the fitness value of a solution.
	 * @param improvement	genetic improvement operator.
	 */
	public MemeticPopulationEvaluatorWithImprovement(final Evaluator<S> evaluator, final Optional<Improvement<S>> improvement) {
		this.evaluator = Objects.requireNonNull(evaluator);
		this.improvement = improvement.orElse(null);
	}


	/**
	 * Evaluates all agents belonging to the {@code population} and returns a new population consisting of agents
	 * containing evaluated solutions.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Seq<EmasAgent> evaluate(final Seq<EmasAgent> population) {
		return population
			.map(agent -> {
				final S solution = (S) agent.solution;
				solution.updateFitness(evaluator.evaluate(solution));
				return EmasAgent.create(agent.energy, improvement != null ? improvement.improve(solution) : solution);
			});
	}

}
