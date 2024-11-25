package br.com.actionlabs.carboncalc.services.Calculation;

import br.com.actionlabs.carboncalc.dto.*;
import org.springframework.stereotype.Service;

public interface CalculationService {

    public StartCalcResponseDTO createCalculation(StartCalcRequestDTO startCalcRequestDTO);
    public UpdateCalcInfoResponseDTO updateCalculation(UpdateCalcInfoRequestDTO updateCalcInfoRequestDTO);
    public CarbonCalculationResultDTO getCarbonCalculationResult(String calculationId);
}
