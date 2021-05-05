package me.widua.databaseauthorization.model;

import me.widua.databaseauthorization.validation.MaxListSize;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

@Document("questions")
public class QuestionModel {

    @Id
    private String questionId;

    @NotNull(message = "You must insert number of question collection!")
    private int questionCollection;
    @NotNull(message = "You must enter the Question content!")
    private String questionContent;
    @NotNull(message = "You must enter Correct Answer!")
    private String correctAnswer;
    @NotNull(message = "You must enter other answers!")
    @MaxListSize(message = "You must enter three answers")
    private List<String> otherAnswers; //3


    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public int getQuestionCollection() {
        return questionCollection;
    }

    public void setQuestionCollection(int questionCollection) {
        this.questionCollection = questionCollection;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<String> getOtherAnswers() {
        return otherAnswers;
    }

    public void setOtherAnswers(List<String> otherAnswers) {
        this.otherAnswers = otherAnswers;
    }

    @Override
    public String toString() {
        return "QuestionModel{" +
                "questionId='" + questionId + '\'' +
                ", questionCollection=" + questionCollection +
                ", questionContent='" + questionContent + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", otherAnswers=" + otherAnswers +
                '}';
    }

    public QuestionModel(int questionCollection, String questionContent, String correctAnswer, List<String> otherAnswers) {
        this.questionCollection = questionCollection;
        this.questionContent = questionContent;
        this.correctAnswer = correctAnswer;
        this.otherAnswers = otherAnswers;
    }
}
