package task10.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import task10.domain.Book;
import task10.repository.BookRepository;

import java.util.List;

@Slf4j
@Controller
public class BookController {

    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/book")
    public String listPage(Model model) {
        log.info("Request all authors");
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "book/list";
    }
    // https://www.thymeleaf.org/doc/articles/layouts.html
    // https://github.com/thymeleaf/thymeleafexamples-layouts/tree/master/src/main/java/thymeleafexamples/layouts
}
