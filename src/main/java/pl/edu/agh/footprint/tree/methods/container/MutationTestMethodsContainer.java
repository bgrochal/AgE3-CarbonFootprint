package pl.edu.agh.footprint.tree.methods.container;

import pl.edu.agh.footprint.tree.model.parameter.Parameter;

import java.util.Collections;
import java.util.List;

import static pl.edu.agh.footprint.tree.util.MethodsContainerUtil.getAttributeValueByKey;
import static pl.edu.agh.footprint.tree.util.MethodsContainerUtil.getParameterValueByName;

/**
 * This class defines the container for methods defined for the Mutation Test problem in the MutationTest.xml file.
 *
 * @author Bartłomiej Grochal
 */
@SuppressWarnings("unused")
public class MutationTestMethodsContainer implements MethodsContainer {

    /* Extraction of a raw material */

	public List<ContainerMethodAttribute> getEmissionForFirstMethodOfExtraction(List<ContainerMethodAttribute> footprintValues,
																				List<Parameter> parameters) {
		return Collections.singletonList(
			new ContainerMethodAttribute("EmissionForFirstMethodOfExtraction", getParameterValueByName(parameters, "Emission")));
	}

	public List<ContainerMethodAttribute> getEmissionForSecondMethodOfExtraction(List<ContainerMethodAttribute> footprintValues,
																				 List<Parameter> parameters) {
		return Collections.singletonList(
			new ContainerMethodAttribute("EmissionForSecondMethodOfExtraction", getParameterValueByName(parameters, "Emission")));
	}

	public List<ContainerMethodAttribute> getEmissionForThirdMethodOfExtraction(List<ContainerMethodAttribute> footprintValues,
																				List<Parameter> parameters) {
		return Collections.singletonList(
			new ContainerMethodAttribute("EmissionForThirdMethodOfExtraction", getParameterValueByName(parameters, "Emission")));
	}

	public List<ContainerMethodAttribute> getConstantEmissionForExtraction(List<ContainerMethodAttribute> footprintValues,
																		   List<Parameter> parameters) {
		return Collections.singletonList(
			new ContainerMethodAttribute("ExtractionEmissionConstant", 30.0));
	}

	public List<ContainerMethodAttribute> getOverallEmissionForFirstMethodOfExtraction(List<ContainerMethodAttribute> footprintValues,
																					   List<Parameter> parameters) {
		double methodEmission = getAttributeValueByKey(footprintValues, "EmissionForFirstMethodOfExtraction");
		double constantEmission = getAttributeValueByKey(footprintValues, "ExtractionEmissionConstant");
		double combined = 100 + Math.log(10 * (1 + Math.abs(130 - methodEmission))) + constantEmission;

		return Collections.singletonList(
			new ContainerMethodAttribute("OverallEmissionForExtraction", combined));

	}

	public List<ContainerMethodAttribute> getOverallEmissionForSecondMethodOfExtraction(List<ContainerMethodAttribute> footprintValues,
																						List<Parameter> parameters) {
		double methodEmission = getAttributeValueByKey(footprintValues, "EmissionForSecondMethodOfExtraction");
		double constantEmission = getAttributeValueByKey(footprintValues, "ExtractionEmissionConstant");
		double combined = 105 + (methodEmission - 115) * Math.sin(113 - methodEmission) * Math.cos(117 - methodEmission)
			+ constantEmission;

		return Collections.singletonList(
			new ContainerMethodAttribute("OverallEmissionForExtraction", combined));
	}

	public List<ContainerMethodAttribute> getOverallEmissionForThirdMethodOfExtraction(List<ContainerMethodAttribute> footprintValues,
																					   List<Parameter> parameters) {
		double methodEmission = getAttributeValueByKey(footprintValues, "EmissionForThirdMethodOfExtraction");
		double constantEmission = getAttributeValueByKey(footprintValues, "ExtractionEmissionConstant");
		double combined = 100 + (123 - methodEmission) * Math.sin(3 * methodEmission) + constantEmission;

		return Collections.singletonList(
			new ContainerMethodAttribute("OverallEmissionForExtraction", combined));
	}


    /* Transport */

	public List<ContainerMethodAttribute> getEmissionForFuelConsumption(List<ContainerMethodAttribute> footprintValues,
																		List<Parameter> parameters) {
		return Collections.singletonList(
			new ContainerMethodAttribute("EmissionForFuelConsumption", 0.1));
	}

	public List<ContainerMethodAttribute> getFuelConsumptionForConventionalTruck(List<ContainerMethodAttribute> footprintValues,
																				 List<Parameter> parameters) {
		return Collections.singletonList(
			new ContainerMethodAttribute("FuelConsumption", 0.26));
	}

