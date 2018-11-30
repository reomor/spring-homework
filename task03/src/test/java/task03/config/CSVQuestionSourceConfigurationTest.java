package task03.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import task03.model.Question;
import task03.service.CSVQuestionService;
import task03.service.QuestionService;
import task03.setting.ApplicationSettings;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = {Main.class})
@SpringBootTest
@TestPropertySource(
        properties = {
                "load-type=csv"
        }
)
public class CSVQuestionSourceConfigurationTest {

    @Autowired
    QuestionService questionService;

    @Autowired
    ApplicationSettings applicationSettings;

    @Test
    public void checkBean() {
        Assert.assertTrue(questionService instanceof CSVQuestionService);
    }

    @Test
    public void test() throws IOException {
        List<Question> questions = questionService.loadQuestion(Locale.ENGLISH);
        assertEquals(5, questions.size());
    }
}