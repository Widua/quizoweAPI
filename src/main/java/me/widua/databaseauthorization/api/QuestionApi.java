package me.widua.databaseauthorization.api;

import me.widua.databaseauthorization.manager.QuestionManager;
import me.widua.databaseauthorization.model.QuestionModel;
import me.widua.databaseauthorization.model.ResponseBody;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
@CrossOrigin
public class QuestionApi {

    private final QuestionManager manager;


    public QuestionApi(QuestionManager manager){ this.manager = manager; }

    @GetMapping("/getCollection")
    @PreAuthorize("hasAuthority('question:read')")
    public ResponseEntity<List<QuestionModel>> getQuestionsByCollection(@RequestBody String questionCollection){
        return ResponseEntity.ok(manager.findAllQuestionsByCollection(questionCollection));
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('question:write')")
    public ResponseEntity<ResponseBody> addQuestion(@Valid @RequestBody QuestionModel question, Errors errors){
        if (errors.hasErrors()){
            List<String> errorMessages = errors.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ResponseBody(HttpStatus.BAD_REQUEST, errorMessages.toString()));
        } else {
            if (manager.doesSameQuestionExist(question) ){
                return ResponseEntity.badRequest().body(new ResponseBody(HttpStatus.BAD_REQUEST, "This question exist in that collection!")) ;
            }
            question.setCollectionName(question.getCollectionName().toLowerCase(Locale.ROOT));
            manager.addQuestion(question);
            return ResponseEntity.ok(new ResponseBody(HttpStatus.OK, "Question successfully add" ));
        }

    }

    @DeleteMapping("/{questionID}/delete")
    @PreAuthorize("hasAuthority('question:write')")
    public ResponseEntity<ResponseBody> deleteQuestion(@PathVariable String questionID){
        if (manager.isQuestionExist(questionID)){
            manager.deleteQuestion(questionID);
            return ResponseEntity.ok(new ResponseBody(HttpStatus.OK , "Question successfully deleted" ));
        } else {
            return ResponseEntity.badRequest().body(new ResponseBody(HttpStatus.NOT_FOUND , "Question does not exist" ));
        }
    }

    @PutMapping("/{questionID}/change")
    @PreAuthorize("hasAuthority('question:write')")
    public ResponseEntity<ResponseBody> changeQuestion(@PathVariable String questionID, @Valid @RequestBody QuestionModel question, Errors errors){
        if (errors.hasErrors()){
            List<String> errorMessages = errors.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ResponseBody(HttpStatus.BAD_REQUEST, errorMessages.toString() ));
        }
        if (manager.isQuestionExist(questionID)){
        manager.changeQuestion(questionID,question);
            return ResponseEntity.ok(new ResponseBody(HttpStatus.OK , "Question successfully changed!" ));
        } else {
            return ResponseEntity.badRequest().body(new ResponseBody(HttpStatus.NOT_FOUND, "Question does not exist!"));
        }
    }


    @GetMapping("/randomQuestion")
    @PreAuthorize("hasAuthority('question:read')")
    public ResponseEntity<ResponseBody> getRandomQuestion(){

        QuestionModel question = manager.getRandomQuestion();
        if (question!=null){
            return ResponseEntity.ok(new ResponseBody(HttpStatus.OK , "Question found" , question));
        } else {
            return ResponseEntity.badRequest().body(new ResponseBody( HttpStatus.NOT_FOUND , "Question not found!" )) ;
        }
    }




}
