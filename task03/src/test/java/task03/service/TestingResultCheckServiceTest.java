package task03.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import task03.model.Question;
import task03.model.TestResult;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class TestingResultCheckServiceTest {

    private TestingResultCheckService testingResultCheckService = new TestingResultCheckService();

    @Test
    public void checkAnswers() {
        TestResult testResult = testingResultCheckService.checkAnswers(Collections.singletonList(
                new Question("Perque?", Arrays.asList("0 - one", "1 - two", "2 - three"), 2)
        ));
        assertEquals(1, testResult.getBadAnswersAmount());
    }
}