package task04.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@PropertySource("classpath:questions.yml")
@ConfigurationProperties("objects")
public class QuestionUtilBean {
    private List<QuestionUtilBean.Question> questions = new ArrayList<>();

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
}
