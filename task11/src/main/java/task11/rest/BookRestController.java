package task11.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value = "book controller", description = "Operation to manage books")
public class BookRestController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookRestController(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @GetMapping("/rest/books")
    @ApiOperation(value = "Get list of all books", response = Book.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved books list")
    })
    public ResponseEntity<List<Book>> getAllBooks() {
        log.info("Get all books by rest");
        List<Book> all = bookRepository.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @ApiOperation(value = "Get book by id", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved book")
    })
    @GetMapping("/rest/books/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable String id) {
        log.info("Get book by id({}) by rest", id);
        Book book = bookRepository.findById(id).orElseThrow(RuntimeException::new);
        List<Author> all = authorRepository.findAll();
        List<String> authorIds = book.getAuthors().stream().map(Author::getId).collect(Collectors.toList());
        return new ResponseEntity<>(new BookDto(book, all, authorIds), HttpStatus.OK);
    }

    @ApiOperation(value = "Create new book", code = 201, response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created book")
    })
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

    @ApiOperation(value = "Update existing book", code = 202, response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Update accepted")
    })
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

    @ApiOperation(value = "Delete book by id", code = 204)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Delete completed")
    })
    @DeleteMapping("/rest/books/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable String id) {
        log.info("Delete author by id({}) by rest", id);
        Book book = checkIfExists(id);
        authorRepository.deleteById(book.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Comment a book", code = 201)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Comment added")
    })
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
