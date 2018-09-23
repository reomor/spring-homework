package task13.stepdefs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.ru.Дано;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Тогда;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.UserRequestPostProcessor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import task13.config.Security;
import task13.business.domain.Author;
import task13.business.repository.AuthorRepository;
import task13.rest.AuthorRestController;
import task13.service.UserService;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(
        value = AuthorRestController.class,
        includeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = Security.class)}
)
public class RestStepDefs {

    @MockBean
    private UserService userService;

    @Inject
    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ResultActions resultActions;
    private UserRequestPostProcessor currentUser;

    @Дано("REST-контроллер для авторов")
    public void restКонтроллерДляАвторов() {
        final String id = "1";
        Author author = new Author(id, "Test1", "Sername1", LocalDate.now(), "biography1");
        given(authorRepository.findById(id)).willReturn(Optional.of(author));

        Author author1 = new Author("1", "Test1", "Sername1", LocalDate.now(), "biography1");
        Author author2 = new Author("2", "Test2", "Sername2", LocalDate.now(), "biography2");
        List<Author> authors = Arrays.asList(author1, author2);
        given(authorRepository.findAll()).willReturn(authors);

        Author authorNew = new Author(null, "Test1", "Sername1", LocalDate.now(), "biography1");
        Author authorSaved = new Author("1", "Test1", "Sername1", LocalDate.now(), "biography1");
        given(authorRepository.save(authorNew)).willReturn(authorSaved);

        Mockito.doNothing().when(authorRepository).deleteById(id);
    }

    @Когда("запрос от USER")
    public void запросОтUSER() {
        currentUser = user("User").roles("USER");
        // admin = user("Admin").roles("ADMIN");
    }

    @Когда("метод GET url {string}")
    public void методGETUrl(String URI) throws Exception {
        resultActions = mockMvc.perform(get(URI).with(currentUser));
    }

    @Когда("метод POST url {string}")
    public void методPOSTUrl(String URI) throws Exception {
        resultActions = mockMvc.perform(post(URI).with(currentUser));
    }

    @Когда("метод PUT url {string}")
    public void методPUTUrl(String URI) throws Exception {
        resultActions = mockMvc.perform(put(URI).with(currentUser));
    }

    @Когда("метод DELETE url {string}")
    public void методDELETEUrl(String URI) throws Exception {
        resultActions = mockMvc.perform(delete(URI).with(currentUser));
    }

    @Тогда("статус {int}")
    public void статус(Integer status) throws Exception {
        resultActions.andExpect(status().is(status));
    }

    private <T> String writeValue(T obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Error while writing obj to JSON:\n" + obj, e);
        }
    }
}
