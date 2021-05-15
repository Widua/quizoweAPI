package me.widua.databaseauthorization.manager;

import me.widua.databaseauthorization.model.QuestionModel;
import me.widua.databaseauthorization.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

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

    public List<QuestionModel> findAllQuestionsByCollection(String collection){
        return repository.getAllByCollectionName(collection);
    }

    public QuestionModel getQuestionById(String id){
        if (repository.findById(id).isPresent()){
            return repository.findById(id).get();
        } else return null;
    }

    public void deleteQuestion(String id){
        repository.deleteById(id);
    }

    public void changeQuestion(String id, QuestionModel question){
        question.setQuestionId(id);
        repository.save(question);
    }

    public QuestionModel getRandomQuestion(String questionCollection){
        List<QuestionModel> questions = findAllQuestionsByCollection(questionCollection);
        if (!questions.isEmpty()){
            Random random = new Random();
            int questionNumber = random.nextInt(questions.size());
            return questions.get(questionNumber);
        } else {
            return null;
        }

    }

    public boolean isQuestionExist(String id){
        if (repository.findById(id).isPresent()){
            return true;
        } else {
            return false;
        }
    }
}
