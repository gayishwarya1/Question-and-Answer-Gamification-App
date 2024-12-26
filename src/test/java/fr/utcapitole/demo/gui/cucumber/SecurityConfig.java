package fr.utcapitole.demo.gui.cucumber;

import fr.utcapitole.demo.entities.User;
import fr.utcapitole.demo.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SecurityConfig {

    @Bean
    public User doo(PasswordEncoder encoder, UserRepository userRepository) {
        return userRepository.save(createUser(
                "john_doe",
                encoder.encode("foo"),
                "USER"));
    }

    @Bean
    public User api(PasswordEncoder encoder, UserRepository userRepository) {
        return userRepository.save(createUser(
                "api",
                encoder.encode("api"),
                "API"));
    }

    private User createUser(String username, String foo, String roles) {
        User doo = new User();
        doo.setUsername(username);
        doo.setPassword(foo);
        doo.setRoles(roles);
        return doo;
    }
}
