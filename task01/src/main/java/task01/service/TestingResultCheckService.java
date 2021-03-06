package task01.service;

import task01.model.Question;
import task01.model.AnswerStatus;
import task01.model.Test;
import task01.model.TestResult;

import java.util.HashMap;
import java.util.Map;

public class TestingResultCheckService {
    public TestResult checkAnswers(Test test) {

        TestResult testResult = new TestResult();
        Map<String, AnswerStatus> report = new HashMap<>();
        testResult.setQuestionAmount(test.getQuestions().size());
        int rightAnswers = 0;

        for (Question question : test.getQuestions()) {
            if (question.isRight()) {
                rightAnswers += 1;
                report.put(question.getQuestionString(), AnswerStatus.Good);
            } else {
                report.put(question.getQuestionString(), AnswerStatus.Bad);
            }
        }
        testResult.setGoodAnswersAmount(rightAnswers);
        testResult.setReportPerQuestion(report);
        test.setTestResult(testResult);
        return testResult;
    }
}
