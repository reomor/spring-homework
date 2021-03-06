package task12.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import task12.dto.RegistrationFormDto;

import javax.validation.Valid;

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

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationFormDto());
        return "registration/form";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("registrationForm") RegistrationFormDto registrationFormDto,
            //BindingResult bindingResult,
            Errors errors,
            Model model,
            RedirectAttributes redirectAttributes
            ) {
        // if (bindingResult.hasErrors()) {
        if (errors.hasErrors()) {
            model.addAttribute("registrationForm", registrationFormDto);
            return "registration/form";
        }
        // TODO register user
        return "redirect:/login";
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
