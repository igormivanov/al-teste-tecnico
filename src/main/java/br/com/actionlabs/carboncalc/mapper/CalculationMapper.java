package br.com.actionlabs.carboncalc.mapper;

import br.com.actionlabs.carboncalc.dto.StartCalcRequestDTO;
import br.com.actionlabs.carboncalc.dto.StartCalcResponseDTO;
import br.com.actionlabs.carboncalc.model.Calculation;

public class CalculationMapper {

    public static Calculation toEntity(StartCalcRequestDTO startCalcRequestDTO) {
        Calculation calculation = new Calculation();

        calculation.setName(startCalcRequestDTO.getName());
        calculation.setEmail(startCalcRequestDTO.getEmail());
        calculation.setPhoneNumber(startCalcRequestDTO.getPhoneNumber());
        calculation.setUf(startCalcRequestDTO.getUf());

        return calculation;
    }

}
