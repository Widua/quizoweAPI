package me.widua.databaseauthorization.roles;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static me.widua.databaseauthorization.roles.Permissions.*;

public enum Roles {

    ADMINISTRATOR(Sets.newHashSet(question_read, question_write, user_write)),
    QUIZADMIN(Sets.newHashSet(question_read,question_write)),
    USER(Sets.newHashSet(question_read));

    private Set<Permissions> permissions;


      Roles(Set<Permissions> permissions) {
          this.permissions = permissions;
    }

    public Set<Permissions> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrangedAuthorities (){
          Set<SimpleGrantedAuthority> authorities = getPermissions().stream()
                  .map( permission -> new SimpleGrantedAuthority(permission.getPermission()) )
                  .collect(Collectors.toSet());
          authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));

          return authorities;
    }


}
