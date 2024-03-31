package Kirana_stores_yhs.astra.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "TransactionRegister")
public class TransactionRegister {
    @Id
    private String id;

    private String name;
    private String description;
    private String credit_amount;
    private String debit_amount;

    private LocalDate date;
}
