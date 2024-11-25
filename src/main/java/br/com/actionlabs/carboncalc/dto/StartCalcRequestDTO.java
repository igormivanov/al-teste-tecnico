package br.com.actionlabs.carboncalc.dto;

import br.com.actionlabs.carboncalc.model.Calculation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "DTO for starting an calc")
public class StartCalcRequestDTO {

  @NotBlank(message = "Name cannot be blank")
  private String name;

  @NotBlank(message = "Email cannot be blank")
  @Email(message = "Email must be valid")
  private String email;

  @NotBlank(message = "UF cannot be blank")
  @Size(min = 2, message = "UF must be at least 2 characters long")
  private String uf;

  @NotBlank(message = "Phone number cannot be blank")
  private String phoneNumber;

}


