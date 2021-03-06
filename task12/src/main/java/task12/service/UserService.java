package task12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import task12.domain.User;
import task12.domain.UserRoles;
import task12.exception.AlreadyExists;
import task12.repository.UserRepository;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // @Secured({"ROLE_ADMIN"})
    public User register(String name, String email, String password, UserRoles role, UserRoles ... roles) {
        if(Objects.nonNull(userRepository.findOneByEmail(email))) {
            throw new AlreadyExists("User with this email has been registered before");
        }
        String passwordSalt = UUID.randomUUID().toString();
        String passwordHash = passwordEncoder.encode(password + passwordSalt);
        User user = new User(name, email, passwordHash, passwordSalt, role, roles);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findOneByEmail(email);
    }

    public User findAndAuthenticate(String email, String providedPassword) {
        User user = userRepository.findOneByEmail(email);
        if (user == null) {
            return null;
        }
        String saltedPassword = providedPassword + user.getPasswordSalt();
        if (passwordEncoder.matches(saltedPassword, user.getPasswordHash())) {
            return user;
        }
        return null;
    }
}
