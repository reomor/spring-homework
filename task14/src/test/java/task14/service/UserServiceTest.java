package task14.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import task14.acl.domain.User;
import task14.acl.domain.UserRoles;
import task14.exception.ObjectNotFound;
import task14.exception.UnauthorizedAuthority;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) //re-init base on each test
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private JdbcMutableAclService aclService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static long UBER_ID = 1L;
    private static long ADMIN_ID = 2L;
    private static long USER_ID = 3L;

    // Register
    @Test
    @WithMockUser(roles = "ANONYMOUS")
    public void givenAnonymousRole_shouldRegisterUser_thenSuccess() throws Exception {
        String password = "12345";
        User user = userService.register("Test", "x@x.x", password);
        User registeredUser = userService.findAndAuthenticate(user.getEmail(), password);
        assertNotNull(registeredUser);
        assertThat(registeredUser.getName()).isEqualTo(user.getName());
    }

    // Create
    @Test
    @WithMockUser(username = "b@b.b", roles = "UBER")
    public void givenUberRole_shouldCreateAdmin_thenSuccess() throws Exception {
        User registeredUser = userService.createAdmin("Test", "x@x.x", "12345");
        ObjectIdentity identity = new ObjectIdentityImpl(User.class, registeredUser.getId());
        Acl acl = aclService.readAclById(identity);
        assertThat(acl.isGranted(
                Arrays.asList(BasePermission.READ, BasePermission.DELETE, BasePermission.ADMINISTRATION),
                Collections.singletonList(new GrantedAuthoritySid(UserRoles.ROLE_UBER)),
                false)
        ).isTrue();
    }

    @Test(expected = UnauthorizedAuthority.class)
    @WithMockUser(username = "b@b.b", roles = "ADMIN")
    public void givenAdminRole_tryCreateAdmin_thenException() throws Exception {
        userService.createAdmin("Test", "x@x.x", "12345");
    }

    @Test
    @WithMockUser(username = "b@b.b", roles = "ADMIN")
    public void givenAdminRole_shouldCreateUser_thenSuccess() throws Exception {
        User registeredUser = userService.createUser("Test", "x@x.x", "12345");
        ObjectIdentity identity = new ObjectIdentityImpl(User.class, registeredUser.getId());
        Acl acl = aclService.readAclById(identity);
        assertThat(acl.isGranted(
                Arrays.asList(BasePermission.READ, BasePermission.DELETE, BasePermission.ADMINISTRATION),
                Collections.singletonList(new GrantedAuthoritySid(UserRoles.ROLE_ADMIN)),
                false)
        ).isTrue();
    }

    @Test(expected = UnauthorizedAuthority.class)
    @WithMockUser(roles = "USER")
    public void givenUserRole_tryCreateUser_thenException() throws Exception {
        userService.createUser("Test", "x@x.x", "12345");
    }

    // Delete
    @Test
    @WithMockUser(username = "b@b.b", roles = "ADMIN")
    public void givenAdminRole_shouldDeleteUser_thenSuccess() throws Exception {
        Long rowsBeforeCreate = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ACL_ENTRY", Long.class);
        User registeredUser = userService.createUser("Test", "x@x.x", "12345");
        Long rowsAfterCreate = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ACL_ENTRY", Long.class);
        userService.delete(registeredUser);
        Optional<User> byId = userService.findById(registeredUser.getId());
        assertThat(byId.isPresent()).isFalse();
        Long rowsAfterDelete = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ACL_ENTRY", Long.class);
        assertThat(rowsBeforeCreate).isEqualTo(rowsAfterDelete);
        assertThat(rowsAfterCreate).isNotEqualTo(rowsAfterDelete);
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(username = "b@b.b", roles = "ADMIN")
    public void givenAdminRole_tryDeleteAdmin_thenException() throws Exception {
        User admin = userService.findById(ADMIN_ID).orElseThrow(ObjectNotFound::new);
        userService.delete(admin);
    }

    @Test
    @WithMockUser(username = "b@b.b", roles = "UBER")
    public void givenUberRole_shouldDeleteAdmin_thenSuccess() throws Exception {
        User admin = userService.findById(ADMIN_ID).orElseThrow(ObjectNotFound::new);
        Long rowsBeforeDelete = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ACL_ENTRY", Long.class);
        userService.delete(admin);
        Long rowsAfterDelete = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ACL_ENTRY", Long.class);
        assertThat(rowsAfterDelete).isNotEqualTo(rowsBeforeDelete);
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(username = "b@b.b", roles = "UBER")
    public void givenUberRole_tryDeleteHimself_thenException() throws Exception {
        User uber = userService.findById(UBER_ID).orElseThrow(ObjectNotFound::new);
        userService.delete(uber);
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(username = "b@b.b", roles = "UBER")
    public void givenUberRole_tryDeleteUser_thenException() throws Exception {
        User user = userService.findById(USER_ID).orElseThrow(ObjectNotFound::new);
        userService.delete(user);
    }

    //findById
    @Test
    @WithMockUser(username = "root", roles = "UBER")
    public void givenUberRole_shouldFindByIdAdmin_thenSuccess() throws Exception {
        Optional<User> userOptional = userService.findById(ADMIN_ID);
        assertThat(userOptional.isPresent()).isTrue();
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(username = "root", roles = "UBER")
    public void givenUberRole_tryFindByIdUser_thenException() throws Exception {
        userService.findById(USER_ID);
    }

    @Test
    @WithMockUser(username = "adm@a.ru", roles = "ADMIN")
    public void givenAdminRole_shouldFindByIdUser_thenSuccess() throws Exception {
        Optional<User> userOptional = userService.findById(USER_ID);
        assertThat(userOptional.isPresent()).isTrue();
    }

    @Test
    @WithMockUser(username = "usr@a.ru", roles = "USER")
    public void givenUserRole_shouldFindByIdHimself_thenSuccess() throws Exception {
        Optional<User> userOptional = userService.findById(USER_ID);
        assertThat(userOptional.isPresent()).isTrue();
    }

    // findAll

    @Test
    @WithMockUser(roles = "USER")
    public void givenUserRole_tryFindAllUsers_thenListIsOnlyWithHimself() {
        List<User> all = userService.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenAdminRole_tryFindAllUsers_thenFindOnlyAdminsAndUsers() {
        List<User> all = userService.findAll();
        assertThat(all.size()).isEqualTo(2);
    }

    @Test
    @WithMockUser(roles = "UBER")
    public void givenUberRole_tryFindAllUsers_thenFindOnlyUbersAndAdmins() {
        List<User> all = userService.findAll();
        assertThat(all.size()).isEqualTo(2);
    }
}