package task01.utils;

import task01.model.Question;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    private Utils() {}

    public static List<Question> readCSVInQuestions(String fileName) throws FileNotFoundException {
        List<Question> questions = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Utils.class.getClassLoader().getResourceAsStream(fileName)))) {
            String inputString = bufferedReader.readLine();
            while (inputString.length() == 0 || inputString.startsWith("#")) {
                inputString = bufferedReader.readLine();
            }
            String[] questionString = inputString.split(",");
            inputString = bufferedReader.readLine();
            while (inputString.length() == 0 || inputString.startsWith("#")) {
                inputString = bufferedReader.readLine();
            }
            Integer rightAnswer = Integer.parseInt(inputString);
            Question question = new Question(
                    questionString[0],
                    Arrays.asList(Arrays.copyOfRange(questionString, 1, questionString.length)),
                    rightAnswer);
            questions.add(question);
        } catch (IOException e) {
            throw new FileNotFoundException("Error during read \"" + fileName + "\".");
        }
        return questions;
    }

    private String getNextString(BufferedReader bufferedReader) throws IOException {
        String inputString = bufferedReader.readLine();
        while (inputString.length() == 0 || inputString.startsWith("#")) {
            inputString = bufferedReader.readLine();
        }
        return inputString;
    }
}
