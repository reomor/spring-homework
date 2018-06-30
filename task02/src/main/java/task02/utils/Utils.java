package task02.utils;

import task02.model.Question;
import task02.service.QuestionService;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Utils {

    private Utils() {}

    public static List<Question> readCSVInQuestions(String fileName) throws IOException {
        List<Question> questions = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Utils.class.getClassLoader().getResourceAsStream(fileName)))) {
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
