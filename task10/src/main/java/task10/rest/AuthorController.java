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
        return "author/list";
    }
    // https://www.thymeleaf.org/doc/articles/layouts.html
    // https://github.com/thymeleaf/thymeleafexamples-layouts/tree/master/src/main/java/thymeleafexamples/layouts
    @GetMapping("/author/edit")
    public String editPage(@RequestParam("id") String id, Model model) {
        log.info("Edit author with id " + id);
        Author author = authorRepository.findById(id).orElseThrow(RuntimeException::new);
        model.addAttribute("author", author);
        return "author/edit";
    }

    @GetMapping("/author/delete")
    public String deletePage(@RequestParam("id") String id) {
        log.info("Delete author with id " + id);
        authorRepository.deleteById(id);
        return "redirect:/author";
    }

    @GetMapping("/author/view")
    public String viewPage(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "edit", required = false) Boolean edit,
            Model model) {
        log.info("View author with id " + id);
        Author author = null;
        String action = "/author";
        if (id == null) {
            author = new Author();
        } else {
            author = authorRepository.findById(id).orElseThrow(RuntimeException::new);
            action += "(?id=" + id + ")";
        }
        model.addAttribute("action", action);
        model.addAttribute("author", author);
        if (edit != null) {
            model.addAttribute("edit", true);
        }
        return "author/view";
    }

    @PostMapping("/author")
    public String postPage(@ModelAttribute Author author, Model model) {
        log.info("Got author to update " + author);
        authorRepository.save(author);
        return "redirect:/author";
    }
}
