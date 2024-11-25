package br.com.actionlabs.carboncalc.constants;

import br.com.actionlabs.carboncalc.dto.CarbonCalculationResultDTO;
import br.com.actionlabs.carboncalc.dto.StartCalcRequestDTO;
import br.com.actionlabs.carboncalc.dto.TransportationDTO;
import br.com.actionlabs.carboncalc.dto.UpdateCalcInfoRequestDTO;
import br.com.actionlabs.carboncalc.enums.TransportationType;
import br.com.actionlabs.carboncalc.model.Calculation;
import br.com.actionlabs.carboncalc.model.EnergyEmissionFactor;
import br.com.actionlabs.carboncalc.model.SolidWasteEmissionFactor;
import br.com.actionlabs.carboncalc.model.TransportationEmissionFactor;
import net.bytebuddy.utility.RandomString;

import java.util.List;

public class Constants {

    public static final Calculation CALCULATION = new Calculation(
            "67424c12636a0724de2da25b",
            "igor",
            "igor@gmail.com",
            "11974949441",
            "uf",
            500,
            List.of(new TransportationDTO(TransportationType.CAR, 1000)),
            500,
            0.5
    );

    public static final CarbonCalculationResultDTO CARBON_CALC_RESULT_DTO = new CarbonCalculationResultDTO(
         500,
         500,
         500,
         1500
    );

    public static final StartCalcRequestDTO START_CALC_REQUEST_DTO = new StartCalcRequestDTO(
            "igor",
            "igor@gmail.com",
            "SP",
            "123456"
    );

    public static final UpdateCalcInfoRequestDTO UPDATE_CALC_INFO_REQUEST_DTO = new UpdateCalcInfoRequestDTO(
            "67424c12636a0724de2da25b",
            500,
            List.of(new TransportationDTO(TransportationType.CAR, 1000)),
            500,
            0.5
    );

    public static final EnergyEmissionFactor ENERGY_EMISSION_FACTOR = new EnergyEmissionFactor("SP", 0.7);

    public static final SolidWasteEmissionFactor SOLID_WASTE_EMISSION_FACTOR = new SolidWasteEmissionFactor("RJ", 0.3, 0.7);

    public static final TransportationEmissionFactor CAR_EMISSION_FACTOR = new TransportationEmissionFactor(TransportationType.CAR, 0.5);
    public static final TransportationEmissionFactor PUBLIC_TRANSPORT_EMISSION_FACTOR = new TransportationEmissionFactor(TransportationType.PUBLIC_TRANSPORT, 0.7);

    public static final List<TransportationDTO> TRANSPORTATION_DTO_LIST = List.of(
            new TransportationDTO(TransportationType.CAR, 1000),
            new TransportationDTO(TransportationType.PUBLIC_TRANSPORT, 1500)
    );


}
