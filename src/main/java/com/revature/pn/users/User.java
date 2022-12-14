package com.revature.pn.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.pn.common.role.Role;

import java.util.Objects;

// POJO = Plain Ol' Java Objects
public class User {

    private String id;
    private String givenName;
    private String surname;
    private String email;
    private String username;
    private String password;
    private String role;

    public User(String userResponse, ObjectMapper jsonMapper) {
        super();
    }

    public User(String id, String givenName, String surname, String email, String username, String password, Role role) {
        this.id = id;
        this.givenName = givenName;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = String.valueOf(role);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(givenName, user.givenName) && Objects.equals(surname, user.surname) && Objects.equals(email, user.email) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, givenName, surname, email, username, password, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", givenName='" + givenName + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }


    public String getUserId() {
        return id;
    }

    public void setUserId(String user_id) {
    }

    public void setIsActive(boolean is_active) {
    }

    public boolean getIsActive() {
        return true;
    }
}