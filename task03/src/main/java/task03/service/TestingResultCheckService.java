package task03.service;

import org.springframework.stereotype.Service;
import task03.model.AnswerStatus;
import task03.model.Question;
import task03.model.TestResult;

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
