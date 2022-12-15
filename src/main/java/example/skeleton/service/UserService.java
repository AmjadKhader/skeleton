package example.skeleton.service;

import example.skeleton.model.UserEntity;
import example.skeleton.repo.UserRepository;
import example.skeleton.request.InsertUserRequest;
import example.skeleton.request.UserLoginRequest;
import example.skeleton.request.UserLogoutRequest;
import example.skeleton.request.UserUpdatePasswordRequest;
import example.skeleton.utils.PasswordEncoder;
import example.skeleton.utils.TokenParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import static example.skeleton.constant.Constants.ENCRYPTION_ERROR;
import static example.skeleton.constant.Constants.WRONG_CREDENTIALS;
import static example.skeleton.constant.Constants.userTokens;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Transactional
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
            if (userTokens.containsKey(userLoginRequest.getUsername())
                    && TokenParser.validateToken(userTokens.get(userLoginRequest.getUsername()))) {
                return userTokens.get(userLoginRequest.getUsername());
            }

            String token = TokenParser.generateToken(userLoginRequest.getUsername());
            userTokens.put(userLoginRequest.getUsername(), token);

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
            if (userTokens.containsKey(userLogoutRequest.getUsername())) {

                String uToken = userTokens.get(userLogoutRequest.getUsername());

                userTokens.remove(userLogoutRequest.getUsername()); // remove from local map ...
                TokenParser.invalidateToken(uToken); // remove from claims ...
                return true;
            }

            throw new RuntimeException("User " + userLogoutRequest.getUsername() + " is not logged in ...");

        } catch (RuntimeException runtimeException) {
            throw new RuntimeException(runtimeException.getMessage());
        }
    }

    @Transactional
    public String addUser(InsertUserRequest insertUserRequest) {
        try {
            // Encrypt the given password ...
            String encryptedPassword = PasswordEncoder.encode(
                    insertUserRequest.getUsername().toLowerCase(Locale.ROOT) + insertUserRequest.getPassword());

            UserEntity user = userRepository.save(new UserEntity(insertUserRequest.getUsername(), encryptedPassword));
            return String.valueOf(user.getId());
        } catch (Exception exception) {
            throw new RuntimeException("Error creating new user ...");
        }
    }

    @Transactional
    public String updatePassword(String userId, UserUpdatePasswordRequest updatePasswordRequest) {
        try {

            Optional<UserEntity> user = userRepository.findById(Long.valueOf(userId));
            if (!user.isPresent()) {
                throw new RuntimeException("User was not found ...");
            }

            // Encrypt the given password ...
            String encryptedPassword = PasswordEncoder.encode(
                    user.get().getUsername().toLowerCase(Locale.ROOT) + updatePasswordRequest.getPassword());

            user.get().setPassword(encryptedPassword);

            userRepository.save(user.get());
            return String.valueOf(user.get().getId());
        } catch (Exception exception) {
            throw new RuntimeException("Error creating new user ...");
        }
    }

    @Transactional
    public void deleteUser(String userId) {
        try {
            userRepository.deleteById(Long.valueOf(userId));
        } catch (Exception exception) {
            throw new RuntimeException("Error creating new user ...");
        }
    }
}