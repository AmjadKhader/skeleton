package example.skeleton.service;

import example.skeleton.model.UserEntity;
import example.skeleton.repo.UserRepository;
import example.skeleton.request.UserLoginRequest;
import example.skeleton.request.UserLogoutRequest;
import example.skeleton.utils.PasswordEncoder;
import example.skeleton.utils.TokenParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Objects;

import static example.skeleton.constant.Constants.ENCRYPTION_ERROR;
import static example.skeleton.constant.Constants.WRONG_CREDENTIALS;
import static example.skeleton.constant.Constants.userToken;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public String processLogin(UserLoginRequest userLoginRequest) throws NoSuchAlgorithmException {
        try {
            // Encrypt the given password ...
            String encryptedPassword = PasswordEncoder.encode(
                    userLoginRequest.getUsername().toLowerCase(Locale.ROOT) + userLoginRequest.getPassword());

            // search for the user in the DB, if exits then the given username and password are correct ...
            UserEntity userEntity = userRepository.findByUsernameAndPassword(
                    userLoginRequest.getUsername(), encryptedPassword);

            if (Objects.isNull(userEntity)) {
                // Wrong username or password ...
                throw new RuntimeException(WRONG_CREDENTIALS);
            }

            // if this user is already logged in, return its token ...
            if (userToken.containsKey(userLoginRequest.getUsername())
                    && TokenParser.validateToken(userToken.get(userLoginRequest.getUsername()))) {
                return userToken.get(userLoginRequest.getUsername());
            }

            String token = TokenParser.generateToken(userLoginRequest.getUsername());
            userToken.put(userLoginRequest.getUsername(), token);

            return token;
        } catch (RuntimeException wrongUsernameAndPasswordException) {
            throw new RuntimeException(wrongUsernameAndPasswordException.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException(ENCRYPTION_ERROR);
        }
    }

    public boolean processLogout(UserLogoutRequest userLogoutRequest) {
        try {
            // if this user is already logged in, remove its token ...
            if (userToken.containsKey(userLogoutRequest.getUsername())) {

                String uToken = userToken.get(userLogoutRequest.getUsername());

                userToken.remove(userLogoutRequest.getUsername()); // remove from local map ...
                TokenParser.invalidateToken(uToken); // remove from claims ...
                return true;
            }

            throw new RuntimeException("User " + userLogoutRequest.getUsername() + " is not logged in ...");

        } catch (RuntimeException runtimeException) {
            throw new RuntimeException(runtimeException.getMessage());
        }
    }
}