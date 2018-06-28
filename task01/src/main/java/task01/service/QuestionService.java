package task01.service;

import task01.model.Question;
import task01.model.Test;
import task01.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestionService {

    private final String PROPERTY_QUESTION_FILE = "question.file";

    public List<Question> loadQuestion() throws IOException {
        String questionFileName = Utils.getProperties().getProperty(PROPERTY_QUESTION_FILE);
        if (questionFileName == null) {
            System.out.println("Нет требуемого параметра \"" + PROPERTY_QUESTION_FILE + "\" в файле настроек.");
            return new ArrayList<>();
        }
        return Utils.readCSVInQuestions(questionFileName);
    }
}
