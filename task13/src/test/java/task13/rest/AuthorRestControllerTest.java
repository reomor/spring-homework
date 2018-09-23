package task13.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import task13.config.Security;
import task13.business.domain.Author;
import task13.business.repository.AuthorRepository;
import task13.service.UserService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// https://stackoverflow.com/questions/15203485/spring-test-security-how-to-mock-authentication

@RunWith(SpringRunner.class)
@WebMvcTest(
        value = AuthorRestController.class,
        includeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = Security.class)}
        )
// or instead of includeFilters
//@Import(Security.class)
public class AuthorRestControllerTest {

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "USER")
    public void givenAuthors_whenUserFindAllAuthors_thenStatus2xx() throws Exception {
        Author author1 = new Author("1", "Test1", "Sername1", LocalDate.now(), "biography1");
        Author author2 = new Author("2", "Test2", "Sername2", LocalDate.now(), "biography2");
        List<Author> authors = Arrays.asList(author1, author2);

        given(authorRepository.findAll()).willReturn(authors);

        mockMvc.perform(get("/rest/authors"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(writeValue(authors)));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getAuthor_whenUserFindAuthorById_thenStatus2xx() throws Exception {
        final String id = "1";
        Author author = new Author(id, "Test1", "Sername1", LocalDate.now(), "biography1");

        given(authorRepository.findById(id)).willReturn(Optional.of(author));

        mockMvc.perform(get("/rest/authors/" + id))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(writeValue(author)));
    }

    @Test
    public void getAuthor_whenWithoutCredentialsFindAuthorById_thenStatus3xx() throws Exception {
        final String id = "1";
        mockMvc.perform(get("/rest/authors/" + id))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postAuthor_whenAdminSaveNewAuthor_thenStatus2xx() throws Exception {
        Author author = new Author(null, "Test1", "Sername1", LocalDate.now(), "biography1");
        Author authorSaved = new Author("1", "Test1", "Sername1", LocalDate.now(), "biography1");

        given(authorRepository.save(author)).willReturn(authorSaved);

        mockMvc.perform(post("/rest/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(author)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(writeValue(authorSaved)));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void postAuthor_whenUserSaveNewAuthor_thenStatus4xx() throws Exception {
        Author author = new Author(null, "Test1", "Sername1", LocalDate.now(), "biography1");

        mockMvc.perform(post("/rest/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(author)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void putExistingAuthor_whenAdminUpdateAuthor_thenStatus2xx() throws Exception {
        final String id = "1";
        Author author = new Author(id, "Test1", "Sername1", LocalDate.now(), "biography1");
        given(authorRepository.findById(id)).willReturn(Optional.of(author));
        given(authorRepository.save(author)).willReturn(author);

        mockMvc.perform(put("/rest/authors/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(author)))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(writeValue(author)));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void putExistingAuthor_whenUserUpdateAuthor_thenStatus4xx() throws Exception {
        final String id = "1";
        Author author = new Author(id, "Test1", "Sername1", LocalDate.now(), "biography1");
        mockMvc.perform(put("/rest/authors/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(author)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteExistingAuthor_whenAdminDeleteAuthor_thenNoContent() throws Exception {
        final String id = "1";
        Author author = new Author(id, "Test1", "Sername1", LocalDate.now(), "biography1");
        Mockito.doNothing().when(authorRepository).deleteById(id);
        given(authorRepository.findById(id)).willReturn(Optional.of(author));
        mockMvc.perform(delete("/rest/authors/" + id))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void deleteExistingAuthor_whenUserDeleteAuthor_then4xx() throws Exception {
        final String id = "1";
        mockMvc.perform(delete("/rest/authors/" + id))
                .andExpect(status().isForbidden());
    }

    private <T> String writeValue(T obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Error while writing obj to JSON:\n" + obj, e);
        }
    }
}