package br.com.actionlabs.carboncalc.model;

import br.com.actionlabs.carboncalc.enums.TransportationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document("transportationEmissionFactor")
public class TransportationEmissionFactor {
  @Id private TransportationType type;
  private double factor;
}
