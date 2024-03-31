package Kirana_stores_yhs.astra.Services;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RateResponse {
    /*
********************
  collecting response from API for currency conversion
  - https://api.fxratesapi.com/latest.

********************
* */
    private String base;
    private Map<String, BigDecimal> rates;

    public BigDecimal getRateForCurrency(String currency) {
        return rates.get(currency);
    }
}
