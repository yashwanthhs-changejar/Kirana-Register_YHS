package Kirana_stores_yhs.astra.Repository;

import Kirana_stores_yhs.astra.Entity.RateExchange;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateCoversionRepository extends MongoRepository<RateExchange, String> {
    // You can define additional methods here if needed
}
