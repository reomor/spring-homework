package task02.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import task02.model.Question;
import task02.model.Test;
import task02.model.TestResult;
import task02.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

@Service
public class TestingProcessService {

    private MessageSource messageSource;
    private QuestionService questionService;
    private ConsoleInteractionService consoleInteractionService;
    private TestingResultCheckService testingResultCheckService;

    private Locale locale = Locale.ENGLISH;

    @Autowired
    public TestingProcessService(
            QuestionService questionService,
            ConsoleInteractionService consoleInteractionService,
            TestingResultCheckService testingResultCheckService,
            MessageSource messageSource
    ) {
        this.questionService = questionService;
        this.consoleInteractionService = consoleInteractionService;
        this.testingResultCheckService = testingResultCheckService;
        this.messageSource = messageSource;
    }

    public void processTest() {
        this.locale = consoleInteractionService.askLocale();
        Test test = prepareTest();
        if (test == null) {
            System.out.println(messageSource.getMessage("error.test.prepare", null, this.locale));
            return;
        }
        consoleInteractionService.performAskStages(test);
        TestResult testResult = testingResultCheckService.checkAnswers(test);
        testResult.printReport();
    }

    private Test prepareTest() {
        Test test = new Test();
        try {
            List<Question> questions = questionService.loadQuestion(this.locale);
            if (questions.size() == 0) {
                System.out.println(messageSource.getMessage("error.list.empty",null, this.locale));
                return null;
            }
            test.setQuestions(questions);
        } catch (IOException e) {
            return null;
        }
        return test;
    }
}
