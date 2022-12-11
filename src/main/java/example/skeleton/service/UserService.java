package example.skeleton.service;

import example.skeleton.model.User;
import example.skeleton.model.UserEntity;
import example.skeleton.repo.UserRepository;
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

    public String processLogin(User user) throws NoSuchAlgorithmException {
        try {
            // Encrypt the given password ...
            String encryptedPassword = PasswordEncoder.encode(
                    user.getUsername().toLowerCase(Locale.ROOT) + user.getPassword());

            // search for the user in the DB, if exits then the given username and password are correct ...
            UserEntity userEntity = userRepository.findByUsernameAndPassword(
                    user.getUsername(), encryptedPassword);

            if (Objects.isNull(userEntity)) {
                // Wrong username or password ...
                throw new RuntimeException(WRONG_CREDENTIALS);
            }

            // if this user is already logged in, return its token ...
            if (userToken.containsKey(user.getUsername()) && TokenParser.validateToken(userToken.get(user.getUsername()))) {
                return userToken.get(user.getUsername());
            }

            String token = TokenParser.generateToken();
            userToken.put(user.getUsername(), token);

            return token;
        } catch (RuntimeException wrongUsernameAndPasswordException) {
            throw new RuntimeException(wrongUsernameAndPasswordException.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException(ENCRYPTION_ERROR);
        }
    }
}