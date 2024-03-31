package Kirana_stores_yhs.astra.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "RateExchange")
public class RateExchange {

    @Id
    private String id;

    private String currency;
    private BigDecimal rate;
    private LocalDateTime timestamp;

    // Getters
    public String getId() {
        return id;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}


