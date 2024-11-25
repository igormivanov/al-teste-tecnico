package br.com.actionlabs.carboncalc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document("solidWasteEmissionFactor")
public class SolidWasteEmissionFactor {
  @Id private String uf;
  private double recyclableFactor;
  private double nonRecyclableFactor;
}
