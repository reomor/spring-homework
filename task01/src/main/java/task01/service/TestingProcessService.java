package task01.service;

import task01.model.Question;
import task01.model.TestResult;

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
        try {
            List<Question> questions = questionService.loadQuestion();
            askQuestions(questions);
            TestResult testResult = testingResultCheckService.checkAnswers(questions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void askQuestions(List<Question> questions) {
        try {
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                for (Question question : questions) {
                    System.out.println(question.getQuestionString());
                    getAnswer(reader, question);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
