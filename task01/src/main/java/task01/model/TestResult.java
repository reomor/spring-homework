package task01.model;

import java.util.Map;

public class TestResult {
    private int questionAmount;
    private int goodAnsweres;
    private Map<String, String> resultPerQuestion;

    public TestResult(int questionAmount, int goodAnsweres, Map<String, String> resultPerQuestion) {
        this.questionAmount = questionAmount;
        this.goodAnsweres = goodAnsweres;
        this.resultPerQuestion = resultPerQuestion;
    }
}
