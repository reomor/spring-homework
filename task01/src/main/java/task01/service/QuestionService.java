package task01.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class QuestionService {
    private Properties getProperties() throws FileNotFoundException {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("test.properties"));
        } catch (IOException e) {
            throw new FileNotFoundException("Properties file is not found in classpath");
        }
        return properties;
    }

    public String loadQuestion() throws FileNotFoundException {
        Properties properties = getProperties();
        System.out.println(properties.getProperty("question.file"));
        return "";
    }
}
