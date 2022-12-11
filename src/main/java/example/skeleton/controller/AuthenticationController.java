package example.skeleton.controller;

import example.skeleton.model.User;
import example.skeleton.response.UserLoginResponse;
import example.skeleton.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static example.skeleton.constant.Constants.ENCRYPTION_ERROR;
import static example.skeleton.constant.Constants.WRONG_CREDENTIALS;
import static org.apache.logging.log4j.util.Strings.isBlank;

@RestController()
public class AuthenticationController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/authentication/login", produces = "application/json")
    public ResponseEntity<?> userLogin(@RequestBody User user) {
        try {
            if (Objects.isNull(user)) {
                return new ResponseEntity<>(new UserLoginResponse("", "Body request is mandatory", false), HttpStatus.BAD_REQUEST);
            }

            if (isBlank(user.getUsername()) || isBlank(user.getPassword())) {
                return new ResponseEntity<>(new UserLoginResponse("", "Username or password is empty", false), HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(new UserLoginResponse(userService.processLogin(user), "", true), HttpStatus.OK);

        } catch (NoSuchAlgorithmException e) {
            return new ResponseEntity<>(new UserLoginResponse("", ENCRYPTION_ERROR, false), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(new UserLoginResponse("", WRONG_CREDENTIALS, false), HttpStatus.UNAUTHORIZED);
        }
    }
}
