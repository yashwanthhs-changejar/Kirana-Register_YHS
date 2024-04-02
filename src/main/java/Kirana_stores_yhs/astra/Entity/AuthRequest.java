package Kirana_stores_yhs.astra.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @Id
    private String userName;
    private String password;
}
