package br.com.actionlabs.carboncalc.services.CarbonEmissionCalculator;

import br.com.actionlabs.carboncalc.dto.TransportationDTO;
import br.com.actionlabs.carboncalc.exceptions.handler.ResourceNotFoundException;
import br.com.actionlabs.carboncalc.model.EnergyEmissionFactor;
import br.com.actionlabs.carboncalc.model.SolidWasteEmissionFactor;
import br.com.actionlabs.carboncalc.model.TransportationEmissionFactor;
import br.com.actionlabs.carboncalc.repository.EnergyEmissionFactorRepository;
import br.com.actionlabs.carboncalc.repository.SolidWasteEmissionFactorRepository;
import br.com.actionlabs.carboncalc.repository.TransportationEmissionFactorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarbonEmissionCalculatorServiceImpl implements CarbonEmissionCalculatorService {

    private final EnergyEmissionFactorRepository energyEmissionFactorRepository;
    private final SolidWasteEmissionFactorRepository solidWasteEmissionFactorRepository;
    private final TransportationEmissionFactorRepository transportationEmissionFactorRepository;

    @Override
    public double calculateEnergyEmission(int energyConsumption, String uf){
        EnergyEmissionFactor energyEmissionFactor = this.energyEmissionFactorRepository
                .findById(uf)
                .orElseThrow(() -> new ResourceNotFoundException("energy factor not found"));

        return energyConsumption * energyEmissionFactor.getFactor();
    }

    @Override
    public double calculateTransportationEmission(List<TransportationDTO> transportation){

        double totalEmission = 0.0;

        for (TransportationDTO trans : transportation) {
            TransportationEmissionFactor emissionFactor = transportationEmissionFactorRepository
                    .findById(trans.getType())
                    .orElseThrow(() -> new ResourceNotFoundException("transportation type factor not found"));

            totalEmission += trans.getMonthlyDistance() * emissionFactor.getFactor();
        }

        return totalEmission;
    }

    @Override
    public double calculateSolidWasteEmission(int solidWasteTotal, double recyclePercentage, String uf){
        SolidWasteEmissionFactor solidWasteEmissionFactor = solidWasteEmissionFactorRepository
                .findById(uf)
                .orElseThrow(() -> new ResourceNotFoundException("solid waste factor not found"));

        double recyclableEmission = solidWasteTotal * recyclePercentage * solidWasteEmissionFactor.getRecyclableFactor();
        double nonRecyclableEmission = solidWasteTotal * (1 - recyclePercentage) * solidWasteEmissionFactor.getNonRecyclableFactor();

        return recyclableEmission + nonRecyclableEmission;
    }
}
