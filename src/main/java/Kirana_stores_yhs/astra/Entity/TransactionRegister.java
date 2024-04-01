package Kirana_stores_yhs.astra.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "TransactionRegister")
public class TransactionRegister {
    @Id
    private String id;

    private String name;
    private String description;
    private double paymentAmount;
    private String paymentCurrency;
    private String conversionCurrency;
    private double convertedAmount;

    private LocalDate date;
}
