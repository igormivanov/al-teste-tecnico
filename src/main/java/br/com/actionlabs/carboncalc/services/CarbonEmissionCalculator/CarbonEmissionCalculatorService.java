package br.com.actionlabs.carboncalc.services.CarbonEmissionCalculator;

import br.com.actionlabs.carboncalc.dto.TransportationDTO;

import java.util.List;

public interface CarbonEmissionCalculatorService {
    public double calculateEnergyEmission(int energyConsumption, String uf);
    public double calculateTransportationEmission(List<TransportationDTO> transportation);
    public double calculateSolidWasteEmission(int solidWasteTotal, double recyclePercentage, String uf);
}
