package me.widua.databaseauthorization.roles;

public enum Permissions {

    user_write("user:write"),
    question_write("question:write"),
    question_read("question:read")

    ;

    private String permission;

    Permissions(String permission) {
        this.permission = permission;
    }


    public String getPermission() {
        return permission;
    }
}
