package task14.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import task14.acl.domain.User;
import task14.acl.domain.UserRoles;
import task14.exception.ObjectAlreadyExists;
import task14.acl.repository.UserRepository;
import task14.exception.ObjectNotFound;
import task14.exception.UnauthorizedAuthority;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AclManager aclManager;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder, AclManager aclManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.aclManager = aclManager;
    }

    abstract class AbstractUserBuilder {
        protected User user;
        public User getUser() {
            return user;
        }
        void grantPermissions() throws UnauthorizedAuthority {
            try {
                grantAclPermissions(user);
            } catch (UnauthorizedAuthority unauthorizedAuthority) {
                userRepository.delete(user);
                throw unauthorizedAuthority;
            }
        }
    }

    class UserBuilder extends AbstractUserBuilder {
        UserBuilder(String name, String email, String password) throws UnauthorizedAuthority {
            this.user = create(name, email, password, UserRoles.ROLE_USER);
            grantPermissions();
        }
    }

    class AdminBuilder extends AbstractUserBuilder {
        AdminBuilder(String name, String email, String password) throws UnauthorizedAuthority {
            this.user = create(name, email, password, UserRoles.ROLE_ADMIN, UserRoles.ROLE_USER);
            grantPermissions();
        }
    }

    public User createAdmin(String name, String email, String password) throws UnauthorizedAuthority {
        return new AdminBuilder(name, email, password).getUser();
    }

    public User createUser(String name, String email, String password) throws UnauthorizedAuthority {
        return new UserBuilder(name, email, password).getUser();
    }

    public User register(String name, String email, String password) {
        User user = create(name, email, password, UserRoles.ROLE_USER);
        grantAclPermissionsToRegisteredUser(user);
        return user;
    }

    private User create(String name, String email, String password, UserRoles role, UserRoles... roles) {
        if (Objects.nonNull(userRepository.findOneByEmail(email))) {
            throw new ObjectAlreadyExists("User with this email has been registered before");
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

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(ObjectNotFound::new);
    }

    public void delete(User user) throws UnauthorizedAuthority {
        GrantedAuthoritySid sid = getSuperiorCurrentUserSid();
        userRepository.delete(user);
        aclManager.delAllPermissions(User.class, user.getId(), sid);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    private void grantAclPermissions(User user) throws UnauthorizedAuthority {
        GrantedAuthoritySid sid = getSuperiorCurrentUserSid();
        aclManager.addPermission(User.class, user.getId(), sid, BasePermission.READ, BasePermission.DELETE, BasePermission.ADMINISTRATION);
        aclManager.addPermission(User.class, user.getId(), new PrincipalSid(user.getEmail()), BasePermission.READ, BasePermission.WRITE);
    }

    private void grantAclPermissionsToRegisteredUser(User user) {
        aclManager.addPermission(User.class, user.getId(), new GrantedAuthoritySid(UserRoles.ROLE_ADMIN), BasePermission.READ, BasePermission.DELETE, BasePermission.ADMINISTRATION);
        aclManager.addPermission(User.class, user.getId(), new PrincipalSid(user.getEmail()), BasePermission.READ, BasePermission.WRITE);
    }

    private GrantedAuthoritySid getSuperiorCurrentUserSid() throws UnauthorizedAuthority {
        GrantedAuthoritySid sid;
        if (containsRole(UserRoles.ROLE_UBER)) {
            sid = new GrantedAuthoritySid(UserRoles.ROLE_UBER);
        } else if (containsRole(UserRoles.ROLE_ADMIN)) {
            sid = new GrantedAuthoritySid(UserRoles.ROLE_ADMIN);
        } else {
            throw new UnauthorizedAuthority("Current user doesn't have authority to get ACL permissions");
        }
        return sid;
    }

    private boolean containsRole(UserRoles role) {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority next : authorities) {
            if (next.getAuthority().equals(role.getAuthority())) {
                return true;
            }
        }
        return false;
    }
}
