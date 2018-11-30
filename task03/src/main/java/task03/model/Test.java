package task03.model;

import java.util.List;

public class Test {
    private User user;
    private List<Question> questions;
    private TestResult testResult;

    public Test(User user, List<Question> questions, TestResult testResult) {
        this.user = user;
        this.questions = questions;
        this.testResult = testResult;
    }

    public User getUser() {
        return user;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public TestResult getTestResult() {
        return testResult;
    }
}
