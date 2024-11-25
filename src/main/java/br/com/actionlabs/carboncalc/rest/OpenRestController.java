package br.com.actionlabs.carboncalc.rest;

import br.com.actionlabs.carboncalc.dto.*;
import br.com.actionlabs.carboncalc.services.Calculation.CalculationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/open")
@RequiredArgsConstructor
@Slf4j
public class OpenRestController {

  private final CalculationService calculationService;

  @PostMapping("start-calc")
  public ResponseEntity<StartCalcResponseDTO> startCalculation(
      @Valid @RequestBody StartCalcRequestDTO request) {
    StartCalcResponseDTO response = calculationService.createCalculation(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("info")
  public ResponseEntity<UpdateCalcInfoResponseDTO> updateInfo(
      @Valid @RequestBody UpdateCalcInfoRequestDTO request) {
    UpdateCalcInfoResponseDTO response = calculationService.updateCalculation(request);

    if(!response.isSuccess()) {
      return ResponseEntity.badRequest().body(response);
    }

    return ResponseEntity.ok(response);
  }

  @GetMapping("result/{id}")
  public ResponseEntity<CarbonCalculationResultDTO> getResult(@PathVariable String id) {
    if (id.isBlank()) {
      throw new IllegalArgumentException("The ID must not be null or empty.");
    }

    CarbonCalculationResultDTO result = calculationService.getCarbonCalculationResult(id);

    return ResponseEntity.ok(result);
  }
}
