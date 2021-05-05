package me.widua.databaseauthorization.repository;

import me.widua.databaseauthorization.model.QuestionModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends MongoRepository<QuestionModel,String> {

    List<QuestionModel> getAllByQuestionCollection(int questionCollection);


}
