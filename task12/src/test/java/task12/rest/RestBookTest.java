package task12.rest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import task12.domain.Author;
import task12.domain.Book;
import task12.domain.Comment;
import task12.domain.Genre;
import task12.repository.BookReactiveRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.mockito.BDDMockito.given;

public class RestBookTest extends AbstractRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    protected BookReactiveRepository repository;

    @Test
    public void givenBooks_whenFindAllBooks_thenStatusOk() {
        Book book1 = getBook("1");
        Book book2 = getBook("2");

        given(repository.findAll()).willReturn(Flux.just(book1, book2));

        EntityExchangeResult<List<Book>> result = webTestClient.get()
                .uri("/rest/books")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Book.class)
                .returnResult();

        Objects.requireNonNull(result.getResponseBody()).forEach(System.out::println);
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
}
