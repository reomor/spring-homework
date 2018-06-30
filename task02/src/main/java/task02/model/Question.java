package task02.model;

import java.util.List;
import java.util.Objects;

public class Question {
    private String questionString;
    private List<String> answerVariants;
    private Integer answer;
    private Integer rightAnswer;
    private User user;

    public Question(String questionString, List<String> answerVariants, Integer rightAnswer) {
        this.questionString = questionString;
        this.answerVariants = answerVariants;
        this.rightAnswer = rightAnswer;
    }

    public String getQuestionString() {
        return questionString;
    }

    public List<String> getAnswerVariants() {
        return answerVariants;
    }

    public Integer getRightAnswer() {
        return rightAnswer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public Integer getAnswer() {
        return answer;
    }

    public boolean isRight() {
        return this.rightAnswer.equals(this.answer);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(questionString, question.questionString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionString);
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionString='" + questionString + '\'' +
                ", answerVariants=" + answerVariants +
                ", answer=" + answer +
                ", rightAnswer=" + rightAnswer +
                '}';
    }
}
