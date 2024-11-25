package br.com.actionlabs.carboncalc.service;

import br.com.actionlabs.carboncalc.dto.TransportationDTO;
import br.com.actionlabs.carboncalc.enums.TransportationType;
import br.com.actionlabs.carboncalc.exceptions.handler.ResourceNotFoundException;
import br.com.actionlabs.carboncalc.model.SolidWasteEmissionFactor;
import br.com.actionlabs.carboncalc.model.TransportationEmissionFactor;
import br.com.actionlabs.carboncalc.repository.EnergyEmissionFactorRepository;
import br.com.actionlabs.carboncalc.repository.SolidWasteEmissionFactorRepository;
import br.com.actionlabs.carboncalc.repository.TransportationEmissionFactorRepository;
import br.com.actionlabs.carboncalc.services.CarbonEmissionCalculator.CarbonEmissionCalculatorService;
import br.com.actionlabs.carboncalc.services.CarbonEmissionCalculator.CarbonEmissionCalculatorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static br.com.actionlabs.carboncalc.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CarbonEmissionCalculatorServiceTest {

    @InjectMocks
    private CarbonEmissionCalculatorServiceImpl carbonEmissionCalculatorService;
    @Mock
    private EnergyEmissionFactorRepository energyEmissionFactorRepository;
    @Mock
    private SolidWasteEmissionFactorRepository solidWasteEmissionFactorRepository;
    @Mock
    private TransportationEmissionFactorRepository transportationEmissionFactorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return correct result when energy emission factor found")
    void calculateEnergyEmission_ShouldReturnCorrectResult_WheFactorFound() throws Exception {

        int energyConsumption = 500;
        String uf = "SP";

        doReturn(Optional.of(ENERGY_EMISSION_FACTOR)).when(energyEmissionFactorRepository).findById(ENERGY_EMISSION_FACTOR.getUf());

        double result = carbonEmissionCalculatorService.calculateEnergyEmission(energyConsumption, uf);

        assertEquals(energyConsumption * ENERGY_EMISSION_FACTOR.getFactor(), result);
        verify(energyEmissionFactorRepository, times(1)).findById(uf);
    }

    @Test
    @DisplayName("Should throw exception when energy emission factor not found")
    void calculateEnergyEmission_ShouldThrowException_WhenFactorNotFound() {

        int energyConsumption = 500;
        String invalidUf = "ZX";

        doReturn(Optional.empty()).when(energyEmissionFactorRepository).findById(invalidUf);

        assertThrows(ResourceNotFoundException.class, () -> {
            carbonEmissionCalculatorService.calculateEnergyEmission(energyConsumption, invalidUf);
        });
    }

    @Test
    @DisplayName("Should return correct result when transportation emission factor found")
    void calculateTransportationEmission_ShouldReturnCorrectResult_WhenFactorsFound() {

        double expectedCarEmission = TRANSPORTATION_DTO_LIST.get(0).getMonthlyDistance() * CAR_EMISSION_FACTOR.getFactor();
        double expectedPublicTransportEmission = TRANSPORTATION_DTO_LIST.get(1).getMonthlyDistance() * PUBLIC_TRANSPORT_EMISSION_FACTOR.getFactor();

        double expectedTotalEmission =  expectedCarEmission + expectedPublicTransportEmission;

        doReturn(Optional.of(CAR_EMISSION_FACTOR)).when(transportationEmissionFactorRepository).findById(TRANSPORTATION_DTO_LIST.get(0).getType());
        doReturn(Optional.of(PUBLIC_TRANSPORT_EMISSION_FACTOR)).when(transportationEmissionFactorRepository).findById(TRANSPORTATION_DTO_LIST.get(1).getType());

        double result = carbonEmissionCalculatorService.calculateTransportationEmission(TRANSPORTATION_DTO_LIST);

        assertEquals(expectedTotalEmission, result);
        verify(transportationEmissionFactorRepository, times(1)).findById(TRANSPORTATION_DTO_LIST.get(0).getType());
        verify(transportationEmissionFactorRepository, times(1)).findById(TRANSPORTATION_DTO_LIST.get(1).getType());
    }

    @Test
    @DisplayName("Should throw exception when transportation emission factor not found")
    void calculateTransportationEmission_ShouldThrowException_WhenFactorNotFound() {

        doReturn(Optional.empty()).when(transportationEmissionFactorRepository).findById(TRANSPORTATION_DTO_LIST.get(0).getType());

        assertThrows(ResourceNotFoundException.class, () -> {
            carbonEmissionCalculatorService.calculateTransportationEmission(TRANSPORTATION_DTO_LIST);
        });
    }

    @Test
    @DisplayName("Should return correct result when solid waste factor found")
    void calculateSolidWasteEmission_ShouldReturnCorrectResult_WhenFactorFound() {

        int solidWasteTotal = 1000;
        double recyclePercentage = 0.5;

        double recyclableEmission = solidWasteTotal * recyclePercentage * SOLID_WASTE_EMISSION_FACTOR.getRecyclableFactor();
        double nonRecyclableEmission = solidWasteTotal * (1 - recyclePercentage) * SOLID_WASTE_EMISSION_FACTOR.getNonRecyclableFactor();
        double expectedEmission = recyclableEmission + nonRecyclableEmission;

        doReturn(Optional.of(SOLID_WASTE_EMISSION_FACTOR)).when(solidWasteEmissionFactorRepository).findById(SOLID_WASTE_EMISSION_FACTOR.getUf());

        double result = carbonEmissionCalculatorService.calculateSolidWasteEmission(solidWasteTotal, recyclePercentage, SOLID_WASTE_EMISSION_FACTOR.getUf());

        assertEquals(expectedEmission, result);
        verify(solidWasteEmissionFactorRepository, times(1)).findById(SOLID_WASTE_EMISSION_FACTOR.getUf());
    }

    @Test
    @DisplayName("Should throw exception when solid waste emission factor not found")
    void calculateSolidWasteEmission_ShouldThrowException_WhenFactorNotFound() {

        int solidWasteTotal = 1000;
        double recyclePercentage = 0.5;
        String invalidUf = "SP";

        doReturn(Optional.empty()).when(solidWasteEmissionFactorRepository).findById(invalidUf);

        assertThrows(ResourceNotFoundException.class, () -> {
            carbonEmissionCalculatorService.calculateSolidWasteEmission(solidWasteTotal, recyclePercentage, invalidUf);
        });
    }
}
