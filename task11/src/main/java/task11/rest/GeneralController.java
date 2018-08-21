package task11.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import task11.domain.Author;
import task11.repository.AuthorRepository;

import java.util.List;

@Slf4j
@Controller
public class GeneralController {

    private final AuthorRepository authorRepository;

    @Autowired
    public GeneralController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/")
    public String listPage() {
        log.info("Main page");
        return "index";
    }

    @GetMapping("/authors")
    public String listPage(Model model) {
        log.info("Request all authors");
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        return "author/list_bk";
    }
}
