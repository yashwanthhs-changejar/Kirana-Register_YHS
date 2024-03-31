package Kirana_stores_yhs.astra.Services;

import Kirana_stores_yhs.astra.Entity.RateExchange;
import Kirana_stores_yhs.astra.Repository.RateCoversionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

@Getter
@Setter
@Service
public class RateConversionService {
    private static final String API_URL = "https://api.fxratesapi.com/latest";

    @Autowired
    private static RateCoversionRepository rateCoversionRepository; // Your MongoDB repository for saving exchange rate data

    public static BigDecimal RateConversion() {
        try {
            URI uri = URI.create(API_URL);
            // Sending HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpClient client = HttpClient.newHttpClient();

            // Sending the request and receiving the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            RateResponse rateResponse = objectMapper.readValue(response.body(), RateResponse.class);

            // Get the exchange rate for INR
            BigDecimal inrRate = rateResponse.getRateForCurrency("INR");

            // Save exchange rate in MongoDB
            RateExchange rateExchange = new RateExchange();
            rateExchange.setCurrency("INR");
            rateExchange.setRate(inrRate);
            rateExchange.setTimestamp(LocalDateTime.now());
            rateCoversionRepository.save(rateExchange);

            return inrRate;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }
}
