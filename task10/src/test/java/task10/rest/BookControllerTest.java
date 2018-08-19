package task10.rest;

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
import task10.domain.Book;
import task10.domain.Comment;
import task10.domain.Genre;
import task10.dto.AuthorsDto;
import task10.repository.AuthorRepository;
import task10.repository.BookRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {
    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testListPage() throws Exception {
        Book book = getBook();
        List<Book> books = Collections.singletonList(book);
        given(bookRepository.findAll())
                .willReturn(Collections.singletonList(book));
        mockMvc.perform(get("/book"))
                .andDo(print())
                .andExpect(view().name("book/list"))
                .andExpect(model().attribute("books", books))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletePage() throws Exception {
        Mockito.doNothing().when(bookRepository).deleteById("1");
        mockMvc.perform(get("/book/delete?id=1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book"));
    }

    @Test
    public void testEditPage_withId() throws Exception {
        Author author = new Author("1", "Test", "Sername", LocalDate.now(), "biography");
        List<Author> authors = Collections.singletonList(author);
        given(authorRepository.findAll())
                .willReturn(Collections.singletonList(author));
        Book book = getBook();
        given(bookRepository.findById("1")).willReturn(Optional.of(book));
        mockMvc.perform(get("/book/edit?id=1"))
                .andDo(print())
                .andExpect(view().name("book/edit"))
                .andExpect(model().attribute("action", "/book?id=1"))
                .andExpect(model().attribute("book", book))
                .andExpect(model().attribute("genre", book.getGenre()))
                .andExpect(model().attribute("authors", new AuthorsDto(authors)))
                .andExpect(status().isOk());
    }

    @Test
    public void testEditPage_withoutId() throws Exception {
        Author author = new Author("1", "Test", "Sername", LocalDate.now(), "biography");
        List<Author> authors = Collections.singletonList(author);
        given(authorRepository.findAll())
                .willReturn(Collections.singletonList(author));
        Book book = new Book();
        given(bookRepository.findById("1")).willReturn(Optional.of(book));
        mockMvc.perform(get("/book/edit"))
                .andDo(print())
                .andExpect(view().name("book/edit"))
                .andExpect(model().attribute("action", "/book"))
                .andExpect(model().attribute("book", book))
                .andExpect(model().attribute("genre", book.getGenre()))
                .andExpect(model().attribute("authors", new AuthorsDto(authors)))
                .andExpect(status().isOk());
    }

    @Test
    public void testViewPage() throws Exception {
        Author author = new Author("1", "Test", "Sername", LocalDate.now(), "biography");
        List<Author> authors = Collections.singletonList(author);
        Book book = getBook();
        book.setAuthors(authors);
        given(bookRepository.findById("1")).willReturn(Optional.of(book));
        mockMvc.perform(get("/book/view?id=1"))
                .andDo(print())
                .andExpect(view().name("book/view"))
                .andExpect(model().attribute("action", "/book/comment?id=1"))
                .andExpect(model().attribute("book", book))
                .andExpect(model().attribute("genre", book.getGenre()))
                .andExpect(model().attribute("authors", book.getAuthors()))
                .andExpect(model().attribute("comment", new Comment()))
                .andExpect(status().isOk());
    }

    @Test
    public void testCommentPage() throws Exception {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        Comment comment = new Comment("This book is amazing", LocalDateTime.now());
        multiValueMap.add("commentBody", comment.getCommentBody());
        Book book = getBook();

        given(bookRepository.findById("1")).willReturn(Optional.of(book));
        Mockito.doNothing().when(bookRepository).setComment("1", comment);

        mockMvc.perform(post("/book/comment?id=1")
                .params(multiValueMap))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book/view?id=1"));
    }

    @Test
    public void testPostPage() throws Exception {
        Author author1 = new Author("1", "Test1", "Sername1", LocalDate.now(), "biography1");
        Author author2 = new Author("2", "Test2", "Sername2", LocalDate.now(), "biography2");
        List<Author> authors = Arrays.asList(author1, author2);
        Book book = getBook();
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

        multiValueMap.add("id", book.getId());
        multiValueMap.add("title", book.getTitle());
        multiValueMap.add("isbn", book.getIsbn());
        multiValueMap.add("genreName", book.getGenre().getGenreName());
        multiValueMap.add("genreDescription", book.getGenre().getGenreDescription());
        multiValueMap.add("description", book.getDescription());

        given(bookRepository.findById("1")).willReturn(Optional.of(book));

        mockMvc.perform(post("/book")
                .params(multiValueMap)
                .flashAttr("authorsDto", new AuthorsDto(authors)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book"));
    }

    private Book getBook() {
        Genre genre = new Genre("GenreMockName", "GenreMockDescription");
        Comment comment = new Comment("Like a shit", LocalDateTime.now());
        Comment comment2 = new Comment("Like a shit2", LocalDateTime.now());
        return new Book("1","Title", genre, "978-3-16-148410-0", "Description", Collections.emptyList(), Arrays.asList(comment, comment2));
    }
}