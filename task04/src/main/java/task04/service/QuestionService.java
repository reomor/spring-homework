package task04.service;

import task04.model.Question;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public interface QuestionService {
    List<Question> loadQuestion(Locale locale) throws IOException;
}
