package me.widua.databaseauthorization.repository;

import me.widua.databaseauthorization.model.QuestionModel;
import me.widua.databaseauthorization.model.QuestionStatsModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatsRepository extends MongoRepository<QuestionStatsModel, String> {

    List<QuestionStatsModel> getAllByQuestion(QuestionModel questionModel);
    List<QuestionStatsModel> getAllByQuestion_QuestionCollection(Integer questionCollection);

}
