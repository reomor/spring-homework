package task04.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import task04.model.Question;
import task04.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

@Service
public class ConsoleInteractionService {

    private MessageSource messageSource;
    private Locale locale = Locale.ENGLISH;

    @Autowired
    public ConsoleInteractionService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public Locale askLocale() {
        boolean valid = false;
        Integer answer = null;
        System.out.println("Change locale to RU?, 0 - no, 1 - yes");
        try {
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)) {
                @Override
                public void close() throws IOException {/*NOP*/}
            }) {
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
            }
        } catch (IOException e) {
            return this.locale;
        }
        this.locale = answer == 1 ? new Locale("ru", "RU") : Locale.ENGLISH;
        return this.locale;
    }

    public User askUserInformation() throws IOException {
        User user = null;
        System.out.println(messageSource.getMessage("enter.user",null, this.locale));
        // because try-with-resources closes chain of resources recursively
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)) {
            @Override
            public void close() throws IOException {/*NOP*/}
        }) {
            String[] userData = reader.readLine().split(" ");
            if (userData.length != 2) {
                return null;
            }
            user = new User(userData[0], userData[1]);
        }
        return user;
    }

    public void askQuestions(List<Question> questions) {
        try {
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                for (Question question : questions) {
                    System.out.println(question.getQuestionString());
                    for (String variant : question.getAnswerVariants()) {
                        System.out.println(variant);
                    }
                    getAnswer(reader, question);
                }
            }
        } catch (IOException e) {/*NOP*/}
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
