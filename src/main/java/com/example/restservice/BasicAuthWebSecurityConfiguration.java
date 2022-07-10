package com.example.restservice;

import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
public class BasicAuthWebSecurityConfiguration
{
    @Autowired
    private UsersCredentials usersCredentials;
    @Autowired
    private AppBasicAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/camel/ilya").permitAll()
                .anyRequest().fullyAuthenticated()
                .and()
                .csrf()
                .disable()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint);
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
/*        Logger logger=Logger.getLogger(RestServiceApplication.class);
        ConsoleAppender c = new ConsoleAppender();
        c.setName("CONSOLE_ERR");
        c.setTarget("System.out");
        c.setThreshold(Priority.INFO);
        c.setLayout(new PatternLayout("[%d{ISO8601}][%-5p][%t][%c{1}] %m%n"));
        c.activateOptions();
        logger.addAppender(c);
        logger.info("n="+usersCredentials.getUsersNumber()+" user1="+usersCredentials.getUserCredentials(1).toString());*/
        List<UserDetails> users1 = new ArrayList<>();
        int ii;
        for(ii=1; ii<=usersCredentials.getUsersNumber(); ii++) {
            Map<String, String> userCredentials = usersCredentials.getUserCredentials(ii);
            UserDetails ud = User.withUsername(userCredentials.get("name")).password(userCredentials.get("password")).roles(userCredentials.get("roles")).build();
            users1.add(ud);
        }
/*        UserDetails user2 = User
                .withUsername("test")
                .password("$2a$08$Zgk8t0pLmkG1Jm0y3Tu/oOPetks8l/lBkBWAu/O/4uFlE6rSxZFVW")
                .roles("USER_ROLE")
                .build();
        users1.add(user1);
        users1.add(user2);*/
        return new InMemoryUserDetailsManager(users1);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}