	public List<ContainerMethodAttribute> getFuelConsumptionForEcologicalTruck(List<ContainerMethodAttribute> footprintValues,
																			   List<Parameter> parameters) {
		return Collections.singletonList(
			new ContainerMethodAttribute("FuelConsumption", 0.20));
	}

	public List<ContainerMethodAttribute> getEmissionForWheelTransport(List<ContainerMethodAttribute> footprintValues,
																	   List<Parameter> parameters) {
		double fuelConsumptionEmission = getAttributeValueByKey(footprintValues, "EmissionForFuelConsumption");
		double fuelConsumption = getAttributeValueByKey(footprintValues, "FuelConsumption");
		double combined = fuelConsumptionEmission * fuelConsumption;

		return Collections.singletonList(
			new ContainerMethodAttribute("EmissionForWheelTransport", combined));

	}

	public List<ContainerMethodAttribute> getWheelDistance(List<ContainerMethodAttribute> footprintValues,
														   List<Parameter> parameters) {
		return Collections.singletonList(
			new ContainerMethodAttribute("WheelDistance", getParameterValueByName(parameters, "Distance")));
	}

	public List<ContainerMethodAttribute> getEmissionForRailwayTransport(List<ContainerMethodAttribute> footprintValues,
																		 List<Parameter> parameters) {
		return Collections.singletonList(
			new ContainerMethodAttribute("EmissionForRailwayTransport", 0.018));
	}

	public List<ContainerMethodAttribute> getRailwayDistance(List<ContainerMethodAttribute> footprintValues,
															 List<Parameter> parameters) {
		return Collections.singletonList(
			new ContainerMethodAttribute("RailwayDistance", 700.0));
	}

	public List<ContainerMethodAttribute> getOverallEmissionForWheelTransport(List<ContainerMethodAttribute> footprintValues,
																			  List<Parameter> parameters) {
		double wheelTransportEmission = getAttributeValueByKey(footprintValues, "EmissionForWheelTransport");
		double wheelDistance = getAttributeValueByKey(footprintValues, "WheelDistance");
		double combined = (wheelDistance + Math.pow(530 - wheelDistance, 2.0)) * wheelTransportEmission;

		return Collections.singletonList(
			new ContainerMethodAttribute("OverallEmissionForTransport", combined));
	}

	public List<ContainerMethodAttribute> getOverallEmissionForRailwayTransport(List<ContainerMethodAttribute> footprintValues,
																				List<Parameter> parameters) {
		double railwayTransportEmission = getAttributeValueByKey(footprintValues, "EmissionForRailwayTransport");
		double railwayDistance = getAttributeValueByKey(footprintValues, "RailwayDistance");
		double combined = railwayDistance * railwayTransportEmission;

		return Collections.singletonList(
			new ContainerMethodAttribute("OverallEmissionForTransport", combined));
	}


    /* Production */

	public List<ContainerMethodAttribute> getEmissionForFirstMethodOfProduction(List<ContainerMethodAttribute> footprintValues,
																				List<Parameter> parameters) {
		return Collections.singletonList(
			new ContainerMethodAttribute("EmissionForProduction", 40.0));
	}

	public List<ContainerMethodAttribute> getEmissionForSecondMethodOfProduction(List<ContainerMethodAttribute> footprintValues,
																				 List<Parameter> parameters) {
		return Collections.singletonList(
			new ContainerMethodAttribute("EmissionForProduction", 60.0));
	}

	public List<ContainerMethodAttribute> getConstantEmissionForProduction(List<ContainerMethodAttribute> footprintValues,
																		   List<Parameter> parameters) {
		return Collections.singletonList(
			new ContainerMethodAttribute("ProductionEmissionConstant", 10.0));
	}

	public List<ContainerMethodAttribute> getOverallEmissionForProduction(List<ContainerMethodAttribute> footprintValues,
																		  List<Parameter> parameters) {
		double productionEmission = getAttributeValueByKey(footprintValues, "EmissionForProduction");
		double constantEmission = getAttributeValueByKey(footprintValues, "ProductionEmissionConstant");
		double combined = productionEmission + constantEmission;

		return Collections.singletonList(
			new ContainerMethodAttribute("OverallEmissionForProduction", combined));
	}


	/* Final */

	public List<ContainerMethodAttribute> getOverallEmission(List<ContainerMethodAttribute> footprintValues,
															 List<Parameter> parameters) {
		double extractionEmission = getAttributeValueByKey(footprintValues, "OverallEmissionForExtraction");
		double transportEmission = getAttributeValueByKey(footprintValues, "OverallEmissionForTransport");
		double productionEmission = getAttributeValueByKey(footprintValues, "OverallEmissionForProduction");
		double combined = extractionEmission + transportEmission + productionEmission;

		return Collections.singletonList(
			new ContainerMethodAttribute("OverallEmission", combined));
	}

}
