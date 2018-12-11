package task11.rest;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import task11.domain.Author;
import task11.domain.Book;
import task11.domain.Comment;
import task11.domain.Genre;
import task11.repository.BookReactiveRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestBookTest {

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

    @Test
    public void givenBook_whenFindByIdBook_thenStatusOk() {
        Book book = getBook("1");

        given(repository.findById("1")).willReturn(Mono.just(book));

        EntityExchangeResult<Book> result = webTestClient.get()
                .uri("/rest/books/" + book.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(Book.class)
                .returnResult();

        System.out.println(result.getResponseBody());
    }

    @Test
    public void givenBook_whenSaveBook_thenStatusOk() {
        Book bookNew = getBook(null);
        Book bookSaved = getBook("1");

        given(repository.save(bookNew)).willReturn(Mono.just(bookSaved));

        EntityExchangeResult<Book> result = webTestClient.post()
                .uri("/rest/books")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(bookNew), Book.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(Book.class)
                .consumeWith(response -> {
                    Assertions.assertThat(response.getResponseBody()).isEqualTo(bookSaved);
                })
                .returnResult();

        System.out.println(result.getResponseBody());
    }

    @Test
    public void givenUpdatedBook_whenUpdateBook_thenStatusOk() {
        final String id = "1";
        Book book = getBook(id);
        Book bookUpdated = getUpdatedBook(id);

        given(repository.findById(id)).willReturn(Mono.just(book));
        given(repository.save(bookUpdated)).willReturn(Mono.just(bookUpdated));

        EntityExchangeResult<Book> result = webTestClient.put()
                .uri("/rest/books/" + id)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(bookUpdated), Book.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(Book.class)
                .consumeWith(response -> {
                    Assertions.assertThat(response.getResponseBody()).isEqualTo(bookUpdated);
                })
                .returnResult();

        System.out.println(result.getResponseBody());
    }

    @Test
    public void givenId_whenDeleteBook_thenStatusOk() {
        Book bookUpdated = getBook("1");

        given(repository.findById(bookUpdated.getId())).willReturn(Mono.just(bookUpdated));
        given(repository.delete(bookUpdated)).willReturn(Mono.empty());

        webTestClient.delete()
                .uri("/rest/books/" + bookUpdated.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void givenComment_whenAddComment_thenStatusOk() {
        Comment comment = new Comment("New comment to add", LocalDateTime.now());
        final String bookId = "1";
        Book book = getBook(bookId);

        given(repository.findById(bookId)).willReturn(Mono.just(book));
        given(repository.setComment(bookId, comment)).willReturn(Mono.empty());

        webTestClient.post()
                .uri("/rest/books/" + bookId + "/comments")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(comment), Comment.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void givenBookId_whenFindAllComments_thenStatusOk() {
        final String bookId = "1";
        Book book = getBook(bookId);

        given(repository.getComments(bookId)).willReturn(Flux.fromIterable(book.getComments()));

        EntityExchangeResult<List<Comment>> result = webTestClient.get()
                .uri("/rest/books/" + bookId + "/comments")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Comment.class)
                .returnResult();
        System.out.println(result.getResponseBody());
    }

    private Author getAuthor() {
        return new Author("1", "Test1", "Sername1", LocalDate.now(), "biography1");
    }

    private Book getBook(String id) {
        Author author = getAuthor();
        Genre genre = new Genre("GenreMockName", "GenreMockDescription");
        Comment comment = new Comment("Random comment about", LocalDateTime.now());
        Comment comment2 = new Comment("Random comment about2", LocalDateTime.now());
        return new Book(id, "Title", genre, "978-3-16-148410-0", "Description", Collections.singletonList(author), Arrays.asList(comment, comment2));
    }

    private Book getUpdatedBook(String id) {
        Author author = getAuthor();
        Genre genre = new Genre("GenreUpdatedName", "GenreMockDescription");
        Comment comment = new Comment("Random comment about", LocalDateTime.now());
        Comment comment2 = new Comment("Random comment about2", LocalDateTime.now());
        return new Book(id, "TitleUpdated", genre, "978-3-16-148550-0", "Description", Collections.singletonList(author), Arrays.asList(comment, comment2));
    }
}
