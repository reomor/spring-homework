package task01.service;

import task01.model.Question;
import task01.utils.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class QuestionService {

    private final String PROPERTIES = "test.properties";

    private Properties getProperties() throws FileNotFoundException {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(PROPERTIES));
        } catch (IOException e) {
            throw new FileNotFoundException("Properties file is not found in classpath.");
        }
        return properties;
    }

    public List<Question> loadQuestion() throws FileNotFoundException {
        Properties properties = getProperties();
        System.out.println(properties.getProperty("question.file"));
        List<Question> questions = null;
        try {
            questions = Utils.readCSVInQuestions("questions.csv");
            for (Question question : questions) {
                System.out.println(question);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
