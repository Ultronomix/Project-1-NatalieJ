package com.revature.pn.auth;

import com.revature.pn.common.exceptions.AuthenticationException;
import com.revature.pn.common.exceptions.InvalidRequestException;
import com.revature.pn.users.UserDAO;
import com.revature.pn.users.UserResponse;

public class AuthService {

    private final UserDAO userDAO;

    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public AuthService() {

        userDAO = new UserDAO();
    }

    public UserResponse authenticate(Credentials credentials) {

        if (credentials == null) {
            throw new InvalidRequestException("The provided credentials object was found to be null.");
        }

        if (credentials.getUsername().length() < 4) {
            throw new InvalidRequestException("The provided username was not the correct length (must be at least 4 characters).");
        }

        if (credentials.getPassword().length() < 8) {
            throw new InvalidRequestException("The provided password was not the correct length (must be at least 8 characters).");
        }

        return userDAO.findUserByUsernameAndPassword(credentials.getUsername(), credentials.getPassword())
                .map(UserResponse::new)
                .orElseThrow(AuthenticationException::new);

    }
}
