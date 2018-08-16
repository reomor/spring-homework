package task10.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import task10.domain.Author;
import task10.repository.AuthorRepository;

import java.util.List;

@Slf4j
@Controller
public class AuthorController {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/author")
    public String listPage(Model model) {
        log.info("Request all authors");
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        return "list";
    }
    // https://www.thymeleaf.org/doc/articles/layouts.html
    // https://github.com/thymeleaf/thymeleafexamples-layouts/tree/master/src/main/java/thymeleafexamples/layouts
    @GetMapping("/author/edit")
    public String editPage(@RequestParam("id") String id, Model model) {
        log.info("Edit author with id " + id);
        Author author = authorRepository.findById(id).orElseThrow(RuntimeException::new);
        model.addAttribute("author", author);
        return "edit";
    }

    @PostMapping("/author")
    public String postPage(@ModelAttribute Author author, Model model) {
        log.info("Got author to update " + author);
        return "redirect:author";
    }
    //*/
}
