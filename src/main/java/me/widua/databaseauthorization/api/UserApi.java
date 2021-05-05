package me.widua.databaseauthorization.api;

import me.widua.databaseauthorization.manager.UserManager;
import me.widua.databaseauthorization.model.UserModel;
import me.widua.databaseauthorization.model.UserRegisterModel;
import me.widua.databaseauthorization.roles.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static me.widua.databaseauthorization.roles.Roles.USER;

@RestController
@RequestMapping("/user/")
public class UserApi {

    private final UserManager userManager ;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserApi(UserManager userManager, PasswordEncoder passwordEncoder){
        this.userManager = userManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserRegisterModel userRegisterModel, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors.toString());
        } else {

            if (userRegisterModel.getPassword().equals(userRegisterModel.getRetypedPassword())){
                if ( userManager.findUserByUsername(userRegisterModel.getUsername()) == null ){
                    UserModel userModel = new UserModel(
                            userRegisterModel.getUsername(),
                            passwordEncoder.encode(userRegisterModel.getPassword()) ,
                            USER.getGrangedAuthorities(),
                            true,
                            true,
                            true,
                            true);

                    userManager.registerUser(userModel);
                    ResponseEntity<String> response = ResponseEntity.ok("User register correctly!");
                    return response ;
                } else {
                    return ResponseEntity.ok("Username exist!");
                }
            } else {
                ResponseEntity<String> response = ResponseEntity.badRequest().body("Not the same password");
                return response;
            }



        }
    }

    @PutMapping("/{username}/promote")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<String> promoteUser(@PathVariable(name = "username") String username, @RequestParam String roleName){
        UserModel user = userManager.findUserByUsername(username)
                .orElseThrow(() ->  new UsernameNotFoundException(String.format("User %s does not exist", username)));

        if( userManager.findRoleByName(roleName) == null ){
            return ResponseEntity.badRequest().body(String.format("Role %s does not exist!",roleName));
        } else {
            Roles role = userManager.findRoleByName(roleName);
            userManager.promoteUser(user, role );
            return ResponseEntity.ok(String.format("%s successfully promoted!", username));
        }

    }

    @DeleteMapping("/{username}/delete")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "username") String username ){
        if (userManager.findUserByUsername(username) == null){
            return ResponseEntity.ok().body(String.format("User %s does not exist", username));
        } else {
            userManager.deleteUser(username);
            return ResponseEntity.ok(String.format("User %s successfully deleted!", username));
        }
    }

    @PutMapping("/{username}/passwordChg")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<String> passwordChange( @PathVariable(name = "username") String username, @RequestBody String password ){

        if (userManager.findUserByUsername(username).isPresent()){
            UserModel user = userManager.findUserByUsername(username).get();
            userManager.changePassword(user,password);
            return ResponseEntity.ok("Password successfully changed!");
        } else {
            return ResponseEntity.badRequest().body(String.format("User %s does not exist", username));
        }

    }

}
