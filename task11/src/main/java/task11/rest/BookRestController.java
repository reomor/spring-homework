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
import task11.domain.Comment;
import task11.dto.BookDto;
import task11.exception.ObjectNotFound;
import task11.repository.AuthorRepository;
import task11.repository.BookRepository;

import java.time.LocalDateTime;
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

    // get book by id
    @GetMapping("/rest/books/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable String id) {
        log.info("Get book by id({}) by rest", id);
        Book book = bookRepository.findById(id).orElseThrow(RuntimeException::new);
        List<Author> all = authorRepository.findAll();
        List<String> authorIds = book.getAuthors().stream().map(Author::getId).collect(Collectors.toList());
        return new ResponseEntity<>(new BookDto(book, all, authorIds), HttpStatus.OK);
    }

    //post new book
    @PostMapping("/rest/books")
    public ResponseEntity<Book> createNewBook(@RequestBody BookDto bookDto) {
        List<Author> authorList = bookDto.getAuthorIds()
                .stream()
                .map(id -> authorRepository.findById(id).orElseThrow(RuntimeException::new))
                .collect(Collectors.toList());
        Book book = bookDto.getBook();
        book.setId(null);
        book.setAuthors(authorList);
        return new ResponseEntity<>(bookRepository.save(book), HttpStatus.CREATED);
    }

    // update existing book
    @PutMapping("/rest/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable String id, @RequestBody BookDto bookDto) {
        List<Author> authorList = bookDto.getAuthorIds()
                .stream()
                .map(authorId -> authorRepository.findById(authorId).orElseThrow(RuntimeException::new))
                .collect(Collectors.toList());
        Book book = bookDto.getBook();
        book.setId(id);
        book.setAuthors(authorList);
        return new ResponseEntity<>(bookRepository.save(book), HttpStatus.ACCEPTED);
    }

    // delete book
    @DeleteMapping("/rest/books/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable String id) {
        log.info("Delete author by id({}) by rest", id);
        Book book = checkIfExists(id);
        authorRepository.deleteById(book.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // add comment to book
    @PostMapping("/rest/books/comment/{id}")
    public ResponseEntity<Comment> commentBook(@PathVariable String id, @RequestBody Comment comment) {
        comment.setDate(LocalDateTime.now());
        bookRepository.setComment(id, comment);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    private Book checkIfExists(String id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            return bookOptional.get();
        }
        throw new ObjectNotFound();
    }
}