package task05.setting;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
@ConfigurationProperties("application")
public class ApplicationSettings {

    private String fileQuestion;
    private Locale locale;
    private String loadType;
    private List<ApplicationSettings.Question> questions = new ArrayList<>();

    public static class Question {

        private String questionString;
        private Integer rightAnswer;

        public String getQuestionString() {
            return questionString;
        }

        public void setQuestionString(String questionString) {
            this.questionString = questionString;
        }

        public Integer getRightAnswer() {
            return rightAnswer;
        }

        public void setRightAnswer(Integer rightAnswer) {
            this.rightAnswer = rightAnswer;
        }
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getFileQuestion() {
        return fileQuestion;
    }

    public void setFileQuestion(String fileQuestion) {
        this.fileQuestion = fileQuestion;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getLoadType() {
        return loadType;
    }

    public void setLoadType(String loadType) {
        this.loadType = loadType;
    }
}
