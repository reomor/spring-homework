package task01.utils;

import java.io.*;

public class Utils {

    private Utils() {}

    public static void readCSVInQuestions() {
        String file = "questions.csv";
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Utils.class.getClassLoader().getResourceAsStream(file)))) {
            String[] parts = bufferedReader.readLine().split(",");
            for (String part : parts) {
                System.out.print("->" + part + "<-");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
