package me.widua.databaseauthorization.api;

import me.widua.databaseauthorization.manager.UserManager;
import me.widua.databaseauthorization.model.ResponseBody;
import me.widua.databaseauthorization.model.UserModel;
import me.widua.databaseauthorization.model.UserRegisterModel;
import me.widua.databaseauthorization.roles.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static me.widua.databaseauthorization.roles.Roles.ADMINISTRATOR;
import static me.widua.databaseauthorization.roles.Roles.USER;

@RestController
@RequestMapping("/user/")
@CrossOrigin
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
    public ResponseEntity<ResponseBody> registerUser(@RequestBody @Valid UserRegisterModel userRegisterModel, Errors errors){


        if (errors.hasErrors()){
            return ResponseEntity.badRequest().body(new ResponseBody(HttpStatus.BAD_REQUEST, errors.toString()));
        } else {

            if (userRegisterModel.getPassword().equals(userRegisterModel.getRetypedPassword())){
                if (userManager.findUserByUsername(userRegisterModel.getUsername()).isEmpty()){
                    UserModel userModel = new UserModel(
                            userRegisterModel.getUsername(),
                            passwordEncoder.encode(userRegisterModel.getPassword()) ,
                            USER.getGrangedAuthorities(),
                            true,
                            true,
                            true,
                            true);

                    if (userManager.doesAnyAccountExist()){
                        userManager.registerUser(userModel);
                    } else {
                        userModel.setAuthorities(ADMINISTRATOR.getGrangedAuthorities());
                        userManager.registerUser(userModel);
                    }


                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseBody(HttpStatus.OK, "User Registred correctly!"));
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseBody(HttpStatus.BAD_REQUEST , "Username exist in database!"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseBody(HttpStatus.BAD_REQUEST , "Passwords not same!"));
            }



        }
    }

    @PutMapping("/{username}/promote")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<ResponseBody> promoteUser(@PathVariable(name = "username") String username, @RequestParam String roleName){
        UserModel user = userManager.findUserByUsername(username)
                .orElseThrow(() ->  new UsernameNotFoundException(String.format("User %s does not exist", username)));

        if( userManager.findRoleByName(roleName) == null ){
            return ResponseEntity.badRequest().body(new ResponseBody(HttpStatus.BAD_REQUEST , String.format("Role %s does not exist!",roleName )));
        } else {
            Roles role = userManager.findRoleByName(roleName);
            userManager.promoteUser(user, role );
            return ResponseEntity.ok( new ResponseBody(HttpStatus.OK , String.format("%s successfully promoted!", username) ));
        }

    }

    @DeleteMapping("/{username}/delete")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<ResponseBody> deleteUser(@PathVariable(name = "username") String username ){
        if (!userManager.findUserByUsername(username).isPresent()){
            return ResponseEntity.ok().body(new ResponseBody( HttpStatus.OK , String.format("User %s does not exist", username)  ));
        } else {
            userManager.deleteUser(username);
            return ResponseEntity.ok(new ResponseBody(HttpStatus.OK , String.format("User %s successfully deleted!", username)));
        }
    }

    @PutMapping("/{username}/passwordChg")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<ResponseBody> passwordChange( @PathVariable(name = "username") String username, @RequestBody String password ){

        if (userManager.findUserByUsername(username).isPresent()){
            UserModel user = userManager.findUserByUsername(username).get();
            userManager.changePassword(user,password);
            return ResponseEntity.ok(new ResponseBody(HttpStatus.OK , "Password changed correctly"));
        } else {
            return ResponseEntity.badRequest().body(new ResponseBody(HttpStatus.BAD_REQUEST , String.format("User %s does not exist", username) ));
        }

    }

}
