package task14.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import task14.acl.domain.User;
import task14.acl.domain.UserRoles;
import task14.exception.AlreadyExists;
import task14.acl.repository.UserRepository;
import task14.exception.ObjectNotFound;

import java.util.List;
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

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(ObjectNotFound::new);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
