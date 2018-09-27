package task14.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import task14.business.dto.RegistrationFormDto;
import task14.exception.UnauthorizedAuthority;
import task14.service.UserService;

import javax.validation.Valid;

@Slf4j
@Controller
public class IndexPageController {

    private final UserService userService;

    @Autowired
    public IndexPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String listPage() {
        log.info("Main page");
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        log.info("Login page");
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        log.info("Register page");
        model.addAttribute("registrationForm", new RegistrationFormDto());
        return "registration/form";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("registrationForm") RegistrationFormDto registrationFormDto,
            Errors errors,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        log.info("Attempt to register with " + registrationFormDto);
        if (errors.hasErrors()) {
            log.info("Errors during registration");
            model.addAttribute("registrationForm", registrationFormDto);
            return "registration/form";
        }
        log.info("Successfully registered");
        // is validated
        try {
            userService.register(registrationFormDto.getName(), registrationFormDto.getEmail(), registrationFormDto.getPassword());
        } catch (UnauthorizedAuthority unauthorizedAuthority) {
            redirectAttributes.addFlashAttribute("alertError", "Error during registration.");
            return "redirect:/login";
        }
        redirectAttributes.addFlashAttribute("alertSuccess", "Successfully registered. Please, login :)");
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
