package task12.rest;

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
import task12.config.Security;
import task12.domain.Author;
import task12.domain.Book;
import task12.domain.Comment;
import task12.domain.Genre;
import task12.dto.BookDto;
import task12.repository.AuthorRepository;
import task12.repository.BookRepository;
import task12.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(
        value = BookRestController.class,
        includeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = Security.class)}
)
public class BookRestControllerTest {

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "USER")
    public void givenBooks_whenUserFindAllBooks_thenStatus2xx() throws Exception {
        Book book = getBook("1");
        List<Book> books = Arrays.asList(book, book);
        given(bookRepository.findAll()).willReturn(books);

        mockMvc.perform(get("/rest/books").contentType(MediaType.APPLICATION_JSON).content(writeValue(books)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(writeValue(books)));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenBook_whenUserFindBookById_thenStatus2xx() throws Exception {
        final String id = "1";
        Book book = getBook(id);
        Author author1 = new Author("1", "Test1", "Sername1", LocalDate.now(), "biography1");
        Author author2 = new Author("2", "Test2", "Sername2", LocalDate.now(), "biography2");
        List<Author> authors = Arrays.asList(author1, author2);
        List<String> authorIds = book.getAuthors().stream().map(Author::getId).collect(Collectors.toList());

        given(bookRepository.findById(id)).willReturn(Optional.of(book));
        given(authorRepository.findAll()).willReturn(authors);

        BookDto bookDto = new BookDto(book, authors, authorIds);

        mockMvc.perform(get("/rest/books/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(writeValue(bookDto)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenBook_whenAdminSaveNewBook_thenStatus2xx() throws Exception {
        Book book = getBook(null);
        Book bookSaved = getBook("1");
        List<String> authorIds = book.getAuthors().stream().map(Author::getId).collect(Collectors.toList());

        given(authorRepository.findById("1")).willReturn(Optional.of(getAuthor()));
        given(bookRepository.save(book)).willReturn(bookSaved);

        BookDto bookDto = new BookDto(book, Collections.emptyList(), authorIds);

        mockMvc.perform(post("/rest/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(bookDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(writeValue(bookSaved)));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenBook_whenUserSaveNewBook_thenStatus4xx() throws Exception {
        Book book = getBook(null);
        List<String> authorIds = book.getAuthors().stream().map(Author::getId).collect(Collectors.toList());

        BookDto bookDto = new BookDto(book, Collections.emptyList(), authorIds);

        mockMvc.perform(post("/rest/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(bookDto)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenBook_whenAdminUpdateBook_thenStatus2xx() throws Exception {
        final String id = "1";
        Book book = getBook(id);
        List<String> authorIds = book.getAuthors().stream().map(Author::getId).collect(Collectors.toList());

        given(authorRepository.findById("1")).willReturn(Optional.of(getAuthor()));
        given(bookRepository.save(book)).willReturn(book);

        BookDto bookDto = new BookDto(book, Collections.emptyList(), authorIds);

        mockMvc.perform(put("/rest/books/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(bookDto)))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(writeValue(book)));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenBook_whenUserUpdateBook_thenStatus4xx() throws Exception {
        final String id = "1";
        Book book = getBook(id);
        List<String> authorIds = book.getAuthors().stream().map(Author::getId).collect(Collectors.toList());

        BookDto bookDto = new BookDto(book, Collections.emptyList(), authorIds);

        mockMvc.perform(put("/rest/books/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(bookDto)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenId_whenAdminDeleteBook_thenNoContent() throws Exception {
        final String id = "1";
        Book book = getBook(id);

        Mockito.doNothing().when(bookRepository).deleteById(id);
        given(bookRepository.findById(id)).willReturn(Optional.of(book));

        mockMvc.perform(delete("/rest/books/" + id))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenId_whenUserDeleteBook_thenStatus4xx() throws Exception {
        final String id = "1";

        mockMvc.perform(delete("/rest/books/" + id))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenBook_whenUserComment_thenStatus2xx() throws Exception {
        final String id = "1";
        Comment comment = new Comment("New comment", LocalDateTime.now());

        Mockito.doNothing().when(bookRepository).setComment("1", comment);

        mockMvc.perform(post("/rest/books/" + id + "/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(comment)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenBook_whenUserFindAllComments_thenStatus2xx() throws Exception {
        final String id = "1";
        Book book = getBook(id);
        List<Comment> comments = book.getComments();
        given(bookRepository.findById(id)).willReturn(Optional.of(book));
        given(bookRepository.getComments(id)).willReturn(comments);

        mockMvc.perform(get("/rest/books/" + id + "/comments"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(writeValue(comments)));
    }

    private Author getAuthor() {
        return new Author("1", "Test1", "Sername1", LocalDate.now(), "biography1");
    }

    private Book getBook(String id) {
        Author author = getAuthor();
        Genre genre = new Genre("GenreMockName", "GenreMockDescription");
        Comment comment = new Comment("Random comment about", LocalDateTime.now());
        Comment comment2 = new Comment("Random comment about2", LocalDateTime.now());
        return new Book(id,"Title", genre, "978-3-16-148410-0", "Description", Collections.singletonList(author), Arrays.asList(comment, comment2));
    }

    private <T> String writeValue(T obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Error while writing obj to JSON:\n" + obj, e);
        }
    }
}