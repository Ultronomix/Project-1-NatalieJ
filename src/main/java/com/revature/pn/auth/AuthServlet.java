package com.revature.pn.auth;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.pn.common.ErrorResponse;
import com.revature.pn.common.exceptions.AuthenticationException;
import com.revature.pn.common.exceptions.DataSourceException;
import com.revature.pn.common.exceptions.InvalidRequestException;
import com.revature.pn.users.UserResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;

public class AuthServlet extends HttpServlet {
    private static Logger logger = LogManager.getLogger(AuthServlet.class);

    private final AuthService authService;
    private final ObjectMapper jsonMapper;

    public AuthServlet(AuthService authService, ObjectMapper jsonMapper) {
        this.authService = authService;
        this.jsonMapper = jsonMapper;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        logger.info("A POST request was received by /p1/auth at {}", LocalDateTime.now());
        resp.setContentType("application/json");

        try {

            Credentials credentials = jsonMapper.readValue(req.getInputStream(), Credentials.class);
            UserResponse responseBody = authService.authenticate(credentials);
            resp.setStatus(200);

            logger.info("Establishing user session for user: {}", responseBody.getUsername());

            // Establishes an HTTP session that is implicitly attached to the response as a cookie
            HttpSession userSession = req.getSession();
            userSession.setAttribute("authUser", responseBody);

            resp.getWriter().write(jsonMapper.writeValueAsString(responseBody));

            logger.info("Request successfully processed at {}", LocalDateTime.now());

        } catch (InvalidRequestException | JsonMappingException e) {
            logger.warn("Error processing request at {} error message: {}", LocalDateTime.now(), e.getMessage());
            resp.setStatus(400); // BAD REQUEST;
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));

        } catch (AuthenticationException e) {
            logger.warn("Failed login at {}, error message: {}", LocalDateTime.now(), e.getMessage());
            resp.setStatus(401); // UNAUTHORIZED; typically sent back when login fails or if a protected endpoint is hit by an unauthenticated user
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(401, e.getMessage())));

        } catch (DataSourceException e) {
            logger.error("A data source error occurred at {}, error message: {}", LocalDateTime.now(), e.getMessage());
            resp.setStatus(500); // INTERNAL SERVER ERROR; general error indicating a problem with the server
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(500, e.getMessage())));

        }


    }
    @Override // "logout" method
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
    }
}