package task11.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import task11.domain.Book;
import task11.repository.BookRepository;

import java.util.List;

@Slf4j
@RestController
public class BookRestController {

    @Autowired
    private ObjectMapper objectMapper;

    private final BookRepository bookRepository;

    @Autowired
    public BookRestController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
    public ResponseEntity<Book> getBook(@PathVariable String id) throws JsonProcessingException {
        log.info("Get book by id({}) by rest", id);
        Book book = bookRepository.findById(id).orElseThrow(RuntimeException::new);
        String json = objectMapper.writeValueAsString(book);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
}
