package task02.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import task02.model.Question;
import task02.model.Test;
import task02.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

@Service
public class ConsoleInteractionService {

    private MessageSource messageSource;

    private BufferedReader reader;
    private Locale locale = Locale.ENGLISH;

    @Autowired
    public ConsoleInteractionService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void performAskStages(Test test) {
        BufferedReader reader = getBufferedReader();
        final User user;
        try {
            user = askUserInformation(reader, test);
            if (user == null) {
                System.out.println(messageSource.getMessage("error.enter", null, this.locale));
                return;
            }
            askQuestions(reader, test.getQuestions());
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeResources();
    }

    public BufferedReader getBufferedReader() {
        if (reader == null) {
            return new BufferedReader(new InputStreamReader(System.in));
        }
        return reader;
    }

    public void closeResources() {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException ignore) {
        }
    }

    public Locale askLocale() {
        BufferedReader reader = getBufferedReader();
        boolean valid = false;
        Integer answer = null;
        System.out.println("Change locale to RU?, 0 - no, 1 - yes");
        while(!valid) {
            try {
                answer = Integer.parseInt(reader.readLine());
                if (answer != 0 && answer != 1) {
                    System.out.println(messageSource.getMessage(
                            "error.answer.range",
                            new String[] {"0", "1"},
                            this.locale));
                } else {
                    valid = true;
                }
            } catch (IOException e) {
                System.out.println(messageSource.getMessage("error.answer.correctness",null, this.locale));
            }
        }
        this.locale = answer == 1 ? new Locale("ru", "RU") : Locale.ENGLISH;
        return this.locale;
    }

    private User askUserInformation(BufferedReader reader, Test test) throws IOException {
        User user = null;
        System.out.println(messageSource.getMessage("enter.user",null, this.locale));
        String[] userData = reader.readLine().split(" ");
        if (userData.length != 2) {
            return null;
        }
        user = new User(userData[0], userData[1]);
        test.setUser(user);
        return user;
    }

    private void askQuestions(BufferedReader reader, List<Question> questions) {
        for (Question question : questions) {
            System.out.println(question.getQuestionString());
            for (String variant : question.getAnswerVariants()) {
                System.out.println(variant);
            }
            getAnswer(reader, question);
        }
    }

    private Integer getAnswer(BufferedReader reader, Question question) {
        boolean valid = false;
        Integer answer = null;
        while(!valid) {
            try {
                answer = Integer.parseInt(reader.readLine());
                if (isValidAnswer(question, answer)) {
                    valid = true;
                } else {
                    System.out.println(messageSource.getMessage(
                            "error.answer.range",
                            new String[] {"0", Integer.toString(question.getAnswerVariants().size() - 1)},
                            this.locale));
                }
            } catch (IOException e) {
                System.out.println(messageSource.getMessage("error.answer.correctness",null, this.locale));
            }
        }
        question.setAnswer(answer);
        return answer;
    }

    private boolean isValidAnswer(Question question, int answer) {
        return answer >= 0 && answer < question.getAnswerVariants().size();
    }
}
