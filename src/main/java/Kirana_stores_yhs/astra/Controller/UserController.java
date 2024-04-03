package Kirana_stores_yhs.astra.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController{

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        System.out.println("this is also hit now");
        return ResponseEntity.ok("Hello general");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> sayHelloToAdmin() {
        System.out.println("endpoint hit");
        return ResponseEntity.ok("Hello Admin");
    }

    @GetMapping("/user")
    public ResponseEntity<String> sayHelloToUser() {
        return ResponseEntity.ok("Hello User");
    }
    @GetMapping("/admin-and-user")
    public ResponseEntity<String> sayHelloToUserAdmin() {
        return ResponseEntity.ok("Hello User-Admin");
    }
}
