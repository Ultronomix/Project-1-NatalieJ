package com.revature.pn.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.pn.users.*;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceTest {

    private static Logger logger = LogManager.getLogger(UserService.class);
    private static com.revature.pn.users.UserService UserServiceTest;
    private static String UserDAO;

    public static void main(String[] args) throws LifecycleException {

        logger.info("Starting the test for UserServiceTest");

        String docBase = System.getProperty("java.io.tmpdir");
        Tomcat webServer = new Tomcat();


        webServer.setBaseDir(docBase);
        webServer.setPort(5000); //
        webServer.getConnector();


        UserDAO userDAO = new UserDAO();
        NewUserRequest newUserRequest = new NewUserRequest();
        Role role = new Role(UserDAO);
        UserService userService = new UserService(userDAO);
        UserResponse userResponse = new UserResponse(UserDAO);
        ObjectMapper jsonMapper = new ObjectMapper();
        UserServlet userServlet = new UserServlet(userService, jsonMapper);
        User user = new User(userResponse, jsonMapper);
        UserServlet userServlet1 = new UserServlet(userService);


        final String rootContext = "/test";
        webServer.addContext(rootContext, docBase);
        webServer.addServlet(rootContext, "UserServlet", userServlet).addMapping("/users");
        webServer.addServlet(rootContext, "AuthServlet", userServlet).addMapping("/auth");
        webServer.addServlet(rootContext, "ReimbServlet", userServlet).addMapping("/reimbursements");
        webServer.addServlet(rootContext, "UserService", userServlet).addMapping("/users");

        webServer.start();

        logger.info("Welcome to the UseService test!");

        webServer.getServer().await();

    }
}
