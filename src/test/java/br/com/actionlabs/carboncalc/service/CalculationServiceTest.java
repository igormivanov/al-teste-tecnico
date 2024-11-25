package br.com.actionlabs.carboncalc.service;

import br.com.actionlabs.carboncalc.dto.*;
import br.com.actionlabs.carboncalc.enums.TransportationType;
import br.com.actionlabs.carboncalc.model.Calculation;
import br.com.actionlabs.carboncalc.repository.CalculationRepository;
import br.com.actionlabs.carboncalc.services.Calculation.CalculationServiceImpl;
import br.com.actionlabs.carboncalc.services.CarbonEmissionCalculator.CarbonEmissionCalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static br.com.actionlabs.carboncalc.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CalculationServiceTest {

    @Mock
    private CalculationRepository calculationRepository;

    @Mock
    private CarbonEmissionCalculatorService carbonEmissionCalculatorService;

    @InjectMocks
    private CalculationServiceImpl calculationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create calculation successfully when data is valid")
    void createCalculation_ShouldCreateCalculationAndReturnSuccess_WhenDataIsValid() throws Exception {

        doReturn(CALCULATION).when(calculationRepository).save(any());

        var response = calculationService.createCalculation(START_CALC_REQUEST_DTO);

        assertNotNull(response);
        assertEquals(CALCULATION.getId(), response.getId());
        verify(calculationRepository, times(1)).save(any(Calculation.class));
    }


    @Test
    @DisplayName("Should update calculation successfully when data is valid")
    void updateCalculation_ShouldUpdateAndReturnSuccess_WhenDataIsValid() {

        doReturn(Optional.of(CALCULATION)).when(calculationRepository).findById((UPDATE_CALC_INFO_REQUEST_DTO.getId()));
        doReturn(CALCULATION).when(calculationRepository).save(any(Calculation.class));

        UpdateCalcInfoResponseDTO response = calculationService.updateCalculation(UPDATE_CALC_INFO_REQUEST_DTO);

        assertNotNull(response);
        assertTrue(response.isSuccess());
        verify(calculationRepository, times(1)).findById(UPDATE_CALC_INFO_REQUEST_DTO.getId());
        verify(calculationRepository, times(1)).save(any(Calculation.class));
    }


    @Test
    @DisplayName("Should return false when calculation is not found")
    void updateCalculation_ShouldReturnFailure_WhenCalculationNotFound() {

        doReturn(Optional.empty()).when(calculationRepository).findById(UPDATE_CALC_INFO_REQUEST_DTO.getId());

        UpdateCalcInfoResponseDTO response = calculationService.updateCalculation(UPDATE_CALC_INFO_REQUEST_DTO);

        assertNotNull(response);
        assertFalse(response.isSuccess());
        verify(calculationRepository, times(1)).findById(UPDATE_CALC_INFO_REQUEST_DTO.getId());
        verify(calculationRepository, never()).save(any(Calculation.class));
    }

    @Test
    @DisplayName("Should create calculation successfully when everything is OK")
    void getCarbonCalculationResult_ShouldReturnResult_WhenCalculationExists() {

        doReturn(Optional.of(CALCULATION)).when(calculationRepository).findById(CALCULATION.getId());

        doReturn(705.0).when(carbonEmissionCalculatorService).calculateEnergyEmission(anyInt(), anyString());
        doReturn(500.0).when(carbonEmissionCalculatorService).calculateTransportationEmission(anyList());
        doReturn(500.0).when(carbonEmissionCalculatorService).calculateSolidWasteEmission(
                anyInt(),
                anyDouble(),
                anyString()
        );

        CarbonCalculationResultDTO result = calculationService.getCarbonCalculationResult(CALCULATION.getId());

        assertNotNull(result);
        assertEquals(705.0, result.getEnergy());
        assertEquals(500.0, result.getTransportation());
        assertEquals(500.0, result.getSolidWaste());
        assertEquals(1705.0, result.getTotal());
        verify(calculationRepository, times(1)).findById(CALCULATION.getId());
    }

}


