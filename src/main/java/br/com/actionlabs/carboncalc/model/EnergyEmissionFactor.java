package br.com.actionlabs.carboncalc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document("energyEmissionFactor")
public class EnergyEmissionFactor {
  @Id
  private String uf;
  private double factor;
}
