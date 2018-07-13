package task05.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import task05.model.Question;
import task05.model.Test;
import task05.model.TestResult;
import task05.model.User;

import java.io.IOException;
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

    public Test processTest() {
        this.locale = consoleInteractionService.askLocale();
        Test test = prepareTest();
        if (test == null) {
            System.out.println(messageSource.getMessage("error.test.prepare", null, this.locale));
            return null;
        }
        System.out.println(test.getTestResult());
        consoleInteractionService.closeReader();
        return test;
    }

    private Test prepareTest() {
        final List<Question> questions;
        final User user;
        try {
            questions = questionService.loadQuestion(this.locale);
            if (questions.size() == 0) {
                System.out.println(messageSource.getMessage("error.list.empty",null, this.locale));
                return null;
            }
            user = consoleInteractionService.askUserInformation();
            if (user == null) {
                System.out.println(messageSource.getMessage("error.enter.user", null, this.locale));
                return null;
            }
        } catch (IOException e) {
            return null;
        }
        consoleInteractionService.askQuestions(questions);
        TestResult testResult = testingResultCheckService.checkAnswers(questions);
        return new Test(user, questions, testResult);
    }
}
