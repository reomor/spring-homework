package task09.rest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(GeneralController.class)
public class GeneralControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(view().name("index"))
                .andExpect(status().isOk());
    }
}