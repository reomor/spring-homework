package task01.service;

import task01.model.Question;
import task01.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class QuestionService {

    private final String PROPERTIES = "test.properties";

    private Properties getProperties() throws FileNotFoundException {
        Properties properties = new Properties();
        URL url = QuestionService.class.getResource("/" + PROPERTIES);
        if (url == null) {
            System.out.println("Properties file " + PROPERTIES + " doesn't exist");
            return new Properties();
        }
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(PROPERTIES));
        } catch (IOException e) {
            throw new FileNotFoundException("Properties file is not found in classpath.");
        }
        return properties;
    }

    public List<Question> loadQuestion() throws IOException {
        List<Question> questions = null;
        String property = getProperties().getProperty("question.file");
        if (property == null) {
            return new ArrayList<>();
        }
        questions = Utils.readCSVInQuestions(getProperties().getProperty("question.file"));
        return questions;
    }
}
