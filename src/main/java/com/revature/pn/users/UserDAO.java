package com.revature.pn.users;

import com.revature.pn.common.datasource.ConnectionFactory;
import com.revature.pn.common.exceptions.DataSourceException;
import com.revature.pn.common.role.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// DAO = Data Access Object
public class UserDAO {

    private static Logger logger = LogManager.getLogger(UserDAO.class);

    private final String baseSelect = "SELECT eu.user_id, eu.username, eu.email, eu.password, eu.given_name, eu.surname, eu.is_active, eu.role_id, eur.role " +
            "FROM ers_users eu " +
            "JOIN ers_user_roles eur " +
            "ON eu.role_id = eur.role_id ";

    public List<User> getAllUsers() {

        logger.info("Attempting to connect to the database at {}", LocalDateTime.now());

        List<User> allUsers = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            // JDBC Statement objects are vulnerable to SQL injection
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(baseSelect);

            allUsers = mapResultSet(rs);
            logger.info("Successful database connection at {}", LocalDateTime.now());

        } catch (SQLException e) {
            System.err.println("Something went wrong when communicating with the database");
            e.printStackTrace();
            logger.fatal("Unsuccessful database connection at {}, error message: {}", LocalDateTime.now(), e.getMessage());
        }

        return allUsers;

    }

    public Optional<User> findUserById(UUID userId) {

        logger.info("Attempting to search by user id at {}", LocalDateTime.now());

        String sql = baseSelect + "WHERE eu.user_id = ?";

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            // JDBC Statement objects are vulnerable to SQL injection
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, UUID.fromString(String.valueOf(userId)));
            ResultSet rs = pstmt.executeQuery();

            logger.info("User found by user id at {}", LocalDateTime.now());

            return mapResultSet(rs).stream().findFirst();


        } catch (SQLException e) {
            logger.warn("Unable to process user id search at {}, error message: {}", LocalDateTime.now(), e.getMessage());
            e.printStackTrace();
            throw new DataSourceException(e);
        }

    }

    public Optional<User> findUserByUsername(String username) {

        logger.info("Attempting to search by username at {}", LocalDateTime.now());

        String sql = baseSelect + "WHERE eu.username = ?";

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            logger.info("User found by username at {}", LocalDateTime.now());
            // JDBC Statement objects are vulnerable to SQL injection
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();

        } catch (SQLException e) {
            logger.warn("Unable to process username search search at {}, error message: {}", LocalDateTime.now(), e.getMessage());
            throw new DataSourceException(e);
        }

    }

    public boolean isUsernameTaken(String username) {
        return findUserByUsername(username).isPresent();
    }

    public Optional<User> findUserByEmail(String email) {

        logger.info("Attempting to search by email at {}", LocalDateTime.now());

        String sql = baseSelect + "WHERE eu.email = ?";

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            logger.info("User found by email at {}", LocalDateTime.now());
            // JDBC Statement objects are vulnerable to SQL injection
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();

        } catch (SQLException e) {
            logger.warn("Unable to process email search at {}, error message: {}", LocalDateTime.now(), e.getMessage());
            e.printStackTrace();
            throw new DataSourceException(e);
        }

    }

    public boolean isEmailTaken(String email) {
        return findUserByEmail(email).isPresent();
    }

    public Optional<User> findUserByUsernameAndPassword(String username, String password) {

        logger.info("Attempting to search by username and password at {}", LocalDateTime.now());
        String sql = baseSelect + "WHERE eu.username = ? AND eu.password = ?";

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            logger.info("User found by username and password at {}", LocalDateTime.now());
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();

        } catch (SQLException e) {
            logger.warn("Unable to process username and password search at {}, error message: {}", LocalDateTime.now(), e.getMessage());
            e.printStackTrace();
            throw new DataSourceException(e);
        }

    }

    public String save(User user) {

        logger.info("Attempting to persist new user data at {}", LocalDateTime.now());
        String sql = "INSERT INTO ers_users (username, email, password, given_name, surname, is_active, role_id) " +
                "VALUES (?, ?, ?, ?, ?, TRUE, 'SELECT * FROM ERS_USERS;')";

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            logger.info("Successfully persisted new used with id: {} at {}", user.getUserId(), LocalDateTime.now());
            PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"user_id"});
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getGivenName());
            pstmt.setString(5, user.getSurname());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            user.setUserId(rs.getString("user_id"));

        } catch (SQLException e) {
            logger.warn("Unable to persist data at {}, error message: {}", LocalDateTime.now(), e.getMessage());
            e.printStackTrace();
        }

        return user.getUserId();

    }

    private List<User> mapResultSet(ResultSet rs) throws SQLException {
        logger.info("Attempting to map the result set of user info at {}", LocalDateTime.now());
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User user = new User();
            user.setUserId(rs.getString("user_id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setGivenName(rs.getString("given_name"));
            user.setSurname(rs.getString("surname"));
            user.setIsActive(rs.getString("is_active"));
            user.setRole(String.valueOf(new Role(rs.getString("role_id"), rs.getString("role"))));
            users.add(user);
        }

        return users;
    }

    public void updateUser(User user) {
        logger.info("Attempting to update user info at {}", LocalDateTime.now());
        String sql = "UPDATE ers_users " +
                "SET username = ?, password = ?, email = ?, given_name = ?, surname = ? " +
                "WHERE user_id = ?";
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getGivenName());
            pstmt.setString(5, user.getSurname());
            pstmt.setObject(6, UUID.fromString(user.getUserId()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.warn("Unable to persist updated user info at {}, error message: {}", LocalDateTime.now(), e.getMessage());
            e.printStackTrace();
        }

    }

    public String save() {
        return save();
    }
}