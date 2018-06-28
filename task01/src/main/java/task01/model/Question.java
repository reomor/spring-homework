package task01.model;

import java.util.ArrayList;
import java.util.List;

public class Question {
    String question;
    List<String> answerVariants;
    List<Integer> answers;
    Integer rightAnswer;

    public Question(String question, List<String> answerVariants, Integer rightAnswer) {
        this.question = question;
        this.answerVariants = answerVariants;
        this.rightAnswer = rightAnswer;
        this.answers = new ArrayList<>();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswerVariants() {
        return answerVariants;
    }

    public void setAnswerVariants(List<String> answerVariants) {
        this.answerVariants = answerVariants;
    }

    public List<Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Integer> answers) {
        this.answers = answers;
    }

    public Integer getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(Integer rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answerVariants=" + answerVariants +
                ", answers=" + answers +
                ", rightAnswer=" + rightAnswer +
                '}';
    }
}
