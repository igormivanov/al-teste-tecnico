package br.com.actionlabs.carboncalc.services.Calculation;

import br.com.actionlabs.carboncalc.dto.*;
import br.com.actionlabs.carboncalc.exceptions.ResourceNotFoundException;
import br.com.actionlabs.carboncalc.model.Calculation;
import br.com.actionlabs.carboncalc.repository.CalculationRepository;
import br.com.actionlabs.carboncalc.services.CarbonEmissionCalculator.CarbonEmissionCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalculationServiceImpl implements CalculationService{

    private final CalculationRepository calculationRepository;
    private final CarbonEmissionCalculatorService carbonEmissionCalculator;


    @Override
    public StartCalcResponseDTO createCalculation(StartCalcRequestDTO startCalcRequestDTO) {

        Calculation entity = new Calculation();

        entity.setName(startCalcRequestDTO.getName());
        entity.setEmail(startCalcRequestDTO.getEmail());
        entity.setPhoneNumber(startCalcRequestDTO.getPhoneNumber());
        entity.setUf(startCalcRequestDTO.getUf());

        Calculation calculation = calculationRepository.save(entity);

        return new StartCalcResponseDTO(calculation.getId());
    }

    @Override
    public UpdateCalcInfoResponseDTO updateCalculation(UpdateCalcInfoRequestDTO updateCalcInfoRequestDTO) {

        UpdateCalcInfoResponseDTO response = new UpdateCalcInfoResponseDTO();

        try {
            Calculation calculation = calculationRepository
                    .findById(updateCalcInfoRequestDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Calculation not found"));

            calculation.setEnergyConsumption(updateCalcInfoRequestDTO.getEnergyConsumption());
            calculation.setRecyclePercentage(updateCalcInfoRequestDTO.getRecyclePercentage());
            calculation.setSolidWasteTotal(updateCalcInfoRequestDTO.getSolidWasteTotal());
            calculation.setTransportation(updateCalcInfoRequestDTO.getTransportation());

            calculationRepository.save(calculation);
            response.setSuccess(true);

            return response;

        } catch (Exception err) {
            response.setSuccess(false);
            return response;
        }
    }

    @Override
    public CarbonCalculationResultDTO getCarbonCalculationResult(String calculationId) {
        Calculation calculation = calculationRepository
                .findById(calculationId)
                .orElseThrow(() -> new ResourceNotFoundException("Calculation not found"));

        double energy = carbonEmissionCalculator.calculateEnergyEmission(calculation.getEnergyConsumption(), calculation.getUf());
        double transportation = carbonEmissionCalculator.calculateTransportationEmission(calculation.getTransportation());
        double solidWaste = carbonEmissionCalculator.calculateSolidWasteEmission(calculation.getSolidWasteTotal(),calculation.getRecyclePercentage(), calculation.getUf());

        double total = energy + transportation + solidWaste;

        CarbonCalculationResultDTO result = new CarbonCalculationResultDTO(
                energy,
                transportation,
                solidWaste,
                total
        );

        return result;
    }



    ;
}
