package br.com.actionlabs.carboncalc.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCalcInfoRequestDTO {

  @NotBlank(message = "Calculation ID cannot be blank")
  private String id;

  @Positive(message = "Energy consumption must be greater than 0")
  private int energyConsumption;

  @NotNull(message = "Transportation list cannot be null")
  @Size(min = 1, message = "At least one transportation entry is required")
  private List<TransportationDTO> transportation;

  @PositiveOrZero(message = "Solid waste production cannot be negative")
  private int solidWasteTotal;

  @DecimalMin(value = "0.0", inclusive = true, message = "Recycle percentage must be at least 0.0")
  @DecimalMax(value = "1.0", inclusive = true, message = "Recycle percentage cannot exceed 1.0")
  private double recyclePercentage;

}
