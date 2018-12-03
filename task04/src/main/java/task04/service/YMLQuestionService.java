package task04.service;

import org.springframework.context.MessageSource;
import task04.model.Question;
import task04.setting.ApplicationSettings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class YMLQuestionService implements QuestionService {

    private final ApplicationSettings applicationSettings;
    private final MessageSource messageSource;

    public YMLQuestionService(ApplicationSettings applicationSettings, MessageSource messageSource) {
        this.applicationSettings = applicationSettings;
        this.messageSource = messageSource;
    }

    @Override
    public List<Question> loadQuestion(Locale locale) throws IOException {
        return readYMLInQuestions(applicationSettings.getQuestions(), locale);
    }

    private List<Question> readYMLInQuestions(List<ApplicationSettings.Question> list, Locale locale) throws IOException {
        List<Question> questions = new ArrayList<>();
        for (ApplicationSettings.Question questionFromYML : list) {
            String[] questionString = messageSource.getMessage(questionFromYML.getQuestionString(), null, locale).split(",");
            Integer rightAnswer = questionFromYML.getRightAnswer();
            Question question = new Question(
                    questionString[0],
                    Arrays.asList(Arrays.copyOfRange(questionString, 1, questionString.length)),
                    rightAnswer);
            questions.add(question);
        }
        return questions;
    }
}
