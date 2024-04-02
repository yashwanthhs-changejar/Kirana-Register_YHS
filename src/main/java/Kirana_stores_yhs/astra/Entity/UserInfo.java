package Kirana_stores_yhs.astra.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @Id
    @Generated
    private int Id;
    private String name;
    private String email;
    private String password;
    private String roles;
}
