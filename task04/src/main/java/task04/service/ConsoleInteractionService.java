package task04.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import task04.model.Question;
import task04.model.User;
import task04.setting.ApplicationSettings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

@Service
public class ConsoleInteractionService {

    private MessageSource messageSource;
    private ApplicationSettings applicationSettings;

    private BufferedReader reader;

    @Autowired
    public ConsoleInteractionService(MessageSource messageSource, ApplicationSettings applicationSettings) {
        this.messageSource = messageSource;
        this.applicationSettings = applicationSettings;
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public Locale askLocale() {
        boolean valid = false;
        Integer answer = null;
        System.out.println("Change locale to RU?, 0 - no, 1 - yes");
        BufferedReader reader = getReader();
        while (!valid) {
            try {
                String line = reader.readLine();
                answer = Integer.parseInt(line);
                if (answer != 0 && answer != 1) {
                    System.out.println(messageSource.getMessage(
                            "error.answer.range",
                            new String[]{"0", "1"},
                            applicationSettings.getLocale()));
                } else {
                    valid = true;
                }
            } catch (IOException e) {
                System.out.println(messageSource.getMessage("error.answer.correctness", null, applicationSettings.getLocale()));
            }
        }
        Locale locale = answer == 1 ? new Locale("ru", "RU") : applicationSettings.getLocale();
        applicationSettings.setLocale(locale);
        return locale;
    }

    public User askUserInformation() throws IOException {
        User user = null;
        System.out.println(messageSource.getMessage("enter.user", null, applicationSettings.getLocale()));
        BufferedReader reader = getReader();
        String line = reader.readLine();
        String[] userData = line.split(" ");
        if (userData.length != 2) {
            return null;
        }
        user = new User(userData[0], userData[1]);
        return user;
    }

    public Integer askQuestions(List<Question> questions) {
            BufferedReader reader = getReader();
            for (Question question : questions) {
                System.out.println(question.getQuestionString());
                for (String variant : question.getAnswerVariants()) {
                    System.out.println(variant);
                }
                getAnswer(reader, question);
            }
        return questions.size();
    }

    private Integer getAnswer(BufferedReader reader, Question question) {
        boolean valid = false;
        Integer answer = null;
        while(!valid) {
            try {
                String line = reader.readLine();
                answer = Integer.parseInt(line);
                if (isValidAnswer(question, answer)) {
                    valid = true;
                } else {
                    System.out.println(messageSource.getMessage(
                            "error.answer.range",
                            new String[] {"0", Integer.toString(question.getAnswerVariants().size() - 1)},
                            applicationSettings.getLocale()));
                }
            } catch (IOException e) {
                System.out.println(messageSource.getMessage("error.answer.correctness",null, applicationSettings.getLocale()));
            }
        }
        question.setAnswer(answer);
        return answer;
    }

    private boolean isValidAnswer(Question question, int answer) {
        return answer >= 0 && answer < question.getAnswerVariants().size();
    }

    private BufferedReader getReader() {
        if (reader == null) {
            return new BufferedReader(new InputStreamReader(System.in));
        }
        return reader;
    }
}
