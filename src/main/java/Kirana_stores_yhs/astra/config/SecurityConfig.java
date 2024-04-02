package Kirana_stores_yhs.astra.config;

import Kirana_stores_yhs.astra.Services.UserInfoUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/** Security configuration class for securing the Kirana application. */
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
@EnableWebSecurity

public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        return new UserInfoUserDetailsService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests()
                .requestMatchers("/kiranaRegisterYHS/newUser/**").permitAll()
                .requestMatchers("/kiranaRegisterYHS/save", "/kiranaRegisterYHS/authenticate", "/kiranaRegisterYHS/transaction").permitAll()
                .requestMatchers("/kiranaRegisterYHS/**").hasRole("USER")
                .and()
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
