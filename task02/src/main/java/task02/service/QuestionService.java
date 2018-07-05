package task02.service;

import org.springframework.context.MessageSource;
import task02.model.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class QuestionService {

    private final String questionFileName;
    private MessageSource messageSource;

    public QuestionService(String questionFileName, MessageSource messageSource) {
        this.questionFileName = questionFileName;
        this.messageSource = messageSource;
    }

    public List<Question> loadQuestion(Locale locale) throws IOException {
        return readCSVInQuestions(questionFileName, locale);
    }

    private List<Question> readCSVInQuestions(String fileName, Locale locale) throws IOException {
        List<Question> questions = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName)))) {
            String line = null;
            while ((line = getNextString(bufferedReader)) != null) {
                String[] questionString = messageSource.getMessage(line, null, locale).split(",");
                Integer rightAnswer = Integer.parseInt(getNextString(bufferedReader));
                Question question = new Question(
                        questionString[0],
                        Arrays.asList(Arrays.copyOfRange(questionString, 1, questionString.length)),
                        rightAnswer);
                questions.add(question);
            }
        } catch (IOException e) {
            throw new IOException("Error during read \"" + fileName + "\".");
        }
        return questions;
    }

    private String getNextString(BufferedReader bufferedReader) throws IOException {
        String inputString = bufferedReader.readLine();
        if (inputString == null) {
            return null;
        }
        while (inputString.length() == 0 || inputString.startsWith("#")) {
            inputString = bufferedReader.readLine();
        }
        return inputString;
    }
}
