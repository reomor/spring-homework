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
    private TestingResultCheckService testingResultCheckService;

    private Locale locale = new Locale("ru", "RU");

    @Autowired
    public TestingProcessService(
            QuestionService questionService,
            TestingResultCheckService testingResultCheckService,
            MessageSource messageSource
    ) {
        this.questionService = questionService;
        this.testingResultCheckService = testingResultCheckService;
        this.messageSource = messageSource;
    }

    public void processTest() {
        Test test = prepareTest();
        if (test == null) {
            System.out.println(messageSource.getMessage("error.test.prepare", null, this.locale));
            return;
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            final User user = askUserInformation(reader, test);
            if (user == null) {
                System.out.println(messageSource.getMessage("error.enter",null, this.locale));
                return;
            }
            askQuestions(reader, test.getQuestions());
        } catch (IOException e) {
            e.printStackTrace();
        }
        TestResult testResult = testingResultCheckService.checkAnswers(test);
        testResult.printReport();
    }

    private Test prepareTest() {
        Test test = new Test();
        try {
            List<Question> questions = questionService.loadQuestion();
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

    private User askUserInformation(BufferedReader reader, Test test) throws IOException {
        User user = null;
        System.out.println(messageSource.getMessage("error.user.enter",null, this.locale));
        String[] userData = reader.readLine().split(" ");
        if (userData.length != 2) {
            return null;
        }
        user = new User(userData[0], userData[1]);
        test.setUser(user);
        return user;
    }

    private void askQuestions(BufferedReader reader, List<Question> questions) {
        for (Question question : questions) {
            System.out.println(question.getQuestionString());
            for (String variant : question.getAnswerVariants()) {
                System.out.println(variant);
            }
            getAnswer(reader, question);
        }
    }

    private void getAnswer(BufferedReader reader, Question question) {
        boolean valid = false;
        Integer answer = null;
        while(!valid) {
            try {
                answer = Integer.parseInt(reader.readLine());
                if (isValidAnswer(question, answer)) {
                    valid = true;
                } else {
                    System.out.println(messageSource.getMessage(
                            "error.answer.range",
                            new String[] {"0", Integer.toString(question.getAnswerVariants().size() - 1)},
                            this.locale));
                }
            } catch (IOException e) {
                System.out.println(messageSource.getMessage("error.answer.correctness",null, this.locale));
            }
        }
        question.setAnswer(answer);
    }

    private boolean isValidAnswer(Question question, int answer) {
        return answer >= 0 && answer < question.getAnswerVariants().size();
    }
}