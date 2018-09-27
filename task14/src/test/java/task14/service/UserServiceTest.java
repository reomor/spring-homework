package task14.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import task14.exception.UnauthorizedAuthority;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) //re-init base on each test
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private AclManager aclManager;

    @Autowired
    private JdbcMutableAclService aclService;

    // Register
    @Test
    @WithMockUser(roles = "ANONYMOUS")
    public void givenUser_registerUser() throws Exception {
        String password = "12345";
        User user = userService.register("Test", "x@x.x", password);
        User registeredUser = userService.findAndAuthenticate(user.getEmail(), password);
        assertNotNull(registeredUser);
        assertThat(registeredUser.getName()).isEqualTo(user.getName());
    }

    // Create
    @Test
    @WithMockUser(username = "b@b.b", roles = "UBER")
    public void givenUser_uberCreateAdmin() throws Exception {
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
    public void givenUser_adminCreateAdmin() throws Exception {
        User registeredUser = userService.createAdmin("Test", "x@x.x", "12345");
    }

    @Test
    @WithMockUser(username = "b@b.b", roles = "ADMIN")
    public void givenUser_adminCreateUser() throws Exception {
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
    public void givenUser_userCreateUser() throws Exception {
        User registeredUser = userService.createUser("Test", "x@x.x", "12345");
    }

    // Delete

    // findAll
    /*
    @Test
    @WithMockUser(roles = "USER")
    public void givenUser_findAllUsers() {
        List<User> all = userService.findAll();
        assertThat(all).isEmpty();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenAdmin_findAllUsers() {
        List<User> all = userService.findAll();
        assertThat(all).isNotEmpty();
    }

    @Test
    @WithMockUser(roles = "UBER")
    public void givenUber_findAllUsers() {
        List<User> all = userService.findAll();
        assertThat(all).isNotEmpty();
    }
    //*/
}