package com.revature.pn.admin;

import com.revature.pn.common.exceptions.InvalidRequestException;
import com.revature.pn.common.exceptions.ResourceNotFoundException;
import com.revature.pn.users.User;
import com.revature.pn.users.UserDAO;
import com.revature.pn.users.UserResponse;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;



public class IsActiveService {

    private final UserDAO userDAO;

    public IsActiveService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<UserResponse> getAllUsers() {

        return userDAO.getAllUsers()
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(String userId) {
        if (userId == null || userId.length() <= 0) {
            throw new InvalidRequestException("A non-empty id must be provided!");
        }

        try {

            return userDAO.findUserById(UUID.fromString(userId))
                    .map(UserResponse::new)
                    .orElseThrow(ResourceNotFoundException::new);

        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("An invalid UUID string was provided.");
        }

    }

    public void updateIsActive(IsActiveRequest updateIsActive) {

        System.out.println(updateIsActive);

        User userToUpdate = userDAO.findUserById(UUID.fromString(updateIsActive.getUserId()))
                .orElseThrow(ResourceNotFoundException::new);

        if (updateIsActive.getIsActive() != null) {
            userToUpdate.setIsActive(updateIsActive.getIsActive());
        }

        userDAO.updateUser(userToUpdate);

    }

}