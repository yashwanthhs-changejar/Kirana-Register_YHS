package Kirana_stores_yhs.astra.Services;

import Kirana_stores_yhs.astra.Entity.UserInfo;
import Kirana_stores_yhs.astra.Repository.UserInforRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {

    @Autowired
    private UserInforRepositoy userInforRepositoy;


    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserInfoService(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    public String addUser(UserInfo userInfo){
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInforRepositoy.save(userInfo);
        return "User added to System";
    }


}
