package Kirana_stores_yhs.astra.Repository;

import Kirana_stores_yhs.astra.Entity.TransactionRegister;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<TransactionRegister, String> {
    List<TransactionRegister> findByDate(LocalDate date);
    List<TransactionRegister> findById(Long Id);
    List<TransactionRegister> findByName(String name);
}

