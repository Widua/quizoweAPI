package me.widua.databaseauthorization.repository;

import me.widua.databaseauthorization.model.QuestionModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends MongoRepository<QuestionModel,String> {

    @Query(value = "{'collectionName':?0}", collation = "{locale: 'en', strength: 2}")
    List<QuestionModel> getAllByCollectionName(String questionCollection);
    List<QuestionModel> findAll();
    List<QuestionModel> getAllByQuestionContentAndCollectionName(String questionName , String collectionName) ;


}
