package example.skeleton.controller;

import example.skeleton.request.InsertUserRequest;
import example.skeleton.request.UserUpdatePasswordRequest;
import example.skeleton.response.DeleteUserResponse;
import example.skeleton.response.InsertUserResponse;
import example.skeleton.response.UserUpdatePasswordResponse;
import example.skeleton.service.UserService;
import example.skeleton.utils.TokenParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> insertUser(@RequestBody InsertUserRequest insertUserRequest,
                                        @RequestHeader("Authorization") String jwtToken) {
        try {
            if (!TokenParser.validateToken(jwtToken)) {
                return new ResponseEntity<>(new InsertUserResponse("UNAUTHORIZED", "", false), HttpStatus.UNAUTHORIZED);
            }

            if (Objects.isNull(insertUserRequest)) {
                return new ResponseEntity<>(new InsertUserResponse("", "Body request is mandatory", false), HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(new InsertUserResponse(userService.addUser(insertUserRequest), "", true), HttpStatus.OK);

        } catch (RuntimeException exception) {
            return new ResponseEntity<>(new InsertUserResponse("", exception.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/change_password/{user_id}", produces = "application/json")
    public ResponseEntity<?> updateUserPassword(@PathVariable("user_id") String userId,
                                                @RequestBody UserUpdatePasswordRequest updatePasswordRequest,
                                                @RequestHeader("Authorization") String jwtToken) {
        try {
            if (!TokenParser.validateToken(jwtToken)) {
                return new ResponseEntity<>(new UserUpdatePasswordResponse("UNAUTHORIZED", "", false), HttpStatus.UNAUTHORIZED);
            }

            return new ResponseEntity<>(new UserUpdatePasswordResponse(userService.updatePassword(userId, updatePasswordRequest), "", true), HttpStatus.OK);

        } catch (RuntimeException exception) {
            return new ResponseEntity<>(new UserUpdatePasswordResponse("", exception.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{user_id}", produces = "application/json")
    public ResponseEntity<?> deleteUser(@PathVariable("user_id") String userId,
                                        @RequestHeader("Authorization") String jwtToken) {
        try {
            if (!TokenParser.validateToken(jwtToken)) {
                return new ResponseEntity<>(new DeleteUserResponse("UNAUTHORIZED", false), HttpStatus.UNAUTHORIZED);
            }

            userService.deleteUser(userId);
            return new ResponseEntity<>(new DeleteUserResponse("", true), HttpStatus.OK);

        } catch (RuntimeException exception) {
            return new ResponseEntity<>(new DeleteUserResponse(exception.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
