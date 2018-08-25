package task11.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task11.domain.Author;
import task11.domain.Book;
import task11.dto.BookDto;
import task11.exception.ObjectNotFound;
import task11.repository.AuthorRepository;
import task11.repository.BookRepository;

import java.util.List;
import java.util.Optional;
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
    @Autowired
    private ObjectMapper objectMapper;
    // get book by id
    @GetMapping("/rest/books/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable String id) throws JsonProcessingException {
        log.info("Get book by id({}) by rest", id);
        Book book = bookRepository.findById(id).orElseThrow(RuntimeException::new);
        List<Author> all = authorRepository.findAll();
        List<String> authorIds = book.getAuthors().stream().map(Author::getId).collect(Collectors.toList());

        BookDto bookDto = new BookDto(book, all, authorIds);
        String json = objectMapper.writeValueAsString(bookDto);
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    @PostMapping("/rest/books")
    public ResponseEntity<Book> createNewBook(@RequestBody BookDto bookDto) {

        return null;
    }

    @PutMapping("/rest/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable String id, @RequestBody BookDto bookDto) {

        return null;
    }

    // delete book
    @DeleteMapping("/rest/books/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable String id) {
        log.info("Delete author by id({}) by rest", id);
        Book book = checkIfExists(id);
        authorRepository.deleteById(book.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Book checkIfExists(String id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            return bookOptional.get();
        }
        throw new ObjectNotFound();
    }
}
