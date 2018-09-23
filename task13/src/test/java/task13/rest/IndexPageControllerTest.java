package task13.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import task13.config.Security;
import task13.business.dto.RegistrationFormDto;
import task13.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(
        value = IndexPageController.class,
        includeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = Security.class)}
)
public class IndexPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(view().name("index"))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegisterValidData() throws Exception {
        RegistrationFormDto registrationFormDto = new RegistrationFormDto(
                "Name",
                "a@a.ru",
                "password",
                "password");
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(registrationFormDto)))
                .andDo(print())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void testRegisterInvalidData() throws Exception {
        RegistrationFormDto registrationFormDto = new RegistrationFormDto(
                "Name",
                "a_a.ru",
                "password",
                "1password");
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(registrationFormDto)))
                .andDo(print())
                .andExpect(view().name("registration/form"));
    }
}