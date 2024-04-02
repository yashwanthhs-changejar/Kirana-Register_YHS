package Kirana_stores_yhs.astra.Services;

import Kirana_stores_yhs.astra.Entity.UserInfo;
import Kirana_stores_yhs.astra.Repository.UserInforRepositoy;
import Kirana_stores_yhs.astra.config.UserInfoUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInforRepositoy userInforRepositoy;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userInforRepositoy.findByName(username);
        return userInfo.map(UserInfoUserDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("user not found"+username));
    }
}
