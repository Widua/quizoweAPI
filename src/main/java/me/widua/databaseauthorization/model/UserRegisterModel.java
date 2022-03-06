package me.widua.databaseauthorization.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserRegisterModel {

    @NotEmpty(message = "Musi istnieć nazwa użytkownika")
    private String username;
    @NotEmpty(message = "Hasło musi zostać podane")
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
