package me.widua.databaseauthorization.api;

import me.widua.databaseauthorization.manager.QuestionManager;
import me.widua.databaseauthorization.model.QuestionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class QuestionApi {

    private QuestionManager manager;

    public QuestionApi(QuestionManager manager){
        this.manager = manager;
    }

    @GetMapping("/{questionCollection}")
    @PreAuthorize("hasAuthority('question:read')")
    public ResponseEntity<List<QuestionModel>> getQuestionsByCollection(@PathVariable Integer questionCollection){
        return ResponseEntity.ok(manager.findAllQuestionsByCollection(questionCollection));
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('question:write')")
    public ResponseEntity<String> addQuestion(@RequestBody QuestionModel question){
        manager.addQuestion(question);
        return ResponseEntity.ok("Question successfully add");
    }

    @DeleteMapping("/{questionID}/delete")
    @PreAuthorize("hasAuthority('question:write')")
    public ResponseEntity<String> deleteQuestion(@PathVariable String questionID){
        if (manager.isQuestionExist(questionID)){
            manager.deleteQuestion(questionID);
            return ResponseEntity.ok("Question successfully deleted");
        } else {
            return ResponseEntity.ok("Question does not exist");
        }
    }

    @PutMapping("/{questionID}/change")
    @PreAuthorize("hasAuthority('question:write')")
    public ResponseEntity<String> changeQuestion(@PathVariable String questionID, @Valid @RequestBody QuestionModel question, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.ok(errors.getAllErrors().toString());
        }
        if (manager.isQuestionExist(questionID)){
        manager.changeQuestion(questionID,question);
            return ResponseEntity.ok("Question successfully changed!");
        } else {
            return ResponseEntity.ok("Question does not exist!");
        }


    }




}
