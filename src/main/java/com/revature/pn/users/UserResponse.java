package com.revature.pn.users;

import java.io.Serializable;
import java.util.Objects;

public class UserResponse implements Serializable {

    private String userId;

    private String username;
    private String email;
    private String givenName;
    private String surname;

    private boolean isActive;
    private String role;

    public UserResponse(Object o) {
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserResponse(User subject) {
        this.userId = subject.getUserId();
        this.username = subject.getUsername();
        this.email = subject.getEmail();
        this.givenName = subject.getGivenName();
        this.surname = subject.getSurname();
        this.isActive = subject.getIsActive();
        this.role = subject.getRole();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResponse that = (UserResponse) o;
        return isActive == that.isActive && Objects.equals(userId, that.userId) && Objects.equals(username, that.username) && Objects.equals(email, that.email) && Objects.equals(givenName, that.givenName) && Objects.equals(surname, that.surname) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, email, givenName, surname, isActive, role);
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", givenName='" + givenName + '\'' +
                ", surname='" + surname + '\'' +
                ", isActive=" + isActive +
                ", role_='" + role + '\'' +
                '}';
    }

    public String getId() {
        return userId;
    }
}