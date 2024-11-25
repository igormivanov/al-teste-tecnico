package br.com.actionlabs.carboncalc.model;

import br.com.actionlabs.carboncalc.dto.TransportationDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "calculations")
public class Calculation {

    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String uf;

    private int energyConsumption;
    private List<TransportationDTO> transportation;
    private int solidWasteTotal;
    private double recyclePercentage;


}
