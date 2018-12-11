package task10.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import task10.domain.Author;
import task10.repository.AuthorRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthorRestController.class)
public class AuthorRestControllerTest {

    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenAuthors_whenFindAllAuthors_thenStatus2xx() throws Exception {
        Author author1 = new Author("1", "Test1", "Sername1", LocalDate.now(), "biography1");
        Author author2 = new Author("2", "Test2", "Sername2", LocalDate.now(), "biography2");
        List<Author> authors = Arrays.asList(author1, author2);

        given(authorRepository.findAll()).willReturn(authors);

        mockMvc.perform(get("/rest/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(authors)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(writeValue(authors)));
    }

    @Test
    public void getAuthor_whenFindAuthorById_thenStatus2xx() throws Exception {
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
    public void postAuthor_whenSaveNewAuthor_thenStatus2xx() throws Exception {
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
    public void putExistingAuthor_whenUpdateAuthor_thenStatus2xx() throws Exception {
        final String id = "1";
        Author author = new Author(id, "Test1", "Sername1", LocalDate.now(), "biography1");
        given(authorRepository.findById(id)).willReturn(Optional.of(author));
        given(authorRepository.save(author)).willReturn(author);

        mockMvc.perform(put("/rest/authors/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(author)))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(writeValue(author)));
    }

    @Test
    public void putExistingAuthor_whenUpdateAuthor_thenError() throws Exception {
        final String id = "1";
        Author author = new Author(id, "Test1", "Sername1", LocalDate.now(), "biography1");
        given(authorRepository.findById(id)).willReturn(Optional.empty());

        mockMvc.perform(put("/rest/authors/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(author)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteExistingAuthor_whenDeleteAuthor_thenNoContent() throws Exception {
        final String id = "1";
        Author author = new Author(id, "Test1", "Sername1", LocalDate.now(), "biography1");
        Mockito.doNothing().when(authorRepository).deleteById(id);
        given(authorRepository.findById(id)).willReturn(Optional.of(author));
        mockMvc.perform(delete("/rest/authors/" + id))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    private <T> String writeValue(T obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Error while writing obj to JSON:\n" + obj, e);
        }
    }
}