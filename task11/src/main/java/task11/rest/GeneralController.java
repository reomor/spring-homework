package task11.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import task11.domain.Book;
import task11.repository.AuthorRepository;
import task11.repository.BookRepository;

import java.util.List;

@Slf4j
@Controller
public class GeneralController {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Autowired
    public GeneralController(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/")
    public String listPage() {
        log.info("Main page");
        return "index";
    }

    @GetMapping("/authors")
    public String listAuthorsPage(Model model) {
        log.info("Request author list");
        return "author/list";
    }

    @GetMapping("/books")
    public String listBooksPage(Model model) {
        log.info("Request book list");
        return "book/list_bk";
    }
}
