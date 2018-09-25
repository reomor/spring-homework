package task14.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import task14.acl.domain.User;

import java.util.List;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

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
}