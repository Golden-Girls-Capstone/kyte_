package com.kyterescue.config;
import com.kyterescue.services.UserDetailsLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.kyterescue.services.UserDetailsLoader;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsLoader usersLoader;

    public SecurityConfig(UserDetailsLoader usersLoader) {
        this.usersLoader = usersLoader;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
//                         Pages that require authentication

                        .requestMatchers("/profile",  "/profile/edit", "/profile/edit/delete/{id}", "pet/review/{fosterId}").authenticated()

                        /* Pages that do not require authentication
                         * anyone can visit the home page, register, login, and view ads */

                        .requestMatchers("", "/", "/dashboard", "/landing", "/sign-up", "/login", "/browse", "/api/test", "pets/**", "/logout", "/about", "/api/data/default", "/api/data/search", "/api/data/types", "/api/token").permitAll()

                        // allow loading of static resources
                        .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                )
                /* Login configuration */
                .formLogin((login) -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard"))
                /* Logout configuration */
                .logout((logout) -> logout.logoutSuccessUrl("/logout"))  //CHANGED THIS LINE, ADDED LOGOUT
                .httpBasic(withDefaults());
        return http.build();
    }

}
