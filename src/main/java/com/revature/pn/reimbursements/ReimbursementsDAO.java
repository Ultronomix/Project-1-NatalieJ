package com.revature.pn.reimbursements;

import com.revature.pn.common.datasource.ConnectionFactory;
import com.revature.pn.common.exceptions.DataSourceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ReimbursementsDAO {

    private static Logger logger = LogManager.getLogger(ReimbursementsDAO.class);

    private final String baseSelect = "SELECT er.reimb_id, er.amount, er.submitted, er.resolved, er.description, er.payment_id, er.author_id, er.resolver_id, er.status_id, er.type_id, ers.status, ert.type, eu.user_id " +
            "FROM ers_reimbursements er " +
            "JOIN ers_reimbursement_statuses ers " +
            "ON er.status_id = ers.status_id " +
            "JOIN ers_reimbursement_types ert " +
            "ON er.type_id = ert.type_id " +
            "JOIN ers_users eu " +
            "ON er.author_id = eu.user_id " +
            "LEFT JOIN ers_users " +
            "ON er.resolver_id = eu.user_id ";

    public List<Reimbursements> getAllReimbursement() {

        logger.info("Attempting to connect to the database at {}", LocalDateTime.now());

        List<Reimbursements> allReimbs = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(baseSelect);

            allReimbs = mapResultSet(rs);
            logger.info("Successful database connection at {}", LocalDateTime.now());

        } catch (SQLException e) {
            System.err.println("Something went wrong when connection to database.");
            e.printStackTrace();
            logger.fatal("Unsuccessful database connection at {}, error message: {}", LocalDateTime.now(), e.getMessage());
        }

        return allReimbs;
    }

    public Optional<Reimbursements> findReimbById(String reimbId) {

        logger.info("Attempting to search by reimbursement id at {}", LocalDateTime.now());

        String sql = baseSelect + "WHERE er.reimb_id = ?";

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, UUID.fromString(reimbId));
            ResultSet rs = pstmt.executeQuery();


            Optional<Reimbursements> _reimb = mapResultSet(rs).stream().findFirst();

            if (_reimb.isPresent()) {
                logger.info("Reimbursement found by reimbursement id at {}", LocalDateTime.now());
            }

            return _reimb;

        } catch (SQLException e) {
            logger.warn("Unable to process reimbursement id search at {}, error message: {}", LocalDateTime.now(), e.getMessage());
            e.printStackTrace();
            throw new DataSourceException(e);
        }

    }

    public Optional<Reimbursements> findReimbByStatus(String status) {

        logger.info("Attempting to search by reimbursement status at {}", LocalDateTime.now());

        String sql = baseSelect + "WHERE er.status = ?";

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            logger.info("Reimbursement found by status at {}", LocalDateTime.now());
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, status);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();


        } catch (SQLException e) {
            logger.warn("Unable to process reimbursement status search at {}, error message: {}", LocalDateTime.now(), e.getMessage());
            e.printStackTrace();
            throw new DataSourceException(e);
        }
    }

    public String save(Reimbursements newReimbursement) {

        logger.info("Attempting to persist new reimbursement at {}", LocalDateTime.now());
        String sql = "INSERT INTO ers_reimbursements (amount, description, author_id, status_id, type_id) " +
                "VALUES (?, ?, ?, 'PENDING', ?)";

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            logger.info("New reimbursement successfully persisted at {}", LocalDateTime.now());
            PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"reimb_id"});
            pstmt.setFloat(1, (float) newReimbursement.getAmount());
            pstmt.setString(2, newReimbursement.getDescription());
            pstmt.setObject(3, UUID.fromString(newReimbursement.getAuthor_id()));
            pstmt.setString(4, newReimbursement.getType_id());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            newReimbursement.setReimb_id(rs.getString("reimb_id"));

        } catch (SQLException e) {
            logger.warn("Unable to persist data at {}, error message: {}", LocalDateTime.now(), e.getMessage());
            e.printStackTrace();
        }

        return newReimbursement.getReimb_id();

    }

    private List<Reimbursements> mapResultSet(ResultSet rs) throws SQLException {

        List<Reimbursements> reimbursements = new ArrayList<>();
        while (rs.next()) {
            logger.info("Attempting to map the result set of reimbursement info at {}", LocalDateTime.now());
            Reimbursements reimbursement = new Reimbursements();
            reimbursement.setReimb_id(rs.getString("reimb_id"));
            reimbursement.setAmount(rs.getFloat("amount"));
            reimbursement.setSubmitted(String.valueOf(rs.getTimestamp("submitted").toLocalDateTime()));
            Timestamp resolvedTs = rs.getTimestamp("resolved");
            reimbursement.setResolved(resolvedTs == null ? null : String.valueOf(resolvedTs.toLocalDateTime()));
            reimbursement.setDescription(rs.getString("description"));
            reimbursement.setAuthor_id(rs.getString("author_id"));
            reimbursement.setResolverId(rs.getString("resolver_id"));
            reimbursement.setStatusId(rs.getString("status_id"));
            reimbursement.setType_id(rs.getString("type_id"));

            reimbursements.add(reimbursement);
        }

        return reimbursements;
    }

    public void updateReimb(Reimbursements reimb) {
        logger.info("Attempting to update reimbursement info at {}", LocalDateTime.now());
        System.out.println(reimb);
        String sql = "UPDATE ers_reimbursements " +
                "SET amount = ?, description = ?, type_id = ? " +
                "WHERE reimb_id = ?";
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setFloat(1, (float) reimb.getAmount());
            pstmt.setString(2, reimb.getDescription());
            pstmt.setString(3, reimb.getType_id());
            pstmt.setObject(4, UUID.fromString(reimb.getReimb_id()));
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated != 1) {
                System.out.println("Sorry we didnt actually update anything.");
            }
        } catch (SQLException e) {
            logger.warn("Unable to persist updated reimbursement at {}", LocalDateTime.now());
            e.printStackTrace();
        }
    }

    public void updateStatus(Reimbursements newStatus) {

        logger.info("Attempting to update reimbursement status at {}", LocalDateTime.now());
        String sql = "UPDATE ers_reimbursements " +
                "SET resolved = now(), resolver_id = ?, status_id = ?  " +
                "WHERE reimb_id = ?";
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, UUID.fromString(newStatus.getAuthor_id()));
            pstmt.setString(2, newStatus.getStatus_id());
            pstmt.setObject(3, UUID.fromString(newStatus.getReimb_id()));
            pstmt.executeUpdate();

        } catch (SQLException e) {
            logger.warn("Unable to persist updated reimbursement status at {}, error message: {}", LocalDateTime.now(), e.getMessage());
            e.printStackTrace();
        }
    }

    public String findReimbursments(String reimbByReimbId) {
        return reimbByReimbId;
    }

    public String getReimbByReimbId(String reimbByReimbId) {
        return reimbByReimbId;
    }
}