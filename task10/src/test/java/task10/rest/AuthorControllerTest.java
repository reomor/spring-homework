package task10.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import task10.domain.Author;
import task10.repository.AuthorRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
// to test with the full context
//@SpringBootTest
//@AutoConfigureMockMvc
//to test without the whole context
@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {
    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testListPage() throws Exception {
        Author author = new Author(null, "Test", "Sername", LocalDate.now(), "biography");
        List<Author> authors = Collections.singletonList(author);
        given(authorRepository.findAll())
                .willReturn(Collections.singletonList(author));
        mockMvc.perform(get("/author"))
                .andDo(print())
                .andExpect(view().name("author/list"))
                .andExpect(model().attribute("authors", authors))
                .andExpect(status().isOk());
    }

    @Test
    public void testEditPage() throws Exception {
        Author author = new Author("1", "Test", "Sername", LocalDate.now(), "biography");
        given(authorRepository.findById("1")).willReturn(Optional.of(author));
        mockMvc.perform(get("/author/edit?id=1"))
                .andDo(print())
                .andExpect(view().name("author/edit"))
                .andExpect(model().attribute("author", author))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletePage() throws Exception {
        Mockito.doNothing().when(authorRepository).deleteById("1");
        mockMvc.perform(get("/author/delete?id=1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/author"));
    }

    @Test
    public void testViewPage_paramId() throws Exception {
        Author author = new Author("1", "Test", "Sername", LocalDate.now(), "biography");
        given(authorRepository.findById("1")).willReturn(Optional.of(author));
        mockMvc.perform(get("/author/view?id=1"))
                .andDo(print())
                .andExpect(view().name("author/view"))
                .andExpect(model().attribute("action", "/author?id=1"))
                .andExpect(model().attribute("author", author))
                .andExpect(status().isOk());
    }

    @Test
    public void testViewPage_paramIdAndEdit() throws Exception {
        Author author = new Author("1", "Test", "Sername", LocalDate.now(), "biography");
        given(authorRepository.findById("1")).willReturn(Optional.of(author));
        mockMvc.perform(get("/author/view?id=1&edit=true"))
                .andDo(print())
                .andExpect(view().name("author/view"))
                .andExpect(model().attribute("action", "/author?id=1"))
                .andExpect(model().attribute("author", author))
                .andExpect(model().attribute("edit", true))
                .andExpect(status().isOk());
    }

    @Test
    public void testViewPage_paramNoId() throws Exception {
        Author author = new Author();
        mockMvc.perform(get("/author/view"))
                .andDo(print())
                .andExpect(view().name("author/view"))
                .andExpect(model().attribute("action", "/author"))
                .andExpect(model().attribute("author", author))
                .andExpect(model().attribute("edit", true))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostPage() throws Exception {
        Author author = new Author("1", "Test", "Sername", LocalDate.now(), "biography");
        // https://stackoverflow.com/questions/17143116/integration-testing-posting-an-entire-object-to-spring-mvc-controller
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        ObjectReader reader = objectMapper.readerFor(Map.class);
        Map<String, String> map = reader.readValue(objectMapper.writeValueAsString(author));
        map.forEach((key, value) -> multiValueMap.add(key, (value == null ? "" : value)));

        given(authorRepository.save(author)).willReturn(author);
        mockMvc.perform(post("/author").params(multiValueMap))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/author"));
    }
}