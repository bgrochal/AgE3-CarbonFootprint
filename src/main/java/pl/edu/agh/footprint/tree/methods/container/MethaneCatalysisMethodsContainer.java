package pl.edu.agh.footprint.tree.methods.container;

import pl.edu.agh.footprint.tree.model.parameter.Parameter;

import java.util.Collections;
import java.util.List;

import static pl.edu.agh.footprint.tree.util.MethodsContainerUtil.getAttributeValueByKey;
import static pl.edu.agh.footprint.tree.util.MethodsContainerUtil.getParameterValueByName;

/**
 * This class defines the container for methods defined for the Methane Catalysis problem in the MethaneCatalysis.xml file.
 *
 * @author Bartłomiej Grochal
 */
@SuppressWarnings("unused")
public class MethaneCatalysisMethodsContainer implements MethodsContainer {

	public List<ContainerMethodAttribute> getAmountOfMethaneToCatalyze(List<ContainerMethodAttribute> footprintValues,
																	   List<Parameter> parameters) {
		return Collections.singletonList(
			new ContainerMethodAttribute("AmountOfMethaneToCatalyze", getParameterValueByName(parameters, "Amount of methane [m^3]")));
	}

	public List<ContainerMethodAttribute> getFootprintEmissionOfPureMethane(List<ContainerMethodAttribute> footprintValues,
																			List<Parameter> parameters) {
		double methaneAmount = getAttributeValueByKey(footprintValues, "AmountOfMethaneToCatalyze");
		double maximumMethaneAmount = getParameterValueByName(parameters, "Maximum amount of methane [m^3]");
		double multiplier = getParameterValueByName(parameters, "Methane to CO2 multiplier");
		double combined = (maximumMethaneAmount - methaneAmount) * multiplier;

		return Collections.singletonList(
			new ContainerMethodAttribute("FootprintEmissionOfPureMethane", combined));
	}

	public List<ContainerMethodAttribute> getCatalyticCombustionOfMethane(List<ContainerMethodAttribute> footprintValues,
																		  List<Parameter> parameters) {
		return Collections.singletonList(
			new ContainerMethodAttribute("CatalyticCombustionOfMethane", getAttributeValueByKey(footprintValues, "AmountOfMethaneToCatalyze")));
	}

	public List<ContainerMethodAttribute> getPercentageCo2Emissions(List<ContainerMethodAttribute> footprintValues,
																	List<Parameter> parameters) {
		return Collections.singletonList(
			new ContainerMethodAttribute("PercentageCo2Emissions", getParameterValueByName(parameters, "Percent of CO2 emission")));
	}

	public List<ContainerMethodAttribute> getCo2ProducedByCombustion(List<ContainerMethodAttribute> footprintValues,
																	 List<Parameter> parameters) {
		double CatalyticCombustion = getAttributeValueByKey(footprintValues, "CatalyticCombustionOfMethane");
		double multiplier = getParameterValueByName(parameters, "Combustion multiplier");
		double combined = CatalyticCombustion * multiplier;

		return Collections.singletonList(
			new ContainerMethodAttribute("Co2ProducedByCombustion", combined));
	}

	public List<ContainerMethodAttribute> getFootprintEmissionCombustion(List<ContainerMethodAttribute> footprintValues,
																		 List<Parameter> parameters) {
		double co2Emissions = getAttributeValueByKey(footprintValues, "PercentageCo2Emissions");
		double co2Produced = getAttributeValueByKey(footprintValues, "Co2ProducedByCombustion");
		double combined = co2Emissions * co2Produced;

		return Collections.singletonList(
			new ContainerMethodAttribute("FootprintEmissionCombustion", combined));
	}

	public List<ContainerMethodAttribute> getCo2Separated(List<ContainerMethodAttribute> footprintValues,
														  List<Parameter> parameters) {
		double co2Emissions = getAttributeValueByKey(footprintValues, "PercentageCo2Emissions");
		double co2Produced = getAttributeValueByKey(footprintValues, "Co2ProducedByCombustion");
		double combined = (1.0 - co2Emissions) * co2Produced;

		return Collections.singletonList(
			new ContainerMethodAttribute("Co2Separated", combined));
	}

