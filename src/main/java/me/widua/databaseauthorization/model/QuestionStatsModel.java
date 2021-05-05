package me.widua.databaseauthorization.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("Statistics")
public class QuestionStatsModel {

    @Id
    private String id;
    private QuestionModel question;
    private String creator;
    private LocalDate creationDate;
    private int fails;
    private int correctAnswers;
    private String mostFailedUser;
    private String mostSucceedUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public QuestionModel getQuestion() {
        return question;
    }

    public void setQuestion(QuestionModel question) {
        this.question = question;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public int getFails() {
        return fails;
    }

    public void setFails(int fails) {
        this.fails = fails;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public String getMostFailedUser() {
        return mostFailedUser;
    }

    public void setMostFailedUser(String mostFailedUser) {
        this.mostFailedUser = mostFailedUser;
    }

    public String getMostSucceedUser() {
        return mostSucceedUser;
    }

    public void setMostSucceedUser(String mostSucceedUser) {
        this.mostSucceedUser = mostSucceedUser;
    }

    @Override
    public String toString() {
        return "QuestionStatsModel{" +
                "id='" + id + '\'' +
                ", question=" + question +
                ", creator='" + creator + '\'' +
                ", creationDate=" + creationDate +
                ", fails=" + fails +
                ", correctAnswers=" + correctAnswers +
                ", mostFailedUser='" + mostFailedUser + '\'' +
                ", mostSucceedUser='" + mostSucceedUser + '\'' +
                '}';
    }
}
