package Kirana_stores_yhs.astra.Services;


import Kirana_stores_yhs.astra.Entity.TransactionRegister;
import Kirana_stores_yhs.astra.Repository.TransactionRepository; //  MongoDB repository interface is named TransactionRepository
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService { // Renamed to follow Java naming conventions
    @Autowired
    private TransactionRepository transactionRepository; // Injecting MongoDB repository interface
    private List<TransactionRegister> transactions = new ArrayList<>();
    public TransactionRegister saveTransaction(TransactionRegister transactionRegister, String currency) {
        System.out.println(RateConversionService.RateConversion());
        System.out.println(currency);

        try {
            if ("USD".equals(currency)) { // Simplified currency check
                System.out.println(RateConversionService.RateConversion());
                convertAmounts(transactionRegister);
            }
            return transactionRepository.save(transactionRegister);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save transaction", e);
        }
    }

    private void convertAmounts(TransactionRegister transactionRegister) {
        BigDecimal usdRate = RateConversionService.RateConversion();

        if (transactionRegister.getCredit_amount() == null || transactionRegister.getCredit_amount().isEmpty())
            transactionRegister.setCredit_amount("0.00");

        BigDecimal creditAmount = new BigDecimal(transactionRegister.getCredit_amount()).multiply(usdRate);
        transactionRegister.setCredit_amount(creditAmount.toString());

        if (transactionRegister.getDebit_amount() == null || transactionRegister.getDebit_amount().isEmpty())
            transactionRegister.setDebit_amount("0.00");

        BigDecimal debitAmount = new BigDecimal(transactionRegister.getDebit_amount()).multiply(usdRate);
        transactionRegister.setDebit_amount(debitAmount.toString());
    }

    public List<TransactionRegister> getAllTransactionsByDate(LocalDate date) {
        return transactionRepository.findByDate(date);
    }

    public Map<LocalDate, List<TransactionRegister>> getDailyReports() {
        List<TransactionRegister> allTransactions = transactionRepository.findAll();
        return allTransactions.stream()
                .collect(Collectors.groupingBy(TransactionRegister::getDate));
    }

    public void updateTransaction(Long id, TransactionRegister transactionRegister) {
        Optional<TransactionRegister> existingTransactionOptional = transactionRepository.findById(String.valueOf(id));
        if (existingTransactionOptional.isPresent()) {
            TransactionRegister existingTransaction = existingTransactionOptional.get();
            BeanUtils.copyProperties(transactionRegister, existingTransaction, "id");
            transactionRepository.save(existingTransaction);
        } else {
            throw new RuntimeException("Transaction with ID " + id + " not found");
        }
    }


    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(String.valueOf(id));
    }

    public List<TransactionRegister> getAllTransactionsById(Long Id) {
        return transactionRepository.findById(Id);
    }
    public TransactionRegister getTransactionByName(String Name) {
        List<TransactionRegister> transactions = transactionRepository.findByName(Name);
        if (!transactions.isEmpty()) {
            return transactions.get(0); // Assuming the first transaction is the one you want
        } else {
            return null; // Or throw an exception if not found
        }
    }

    public List<TransactionRegister> getAllTransactions() {
        return transactionRepository.findAll();
    }


}

