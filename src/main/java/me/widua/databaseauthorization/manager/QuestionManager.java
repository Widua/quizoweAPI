package me.widua.databaseauthorization.manager;

import me.widua.databaseauthorization.model.QuestionModel;
import me.widua.databaseauthorization.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionManager {

    private final QuestionRepository repository;

    @Autowired
    public QuestionManager(QuestionRepository questionRepository){
        this.repository = questionRepository;
    }

    public void addQuestion(QuestionModel question){
        repository.save(question);
    }

    public List<QuestionModel> findAllQuestionsByCollection(int collection){
        return repository.getAllByQuestionCollection(collection);
    }

    public void deleteQuestion(String id){
        repository.deleteById(id);
    }

    public void changeQuestion(String id, QuestionModel question){
        question.setQuestionId(id);
        repository.save(question);
    }

    public boolean isQuestionExist(String id){
        if (repository.findById(id).isPresent()){
            return true;
        } else {
            return false;
        }
    }
}