	public List<ContainerMethodAttribute> getHeatEnergyDemandSelexol(List<ContainerMethodAttribute> footprintValues,
																	 List<Parameter> parameters) {
		double co2Separated = getAttributeValueByKey(footprintValues, "Co2Separated");
		double multiplier = getParameterValueByName(parameters, "Energy multiplier");
		double combined = co2Separated * multiplier;

		return Collections.singletonList(
			new ContainerMethodAttribute("HeatEnergyDemandSelexol", combined));
	}

	public List<ContainerMethodAttribute> getCoolingEnergyDemandSelexol(List<ContainerMethodAttribute> footprintValues,
																		List<Parameter> parameters) {
		double co2Separated = getAttributeValueByKey(footprintValues, "Co2Separated");
		double multiplier = getParameterValueByName(parameters, "Energy multiplier");
		double combined = co2Separated * multiplier;

		return Collections.singletonList(
			new ContainerMethodAttribute("CoolingEnergyDemandSelexol", combined));
	}

	public List<ContainerMethodAttribute> getElectricEnergyDemandSelexol(List<ContainerMethodAttribute> footprintValues,
																		 List<Parameter> parameters) {
		double co2Separated = getAttributeValueByKey(footprintValues, "Co2Separated");
		double multiplier = getParameterValueByName(parameters, "Energy multiplier");
		double combined = co2Separated * multiplier;

		return Collections.singletonList(
			new ContainerMethodAttribute("ElectricEnergyDemandSelexol", combined));
	}

	public List<ContainerMethodAttribute> getElectricEnergyDemandForCompression(List<ContainerMethodAttribute> footprintValues,
																				List<Parameter> parameters) {
		double co2Separated = getAttributeValueByKey(footprintValues, "Co2Separated");
		double multiplier = getParameterValueByName(parameters, "Energy multiplier");
		double combined = co2Separated * multiplier;

		return Collections.singletonList(
			new ContainerMethodAttribute("ElectricEnergyDemandForCompression", combined));
	}

	public List<ContainerMethodAttribute> getEmissionProductionHeatEnergy(List<ContainerMethodAttribute> footprintValues,
																		  List<Parameter> parameters) {
		double emission = getParameterValueByName(parameters, "Emission from energy production");

		return Collections.singletonList(
			new ContainerMethodAttribute("EmissionProductionHeatEnergy", emission));
	}

	public List<ContainerMethodAttribute> getEmissionProductionElectricEnergy(List<ContainerMethodAttribute> footprintValues,
																			  List<Parameter> parameters) {
		double emission = getParameterValueByName(parameters, "Emission from energy production");

		return Collections.singletonList(
			new ContainerMethodAttribute("EmissionProductionElectricEnergy", emission));
	}

	public List<ContainerMethodAttribute> getFootprintEnergyProduction(List<ContainerMethodAttribute> footprintValues,
																	   List<Parameter> parameters) {
		double productionHeatEnergy = getAttributeValueByKey(footprintValues, "EmissionProductionHeatEnergy");
		double productionElectricEnergy = getAttributeValueByKey(footprintValues, "EmissionProductionElectricEnergy");
		double heatEnergy = getAttributeValueByKey(footprintValues, "HeatEnergyDemandSelexol");
		double coolingEnergy = getAttributeValueByKey(footprintValues, "CoolingEnergyDemandSelexol");
		double electricEnergy = getAttributeValueByKey(footprintValues, "ElectricEnergyDemandSelexol");
		double electricEnergyCompression = getAttributeValueByKey(footprintValues, "ElectricEnergyDemandForCompression");
		double combined = (productionHeatEnergy * (heatEnergy + coolingEnergy)) + ((electricEnergy + electricEnergyCompression) * productionElectricEnergy);

		return Collections.singletonList(
			new ContainerMethodAttribute("FootprintEnergyProduction", combined));
	}

	public List<ContainerMethodAttribute> getOverallFootprint(List<ContainerMethodAttribute> footprintValues,
															  List<Parameter> parameters) {
		double footprintEmissionOfPureMethane = getAttributeValueByKey(footprintValues, "FootprintEmissionOfPureMethane");
		double footprintEmissionCombustion = getAttributeValueByKey(footprintValues, "FootprintEmissionCombustion");
		double footprintEnergyProduction = getAttributeValueByKey(footprintValues, "FootprintEnergyProduction");
		double combined = footprintEmissionOfPureMethane + footprintEmissionCombustion + footprintEnergyProduction;

		return Collections.singletonList(
			new ContainerMethodAttribute("OverallFootprint", combined));
	}

}
