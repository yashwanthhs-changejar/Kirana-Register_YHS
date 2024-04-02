package Kirana_stores_yhs.astra.Repository;

import Kirana_stores_yhs.astra.Entity.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserInforRepositoy extends MongoRepository<UserInfo, String> {
    Optional<UserInfo> findByName(String username);
}
