package br.com.actionlabs.carboncalc.controller;

import br.com.actionlabs.carboncalc.dto.*;
import br.com.actionlabs.carboncalc.enums.TransportationType;
import br.com.actionlabs.carboncalc.rest.OpenRestController;
import br.com.actionlabs.carboncalc.services.Calculation.CalculationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static br.com.actionlabs.carboncalc.constants.Constants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OpenRestController.class)
public class OpenRestControllerTest {

    @MockBean
    private CalculationService calculationService;
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private OpenRestController openRestController;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return 201 Created when the calculation is successfully started")
    void startCalculation_ShouldReturnCreatedAndStartCalcResponseDTO_WhenSuccessful() throws Exception {

        mockMvc.perform(post("/open/start-calc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(START_CALC_REQUEST_DTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should return 200 OK when information is successfully updated")
    public void updateInfo_ShouldReturnOkAndTrue_WhenSuccessful() throws Exception {

        UpdateCalcInfoResponseDTO response = new UpdateCalcInfoResponseDTO(true);

        doReturn(response).when(calculationService).updateCalculation(UPDATE_CALC_INFO_REQUEST_DTO);

        mockMvc.perform(put("/open/info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UPDATE_CALC_INFO_REQUEST_DTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("Should return 400 Bad Request when updating information fails")
    public void updateInfo_ShouldReturnBadRequest_WhenFailure() throws Exception {

        UpdateCalcInfoResponseDTO response = new UpdateCalcInfoResponseDTO(false);

        doReturn(response).when(calculationService).updateCalculation(UPDATE_CALC_INFO_REQUEST_DTO);

        mockMvc.perform(put("/open/info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UPDATE_CALC_INFO_REQUEST_DTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }


    @Test
    @DisplayName("Should return 200 OK and the result when the ID is valid")
    public void getResult_ShouldReturnOk_WhenSuccessful() throws Exception {
        String validId = "67424c12636a0724de2da25b";

        doReturn(CARBON_CALC_RESULT_DTO).when(calculationService).getCarbonCalculationResult(validId);

        mockMvc.perform(get("/open/result/{id}", validId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.energy").value(CARBON_CALC_RESULT_DTO.getEnergy()));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when ID is invalid")
    public void getResult_ShouldThrowException_WhenIdIsInvalid() throws Exception {
        String invalidId = "";

        doThrow(new IllegalArgumentException("Invalid ID")).when(calculationService).getCarbonCalculationResult(invalidId);

        mockMvc.perform(get("/open/result/{id}", invalidId))
                .andExpect(status().isNotFound());
    }

}
