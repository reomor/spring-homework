package task03.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import task03.model.Question;
import task03.service.YMLQuestionService;
import task03.service.QuestionService;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(
        properties = {
                "load-type=yml"
        }
)
public class YMLQuestionSourceConfigurationTest {

    @Autowired
    QuestionService questionService;

    @Test
    public void checkBean() {
        Assert.assertTrue(questionService instanceof YMLQuestionService);
    }

    @Test
    public void test() throws IOException {
        List<Question> questions = questionService.loadQuestion(Locale.ENGLISH);
        assertEquals(5, questions.size());
    }

}