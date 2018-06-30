package task02.service;

import task02.model.Question;
import task02.utils.Utils;

import java.io.IOException;
import java.util.List;

public class QuestionService {

    private final String questionFileName;

    public QuestionService(String questionFileName) {
        this.questionFileName = questionFileName;
    }

    public List<Question> loadQuestion() throws IOException {
        return Utils.readCSVInQuestions(questionFileName);
    }
}
