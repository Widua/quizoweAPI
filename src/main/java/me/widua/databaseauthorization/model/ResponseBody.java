package me.widua.databaseauthorization.model;

import org.springframework.http.HttpStatus;

public class ResponseBody {
    private HttpStatus stauts ;
    private String message ;
    private Object optional ;

    public ResponseBody(HttpStatus stauts, String message, Object optional) {
        this.stauts = stauts;
        this.message = message;
        this.optional = optional;
    }

    public ResponseBody(HttpStatus stauts, String message) {
        this.stauts = stauts;
        this.message = message;
    }

    public HttpStatus getStauts() {
        return stauts;
    }

    public void setStauts(HttpStatus stauts) {
        this.stauts = stauts;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getOptional() {
        return optional;
    }

    public void setOptional(Object optional) {
        this.optional = optional;
    }
}
