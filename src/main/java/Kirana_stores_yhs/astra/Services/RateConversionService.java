package Kirana_stores_yhs.astra.Services;
//
//import Kirana_stores_yhs.astra.Entity.RateExchange;
//import Kirana_stores_yhs.astra.Repository.RateCoversionRepository;
//import Kirana_stores_yhs.astra.Services.RateResponse;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.time.LocalDateTime;
//
//@Getter
//@Setter
//@Service
//public class RateConversionService {
//    private static final String API_URL = "https://api.fxratesapi.com/latest";
//
//    private final RateCoversionRepository rateCoversionRepository;
//
//    @Autowired
//    public RateConversionService(RateCoversionRepository rateCoversionRepository) {
//        this.rateCoversionRepository = rateCoversionRepository;
//    }
//
//    public BigDecimal getRateForCurrency(String currency) {
//        try {
//            URI uri = URI.create(API_URL);
//            // Sending HTTP request
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(uri)
//                    .header("Content-Type", "application/json")
//                    .GET()
//                    .build();
//
//            HttpClient client = HttpClient.newHttpClient();
//
//            // Sending the request and receiving the response
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            RateResponse rateResponse = objectMapper.readValue(response.body(), RateResponse.class);
//
//            // Get the exchange rate for the specified currency
//            BigDecimal rate = rateResponse.getRateForCurrency(currency);
//
//            // Save exchange rate in MongoDB
//            if (rate != null && rateCoversionRepository != null) {
//                RateExchange rateExchange = new RateExchange();
//                rateExchange.setCurrency(currency);
//                rateExchange.setRate(rate);
//                rateExchange.setTimestamp(LocalDateTime.now());
//                rateCoversionRepository.save(rateExchange);
//            }
//
//            return rate;
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//            return BigDecimal.ZERO; // Handle the exception gracefully
//        }
//    }
//}
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.util.Map;


@Service
public class RateConversionService {

    private static final String API_URL = "https://api.fxratesapi.com/latest";

    @Autowired
    private RestTemplate restTemplate;

    // Method to retrieve conversion rate for a given currency
    public double getConversionRate(String baseCurrency, String targetCurrency) {
        String url = API_URL + "?base=" + baseCurrency + "&symbols=" + targetCurrency;
        RateResponse response = restTemplate.getForObject(url, RateResponse.class);

        // Extract the exchange rate for the target currency from the response
        assert response != null;
        Map<String, Double> rates = response.getRates();
        return rates.getOrDefault(targetCurrency, 0.0); // Return rate, or 0 if not found
    }

    // Method to convert amount from one currency to another
    public String convertCurrency(String paymentAmount, String paymentCurrency, String conversionCurrency) {
        double amount = Double.parseDouble(paymentAmount);
        double conversionRate = getConversionRate(paymentCurrency, conversionCurrency);
        double convertedAmount = amount * conversionRate;
        return String.valueOf(convertedAmount);
    }
}

//public class RateConversionService {
//
//    private static final String API_URL = "https://api.fxratesapi.com/latest";
//
//    // Method to retrieve conversion rate for USD
//    public String getUsdConversionRate() {
//        RestTemplate restTemplate = new RestTemplate();
//        Map<String, Object> exchangeRates = restTemplate.getForObject(API_URL, Map.class);
//        if (exchangeRates != null && exchangeRates.containsKey("rates")) {
//            Map<String, Object> rates = (Map<String, Object>) exchangeRates.get("rates");
//            if (rates.containsKey("USD")) {
//                Object rateValue = rates.get("USD");
//                return rateValue.toString(); // Convert rate value to string
//            }
//        }
//        return "0"; // Return zero as string if rate retrieval fails
//    }
//
//    // Method to retrieve conversion rate for INR
//    public String getInrConversionRate() {
//        RestTemplate restTemplate = new RestTemplate();
//        Map<String, Object> exchangeRates = restTemplate.getForObject(API_URL, Map.class);
//        if (exchangeRates != null && exchangeRates.containsKey("rates")) {
//            Map<String, Object> rates = (Map<String, Object>) exchangeRates.get("rates");
//            if (rates.containsKey("INR")) {
//                Object rateValue = rates.get("INR");
//                return rateValue.toString(); // Convert rate value to string
//            }
//        }
//        return "0"; // Return zero as string if rate retrieval fails
//    }
//
//    public double convertToINR(String currency, double amount) {
//        String API_URL = "https://api.fxratesapi.com/latest";
//        String url = API_URL + "?base=" + currency + "&symbols=INR";
//        ConversionResponse response = restTemplate.getForObject(url, ConversionResponse.class);
//
//        // Extract the exchange rate for INR from the response
//        assert response != null;
//        double exchangeRate = response.getRates().get("INR");
//
//        // Convert the amount to INR
//        return amount * exchangeRate;
//    }
//}
