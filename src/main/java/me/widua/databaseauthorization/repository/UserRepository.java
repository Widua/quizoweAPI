package me.widua.databaseauthorization.repository;

import me.widua.databaseauthorization.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {

    public Optional<UserModel> getUserModelByUsername(String username);
    public List<UserModel> findAll();


}
