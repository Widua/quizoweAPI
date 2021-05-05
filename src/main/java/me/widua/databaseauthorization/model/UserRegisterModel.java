package me.widua.databaseauthorization.model;

import javax.validation.constraints.Email;

public class UserRegisterModel {

    private String username;
    private String password;
    private String retypedPassword;


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRetypedPassword() {
        return retypedPassword;
    }

}
