package task01.service;

import task01.model.Question;
import task01.model.Test;
import task01.model.TestResult;
import task01.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class TestingProcessService {

    private QuestionService questionService;
    private TestingResultCheckService testingResultCheckService;

    public TestingProcessService(QuestionService questionService, TestingResultCheckService testingResultCheckService) {
        this.questionService = questionService;
        this.testingResultCheckService = testingResultCheckService;
    }

    public void processTest() {
        Test test = prepareTest();
        if (test == null) {
            System.out.println("Ошибка при составлении теста.");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            final User user = askUserInformation(reader, test);
            if (user == null) {
                System.out.println("Ошибка в процессе ввода имени и фамилии.");
                return;
            }
            askQuestions(reader, test.getQuestions());
        } catch (IOException e) {
            e.printStackTrace();
        }
        test.setTestResult(testingResultCheckService.checkAnswers(test.getQuestions()));
        test.getTestResult().printReport();
    }

    private Test prepareTest() {
        Test test = new Test();
        try {
            List<Question> questions = questionService.loadQuestion();
            if (questions.size() == 0) {
                System.out.println("Список вопросов пуст.");
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
        System.out.println("Введите имя и фамилию");
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
                    System.out.println(String.format("Number must be between %d and %d", 0, question.getAnswerVariants().size() - 1));
                }
            } catch (IOException e) {
                System.out.println("Enter correct number.");
            }
        }
        question.setAnswer(answer);
    }

    private boolean isValidAnswer(Question question, int answer) {
        return answer >= 0 && answer < question.getAnswerVariants().size();
    }
}
