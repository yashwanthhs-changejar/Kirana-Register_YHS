package Kirana_stores_yhs.astra.Controller;

import Kirana_stores_yhs.astra.Services.RateConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

public class RateConversionController {
    @Autowired
    private RateConversionService rateConversionService;
    @GetMapping("/convertCurrency")
    @ResponseBody
    public String convertCurrency(@RequestParam String currency, @RequestParam BigDecimal amount) {
        BigDecimal exchangeRate = rateConversionService.RateConversion();
        BigDecimal convertedAmount = amount.multiply(exchangeRate);
        return "Converted amount in " + currency + ": " + convertedAmount;
    }
}
