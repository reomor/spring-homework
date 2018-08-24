package task11.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import task11.domain.Author;
import task11.domain.Book;
import task11.dto.BookDto;
import task11.repository.AuthorRepository;
import task11.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class BookRestController {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    @Autowired
    public BookRestController(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    // list all books
    @GetMapping("/rest/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        log.info("Get all books by rest");
        List<Book> all = bookRepository.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    // get book by id
    @GetMapping("/rest/books/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable String id) {
        log.info("Get book by id({}) by rest", id);
        Book book = bookRepository.findById(id).orElseThrow(RuntimeException::new);
        List<Author> all = authorRepository.findAll();
        List<String> authorIds = book.getAuthors().stream().map(Author::getId).collect(Collectors.toList());
        return new ResponseEntity<>(new BookDto(book, all, authorIds), HttpStatus.OK);
    }
}
