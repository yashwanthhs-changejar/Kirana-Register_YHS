package Kirana_stores_yhs.astra.Controller;

import Kirana_stores_yhs.astra.Entity.TransactionRegister;
import Kirana_stores_yhs.astra.Repository.TransactionRepository;
import Kirana_stores_yhs.astra.Services.RateConversionService;
import Kirana_stores_yhs.astra.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/kiranaRegisterYHS")

public class TransactionController {


    private final TransactionService transactionService;

    private final TransactionRepository transactionRepository;

    private final RateConversionService rateConversionService;

    @Autowired
    public TransactionController(TransactionService transactionService, TransactionRepository transactionRepository, RateConversionService rateConversionService) {
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
        this.rateConversionService = rateConversionService;
    }

    @PostMapping("/getAllTransactions")
    public List<TransactionRegister> getTransactionByDate(@RequestParam("date") String date){
        System.out.println("Date given" + date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        System.out.println("Date given" + localDate);
        return transactionService.getAllTransactionsByDate(localDate);
    }
//    @PostMapping("/save")
//    public ResponseEntity<?> saveTransaction(@RequestBody TransactionRegister customer) {
//        try {
//            TransactionRegister savedCustomer = transactionRepository.save(customer);
//            return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
//        } catch (Exception error) {
//            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
@PostMapping("/save")
public ResponseEntity<?> saveTransaction(@RequestBody TransactionRegister transactionRegister) {
    try {
        // Retrieve conversion rates
        double conversionRate = rateConversionService.getConversionRate(
                transactionRegister.getPaymentCurrency(),
                transactionRegister.getConversionCurrency()
        );

        // Perform currency conversion
        double paymentAmount = transactionRegister.getPaymentAmount();
        double convertedAmount = paymentAmount * conversionRate;
        transactionRegister.setConvertedAmount(convertedAmount);

        // Set other properties
        transactionRegister.setDate(LocalDate.now()); // Assuming date should be set to current date

        // Save transaction to repository
        TransactionRegister savedTransaction = transactionRepository.save(transactionRegister);
        return new ResponseEntity<>(savedTransaction, HttpStatus.OK);
    } catch (Exception error) {
        return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


    @GetMapping("/changeRates/{id}")
    public ResponseEntity<String> changeRates(@PathVariable String id) {
        Optional<TransactionRegister> optionalTransaction = transactionRepository.findById(id);
        if (optionalTransaction.isPresent()) {
            TransactionRegister transaction = optionalTransaction.get();
            double paymentAmount = transaction.getPaymentAmount();
            String paymentCurrency = transaction.getPaymentCurrency();
            String conversionCurrency = transaction.getConversionCurrency();
            double convertedAmount = transaction.getConvertedAmount();

            if (Double.toString(paymentAmount) != null) {
                // Convert payment amount to conversion currency and update converted amount
                double conversionRate = rateConversionService.getConversionRate(paymentCurrency, conversionCurrency);
                double amount = paymentAmount;
                double updatedConvertedAmount = amount * conversionRate;
                transaction.setConvertedAmount(updatedConvertedAmount);
            } else {
                return new ResponseEntity<>("Payment amount is null", HttpStatus.BAD_REQUEST);
            }

            transactionRepository.save(transaction);
            return new ResponseEntity<>("Rates changed successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Transaction not found", HttpStatus.NOT_FOUND);
        }
    }
//    @GetMapping("/id")
//    public ResponseEntity<TransactionRegister> getTransactionById(@RequestParam Long id) {
//        TransactionRegister transaction = transactionService.getTransactionById(id);
//        if (transaction != null) {
//            return ResponseEntity.ok(transaction);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
@GetMapping ("/id/{id}")
//@PreAuthorize("hasAuthority(ROLE_USER)")
public ResponseEntity<List<TransactionRegister>> getTransactionsById(@PathVariable("id") String id) {
        System.out.println(MessageFormat.format("id :: {0}",id));
    List<TransactionRegister> lis = transactionService.getTransactionsById(id);
    return ResponseEntity.ok(lis);

}

    @GetMapping("/transactions")
//    @PreAuthorize("hasAuthority(ROLE_ADMIN)")
    public List<TransactionRegister> getAllTransactions() {
        System.out.println("endpoint hitttt");
        return transactionService.getAllTransactions();
    }

//    @GetMapping("/RateExchange")
//    public List<RateExchange> getRateForCurrency(@PathVariable("currency") String currency){
//        return rateResponse.getRateForCurrency(currency);
//    }

    @GetMapping("/name/{name}")
//    @PreAuthorize("hasAuthority(ROLE_USER)")
    public ResponseEntity<TransactionRegister> getTransactionByName(@PathVariable("name") String name) {
        TransactionRegister transaction = transactionService.getTransactionByName(name);
        if (transaction != null) {
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getDailyReports")
    public Map<LocalDate, List<TransactionRegister>> getDailyReports(){
        return transactionService.getDailyReports();
    }
    @PutMapping("/update/{id}")
    public void updateTransaction(@PathVariable Long id, @RequestBody TransactionRegister customerRegister) {
        transactionService.updateTransaction(id, customerRegister);

    }
    @DeleteMapping("/Delete/{id}")
    public void deleteCustomer(@PathVariable("id") String id) {
        transactionService.deleteTransaction(id);
    }
}