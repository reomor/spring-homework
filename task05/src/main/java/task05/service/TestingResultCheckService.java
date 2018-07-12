package task05.service;

import org.springframework.stereotype.Service;
import task05.model.AnswerStatus;
import task05.model.Question;
import task05.model.TestResult;

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
