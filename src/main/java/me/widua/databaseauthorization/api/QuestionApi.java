package me.widua.databaseauthorization.api;

import me.widua.databaseauthorization.manager.QuestionManager;
import me.widua.databaseauthorization.model.QuestionModel;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class QuestionApi {

    private final QuestionManager manager;


    public QuestionApi(QuestionManager manager){ this.manager = manager; }

    @GetMapping("/{questionCollection}")
    @PreAuthorize("hasAuthority('question:read')")
    public ResponseEntity<List<QuestionModel>> getQuestionsByCollection(@PathVariable String questionCollection){
        return ResponseEntity.ok(manager.findAllQuestionsByCollection(questionCollection));
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('question:write')")
    public ResponseEntity<String> addQuestion(@Valid @RequestBody QuestionModel question, Errors errors){
        if (errors.hasErrors()){
            List<String> errorMessages = errors.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(errorMessages.toString());
        } else {
            manager.addQuestion(question);
            return ResponseEntity.ok("Question successfully add");
        }

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
            List<String> errorMessages = errors.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(errorMessages.toString());
        }
        if (manager.isQuestionExist(questionID)){
        manager.changeQuestion(questionID,question);
            return ResponseEntity.ok("Question successfully changed!");
        } else {
            return ResponseEntity.ok("Question does not exist!");
        }
    }


    @GetMapping("/{questionCollection}/randomQuestion")
    @PreAuthorize("hasAuthority('question:read')")
    public ResponseEntity<QuestionModel> getRandomQuestion(@PathVariable String questionCollection){

        QuestionModel question = manager.getRandomQuestion(questionCollection);
        if (question!=null){
            return ResponseEntity.ok(question);
        } else {
            return ResponseEntity.noContent().build();
        }
    }




}
