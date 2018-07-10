package task04.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionUtilBeanTest {

    @Autowired
    private QuestionUtilBean questionUtilBean;

    @Test
    public void getQuestions() {
        List<QuestionUtilBean.Question> questions = questionUtilBean.getQuestions();
        assertEquals(5, questions.size());
    }
}