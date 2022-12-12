package example.skeleton.controller;

import example.skeleton.request.UserLoginRequest;
import example.skeleton.request.UserLogoutRequest;
import example.skeleton.response.UserLoginResponse;
import example.skeleton.response.UserLogoutResponse;
import example.skeleton.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static example.skeleton.constant.Constants.ENCRYPTION_ERROR;
import static example.skeleton.constant.Constants.WRONG_CREDENTIALS;
import static org.apache.logging.log4j.util.Strings.isBlank;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<?> userLogin(@RequestBody UserLoginRequest userLoginRequest) {
        try {
            if (Objects.isNull(userLoginRequest)) {
                return new ResponseEntity<>(new UserLoginResponse("", "Body request is mandatory", false), HttpStatus.BAD_REQUEST);
            }

            if (isBlank(userLoginRequest.getUsername()) || isBlank(userLoginRequest.getPassword())) {
                return new ResponseEntity<>(new UserLoginResponse("", "Username or password is empty", false), HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(new UserLoginResponse(userService.processLogin(userLoginRequest), "", true), HttpStatus.OK);

        } catch (NoSuchAlgorithmException e) {
            return new ResponseEntity<>(new UserLoginResponse("", ENCRYPTION_ERROR, false), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(new UserLoginResponse("", WRONG_CREDENTIALS, false), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/logout", produces = "application/json")
    public ResponseEntity<?> userLogout(@RequestBody UserLogoutRequest userLogoutRequest) {
        try {
            if (Objects.isNull(userLogoutRequest)) {
                return new ResponseEntity<>(new UserLogoutResponse("Body request is mandatory", false),
                        HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(new UserLogoutResponse("", userService.processLogout(userLogoutRequest)), HttpStatus.OK);

        } catch (RuntimeException exception) {
            return new ResponseEntity<>(new UserLogoutResponse(exception.getMessage(), false), HttpStatus.OK);
        }
    }
}
