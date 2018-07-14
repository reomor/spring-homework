package task05.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import task05.model.Question;
import task05.model.TestResult;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class TestingResultCheckServiceTest {

    private TestingResultCheckService testingResultCheckService = new TestingResultCheckService();

    @Test
    public void checkAnswers() {
        Question question = new Question("Perque?", Arrays.asList("0 - one", "1 - two", "2 - three"), 2);
        question.setAnswer(1);
        TestResult testResult = testingResultCheckService.checkAnswers(Collections.singletonList(
                question
        ));
        assertEquals(1, testResult.getBadAnswersAmount());
    }
}