package task02.service;

import task02.model.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionService {

    private final String questionFileName;

    public QuestionService(String questionFileName) {
        this.questionFileName = questionFileName;
    }

    public List<Question> loadQuestion() throws IOException {
        return readCSVInQuestions(questionFileName);
    }

    private List<Question> readCSVInQuestions(String fileName) throws IOException {
        List<Question> questions = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName)))) {
            String line = null;
            while ((line = getNextString(bufferedReader)) != null) {
                String[] questionString = line.split(",");
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

    private static String getNextString(BufferedReader bufferedReader) throws IOException {
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
