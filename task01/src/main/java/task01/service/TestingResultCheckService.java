package task01.service;

import task01.model.Question;
import task01.model.AnswerStatus;
import task01.model.TestResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestingResultCheckService {
    public TestResult checkAnswers(List<Question> questions) {
        TestResult result = new TestResult();
        Map<String, AnswerStatus> report = new HashMap<>();
        result.setQuestionAmount(questions.size());
        int rightAnswers = 0;

        for (Question question : questions) {
            if (question.isRight()) {
                rightAnswers += 1;
                report.put(question.getQuestionString(), AnswerStatus.Good);
            } else {
                report.put(question.getQuestionString(), AnswerStatus.Bad);
            }
        }
        result.setGoodAnswersAmount(rightAnswers);
        result.setReportPerQuestion(report);
        return result;
    }
}
