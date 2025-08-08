package spring.project.allegroshop.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.project.allegroshop.model.User;
import spring.project.allegroshop.repository.UserRepository;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String username, String rawPassword) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Użytkownik już istnieje");
        }
        String encoded = passwordEncoder.encode(rawPassword);
        User u = new User();
        u.setUsername(username);
        u.setPassword(encoded);
        return userRepository.save(u);
    }
}
