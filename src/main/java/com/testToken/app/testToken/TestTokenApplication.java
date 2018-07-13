package com.testToken.app.testToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.testToken.app.testToken.config.CustomUserDetails;
import com.testToken.app.testToken.model.Role;
import com.testToken.app.testToken.model.User;
import com.testToken.app.testToken.repositories.UserRepository;
import com.testToken.app.testToken.service.UserService;

import java.util.Arrays;

@SpringBootApplication
public class TestTokenApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TestTokenApplication.class, args);
	}


	@Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository repository, UserService service) throws Exception {
        //Setup a default user if db is empty
        if (repository.count()==0)
            service.save(new User("user", "user", Arrays.asList(new Role("USER"), new Role("ACTUATOR"))));
        builder.userDetailsService(userDetailsService(repository)).passwordEncoder(passwordEncoder);
    }

    private UserDetailsService userDetailsService(final UserRepository repository) {
        return username -> new CustomUserDetails(repository.findByUsername(username));
    }
}
