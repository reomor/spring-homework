package task13.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class IndexPageController {

    @GetMapping("/")
    public String listPage() {
        log.info("Main page");
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/authors")
    public String listAuthorsPage() {
        log.info("Request author list");
        return "author/list";
    }

    @GetMapping("/books")
    public String listBooksPage() {
        log.info("Request book list");
        return "book/list";
    }
}
