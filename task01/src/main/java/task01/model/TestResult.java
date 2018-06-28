package task01.model;

import java.util.Map;

public class TestResult {
    private int questionAmount;
    private int goodAnswersAmount;
    private Map<String, AnswerStatus> reportPerQuestion;

    public TestResult() {}

    public TestResult(int questionAmount, int goodAnswersAmount, Map<String, AnswerStatus> reportPerQuestion) {
        this.questionAmount = questionAmount;
        this.goodAnswersAmount = goodAnswersAmount;
        this.reportPerQuestion = reportPerQuestion;
    }

    public int getQuestionAmount() {
        return questionAmount;
    }

    public void setQuestionAmount(int questionAmount) {
        this.questionAmount = questionAmount;
    }

    public int getGoodAnswersAmount() {
        return goodAnswersAmount;
    }

    public int getBadAnswersAmount() {
        return questionAmount - goodAnswersAmount;
    }

    public void setGoodAnswersAmount(int goodAnswersAmount) {
        this.goodAnswersAmount = goodAnswersAmount;
    }

    public Map<String, AnswerStatus> getReportPerQuestion() {
        return reportPerQuestion;
    }

    public void setReportPerQuestion(Map<String, AnswerStatus> reportPerQuestion) {
        this.reportPerQuestion = reportPerQuestion;
    }

    public void printReport() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Количество правильных ответов: ").append(goodAnswersAmount).append(" из ").append(questionAmount).append("\n");
        for (Map.Entry<String, AnswerStatus> entry : reportPerQuestion.entrySet()) {
            stringBuilder.append(entry.getValue().toString()).append(" ").append(entry.getKey()).append("\n");
        }
        System.out.print(stringBuilder.toString());
    }
}
