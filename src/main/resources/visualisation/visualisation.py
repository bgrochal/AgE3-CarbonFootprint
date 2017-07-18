"""
This script evaluates the *.log files produced by the solver of the Carbon Footprint problem.

Note that this script evaluates all files placed in the {BASE_DIR}/logs directory, where the {BASE_DIR} represents the
path to the main directory of the AgE3-CarbonFootprint repository.

Note also that this script evaluates only the BEST_SOLUTION_SO_FAR field contained in the log entries with a tick
summary (i.e. the log entries starting with the '[S]' prefix).
"""

# TODO: Implement evaluation of the log entries for a particular workplace in a tick (i.e. the log entry starting with
# the '[W]' prefix) and the log entry with the best solution (i.e. the log entry staring with the '[B]' prefix).

import os

import numpy as np
from matplotlib import pyplot as plt


def main():
	# Reading configuration file.
	config = dict()

	with open('../pl/edu/agh/footprint/age/carbon-footprint-config.properties') as properties_file:
		for entry in properties_file:
			if len(entry.strip()) > 0 and (not entry.startswith('#')):
				key, value = entry.split('=', 1)
				config[key.strip()] = value.strip()

	logging_interval_seconds = int(config['footprint.problem.logging-interval-in-ms']) / 1000
	simulation_time = int(config['footprint.problem.stop-condition-in-seconds'])
	ticks_number = int(simulation_time / logging_interval_seconds)

	# Opening *.log files.
	logs_directory_path = '../../../../logs'
	best_solutions = list()

	for log_file_path in os.listdir(logs_directory_path):
		with open(os.path.join(logs_directory_path, log_file_path)) as log_file:
			best_solutions_by_file = list()

			# Parsing the *.log file.
			for entry in log_file:
				if entry.startswith('[S]'):
					best_solutions_by_file.append(float(entry.split(';', 3)[2]))
			assert len(best_solutions_by_file) == ticks_number
			best_solutions.append(best_solutions_by_file)

	# Processing collected data.
	assert len(best_solutions) == len(os.listdir(logs_directory_path))
	best_solutions_by_tick = np.array([np.array([solution[tick] for solution in best_solutions]) for tick in range(ticks_number)])

	# Visualising processed data.
	plt.boxplot(best_solutions_by_tick.T, notch=False, showmeans=True, meanline=True,
				flierprops=dict(markerfacecolor='white', markeredgecolor='black', markersize=7, marker='.'),
				meanprops=dict(color='green', linestyle='-'), whiskerprops=dict(color='purple', linestyle='-'),
				capprops=dict(color='purple'))

	ticks_range = np.arange(0, simulation_time + logging_interval_seconds, logging_interval_seconds)
	ticks_strings = ['%.2f' % tick for tick in ticks_range]
	plt.xticks(np.arange(0, 61), ticks_strings, fontsize=10, rotation=45)

	plt.yticks(np.arange(plt.gca().get_ylim()[0], plt.gca().get_ylim()[1], 2), fontsize=10)

	plt.xlabel('Simulation time [s]', fontsize=10)
	plt.ylabel('Best fitness value', fontsize=10)
	plt.title('Best fitness value among the population in the time domain')

	plt.subplots_adjust(left=0.03, bottom=0.06, right=0.97, top=0.94, wspace=0.0, hspace=0.0)
	plt.show()


if __name__ == '__main__':
	main()
