package task10.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        log.info("Request all books");
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "book/list";
    }

    @GetMapping("/book/view")
    public String editPage(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "edit", required = false) Boolean edit,
            Model model) {
        log.info("Edit book with id " + id);
        Book book = bookRepository.findById(id).orElseThrow(RuntimeException::new);
        String action = "/book";
        if (id == null) {

        } else {
            action += "?id=" + id;
        }
        model.addAttribute("action", action);
        if (edit != null) {
            model.addAttribute("edit", edit);
        }
        model.addAttribute("book", book);
        return "book/view";
    }

    @PostMapping("/book")
    public String postPage(@ModelAttribute Book book, Model model) {
        log.info("Got book to update " + book);
        //bookRepository.save(book);
        return "redirect:/book";
    }
}
