package task10.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import task10.domain.Book;
import task10.domain.Comment;
import task10.domain.Genre;
import task10.dto.AuthorsDto;
import task10.repository.AuthorRepository;
import task10.repository.BookRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
public class BookController {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    @Autowired
    public BookController(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @GetMapping("/book")
    public String listPage(Model model) {
        log.info("Request all books");
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "book/list";
    }

    @GetMapping("/book/delete")
    public String deletePage(@RequestParam("id") String id) {
        log.info("Delete book with id " + id);
        bookRepository.deleteById(id);
        return "redirect:/book";
    }

    @GetMapping("/book/edit")
    public String editPage(
            @RequestParam(value = "id", required = false) String id,
            Model model) {
        log.info("Edit book with id " + id);
        Book book = null;
        String action = "/book";
        if (id == null) {
            book = new Book();
        } else {
            action += "?id=" + id;
            book = bookRepository.findById(id).orElseThrow(RuntimeException::new);
        }
        model.addAttribute("action", action);
        model.addAttribute("book", book);
        model.addAttribute("genre", book.getGenre());
        model.addAttribute("authors", new AuthorsDto(authorRepository.findAll()));
        return "book/edit";
    }

    @GetMapping("/book/view")
    public String viewPage(
            @RequestParam(value = "id") String id,
            Model model) {
        log.info("View book with id " + id);
        Book book = bookRepository.findById(id).orElseThrow(RuntimeException::new);
        model.addAttribute("book", book);
        model.addAttribute("genre", book.getGenre());
        model.addAttribute("authors", book.getAuthors());
        model.addAttribute("comment", new Comment());
        model.addAttribute("action", "/book/comment?id=" + id);
        return "book/view";
    }

    @PostMapping("/book/comment")
    public String commentPage(
            @RequestParam(value = "id") String id,
            @ModelAttribute Comment comment) {
        log.info("Comment book with id " + id);
        // check that book exists
        Book book = bookRepository.findById(id).orElseThrow(RuntimeException::new);
        comment.setDate(LocalDateTime.now());
        bookRepository.setComment(id, comment);
        return "redirect:/book/view?id=" + id;
    }

    @PostMapping("/book")
    public String postPage(
            @ModelAttribute Genre genre,
            @ModelAttribute Book book,
            @ModelAttribute AuthorsDto authorsDto,
            Model model) {
        log.info("Update book({}) with genre({}) and authors({})", book, genre, authorsDto);
        book.setGenre(genre);
        book.setAuthors(authorsDto.getAuthorList());
        bookRepository.save(book);
        return "redirect:/book";
    }
}
