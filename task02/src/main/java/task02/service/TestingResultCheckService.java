package task02.service;

import org.springframework.stereotype.Service;
import task02.model.Question;
import task02.model.AnswerStatus;
import task02.model.Test;
import task02.model.TestResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TestingResultCheckService {

    public TestResult checkAnswers(List<Question> questions) {

        Map<String, AnswerStatus> reportPerQuestion = new HashMap<>();
        int rightAnswers = 0;

        for (Question question : questions) {
            if (question.isRight()) {
                rightAnswers++;
                reportPerQuestion.put(question.getQuestionString(), AnswerStatus.GOOD);
            } else {
                reportPerQuestion.put(question.getQuestionString(), AnswerStatus.BAD);
            }
        }

        return new TestResult(questions.size(), rightAnswers, reportPerQuestion);
    }
}
