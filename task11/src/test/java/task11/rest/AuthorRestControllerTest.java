package task11.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import task11.domain.Author;
import task11.repository.AuthorRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    public void givenAuthors_whenFindAllAuthors_thenStatus200() throws Exception {
        Author author1 = new Author("1", "Test1", "Sername1", LocalDate.now(), "biography1");
        Author author2 = new Author("2", "Test2", "Sername2", LocalDate.now(), "biography2");
        List<Author> authors = Arrays.asList(author1, author2);

        given(authorRepository.findAll()).willReturn(authors);

        mockMvc.perform(get("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(authors)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    private <T> String writeValue(T obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Error while writing obj to JSON:\n" + obj, e);
        }
    }
}