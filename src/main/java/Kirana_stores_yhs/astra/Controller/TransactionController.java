package Kirana_stores_yhs.astra.Controller;

import Kirana_stores_yhs.astra.Entity.RateExchange;
import Kirana_stores_yhs.astra.Entity.TransactionRegister;
import Kirana_stores_yhs.astra.Repository.TransactionRepository;
import Kirana_stores_yhs.astra.Services.RateConversionService;
import Kirana_stores_yhs.astra.Services.RateResponse;
import Kirana_stores_yhs.astra.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/kiranaRegisterYHS")

public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RateResponse rateResponse;

    @PostMapping("/getAllTransactions")
    public List<TransactionRegister> getTransactionByDate(@RequestParam("date") String date){
        System.out.println("Date given" + date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        System.out.println("Date given" + localDate);
        return transactionService.getAllTransactionsByDate(localDate);
    }
    @PostMapping("/save")
    public ResponseEntity<?> saveCustomer(@RequestBody TransactionRegister customer) {
        try {
            TransactionRegister savedCustomer = transactionRepository.save(customer);
            return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
        } catch (Exception error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }en

    @GetMapping("/something")
    public String getSomething(){
        return "Something";
    }
    @GetMapping("/{id}")
    public ResponseEntity<TransactionRegister>getTransactionById(@PathVariable("id") Long id){
        var transaction = transactionService.getAllTransactionsById(id);
        return ResponseEntity.ok((TransactionRegister) transaction);
    }

    @GetMapping("/transactions")
    public List<TransactionRegister> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/RateExchange")
    public List<RateExchange> getRateForCurrency(@PathVariable("currency") String currency){
        return rateResponse.getRateForCurrency(currency);
    }

    @GetMapping("/name/{name}")
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

    @DeleteMapping("/delete/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }
}
