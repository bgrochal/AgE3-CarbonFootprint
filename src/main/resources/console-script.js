var FILE_PREFIX = "file:";

function runComputations(rootDirectory, configFileName, propertiesFileNames, repetitions) {
	var configFile = FILE_PREFIX + rootDirectory + configFileName;
	for (var i = 0; i < propertiesFileNames.length; i++) {
		var propertiesFile = FILE_PREFIX + rootDirectory + propertiesFileNames[i];
		for (var j = 0; j < repetitions; j++) {
			streamcomputation.reloadLogger();
			streamcomputation.load({ config:configFile, propertiesFiles:[propertiesFile] });
			streamcomputation.start();
			sleep(1000); // wait for initialization to finish
			streamcomputation.waitUntilFinished();
			streamcomputation.clean();
		}
	}
}
