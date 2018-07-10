package task04.service;

import org.springframework.stereotype.Service;
import task04.model.Question;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Service(value = "ymlQS")
public class YMLQuestionService implements QuestionService {

    @Override
    public List<Question> loadQuestion(Locale locale) throws IOException {
        return null;
    }
}
