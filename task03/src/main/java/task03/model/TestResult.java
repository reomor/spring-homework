package task03.model;

import java.util.Map;

public class TestResult {
    private final int questionAmount;
    private final int goodAnswersAmount;
    private final Map<String, AnswerStatus> reportPerQuestion;

    public TestResult(int questionAmount, int goodAnswersAmount, Map<String, AnswerStatus> reportPerQuestion) {
        this.questionAmount = questionAmount;
        this.goodAnswersAmount = goodAnswersAmount;
        this.reportPerQuestion = reportPerQuestion;
    }

    public int getQuestionAmount() {
        return questionAmount;
    }

    public int getGoodAnswersAmount() {
        return goodAnswersAmount;
    }

    public int getBadAnswersAmount() {
        return questionAmount - goodAnswersAmount;
    }

    public Map<String, AnswerStatus> getReportPerQuestion() {
        return reportPerQuestion;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("===== ").append(goodAnswersAmount).append("/").append(questionAmount).append(" =====\n");
        for (Map.Entry<String, AnswerStatus> entry : reportPerQuestion.entrySet()) {
            stringBuilder.append(entry.getValue().toString()).append(" ").append(entry.getKey()).append("\n");
        }
        return stringBuilder.toString();
    }
}
