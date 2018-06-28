package task01.model;

import java.util.List;

public class Question {
    private String questionString;
    private List<String> answerVariants;
    private Integer answer;
    private Integer rightAnswer;

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
