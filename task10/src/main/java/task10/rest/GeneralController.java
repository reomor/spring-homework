package task10.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class GeneralController {
    @GetMapping("/")
    public String listPage() {
        log.info("Main page");
        return "index";
    }
}
