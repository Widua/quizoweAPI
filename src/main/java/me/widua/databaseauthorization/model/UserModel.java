package me.widua.databaseauthorization.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Document(collection = "Users")
public class UserModel implements UserDetails {
    @Id
    private String id;

    private String username;
    private String password;
    private Set<? extends GrantedAuthority> authorities;
    private boolean isAccountNotExpired;
    private boolean isAccountNotLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    public UserModel(String username,
                     String password,
                     Set<? extends GrantedAuthority> authorities,
                     boolean isAccountNotExpired,
                     boolean isAccountNotLocked,
                     boolean isCredentialsNonExpired,
                     boolean isEnabled)
    {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.isAccountNotExpired = isAccountNotExpired;
        this.isAccountNotLocked = isAccountNotLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Set<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setAccountNotExpired(boolean accountNotExpired) {
        isAccountNotExpired = accountNotExpired;
    }

    public void setAccountNotLocked(boolean accountNotLocked) {
        isAccountNotLocked = accountNotLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNotExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNotLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                ", isAccountNotExpired=" + isAccountNotExpired +
                ", isAccountNotLocked=" + isAccountNotLocked +
                ", isCredentialsNonExpired=" + isCredentialsNonExpired +
                ", isEnabled=" + isEnabled +
                '}';
    }




}
