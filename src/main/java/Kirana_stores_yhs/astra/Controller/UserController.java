package Kirana_stores_yhs.astra.Controller;

import Kirana_stores_yhs.astra.Entity.AuthRequest;
import Kirana_stores_yhs.astra.Entity.UserInfo;
import Kirana_stores_yhs.astra.Services.JWTSerivce;
import Kirana_stores_yhs.astra.Services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/kiranaRegisterYHS")

public class UserController {

    private final UserInfoService userInfoService;

    @Autowired
    public UserController(UserInfoService userInfoService){
        this.userInfoService = userInfoService;
    }



    @Autowired
    private JWTSerivce jwtSerivce;

    @PostMapping("/newUser")
    public String addNewUser(@RequestBody UserInfo userInfo){
        return userInfoService.addUser(userInfo);
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest){
        return jwtSerivce.generateToken(authRequest.getUserName());
    }
}
