package com.revature.pn;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.pn.auth.AuthService;
import com.revature.pn.auth.AuthServlet;
import com.revature.pn.reimbursements.ReimbService;
import com.revature.pn.reimbursements.ReimbServlet;
import com.revature.pn.reimbursements.ReimbursementsDAO;
import com.revature.pn.users.UserDAO;
import com.revature.pn.users.UserService;
import com.revature.pn.users.UserServlet;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Project1App {

    private static Logger logger = LogManager.getLogger(Project1App.class);
    private static String reimbServlet;

    public static void main(String[] args) throws LifecycleException {

        logger.info("Starting pn application");

        String docBase = System.getProperty("java.io.tmpdir");
        Tomcat webServer = new Tomcat();

        // Web server base configurations
        webServer.setBaseDir(docBase);
        webServer.setPort(5000); // defaults to 8080, but we can set it to whatever port we want (as long as its open)
        webServer.getConnector(); // formality, required in order for the server to receive requests

        // App component instantiation
        UserDAO userDAO = new UserDAO();
        ReimbursementsDAO reimbursementsDAO = new ReimbursementsDAO();
        AuthService authService = new AuthService(userDAO);
        UserService userService = new UserService(userDAO);
        ReimbService reimbService = new ReimbService(reimbursementsDAO);
        ObjectMapper jsonMapper = new ObjectMapper();
        UserServlet userServlet = new UserServlet(userService, jsonMapper);
        AuthServlet authServlet = new AuthServlet(authService, jsonMapper);
        ReimbServlet reimbServlet = new ReimbServlet(reimbService, jsonMapper);

        // Web server context and servlet configurations
        final String rootContext = "/pn";
        webServer.addContext(rootContext, docBase);
        webServer.addServlet(rootContext, "UserServlet", userServlet).addMapping("/users");
        webServer.addServlet(rootContext, "AuthServlet", authServlet).addMapping("/auth");
        webServer.addServlet(rootContext, "ReimbServlet", reimbServlet).addMapping("/reimbursements");
        // Starting and awaiting web requests
        webServer.start();
        logger.info("pn web application successfully started");
        webServer.getServer().await();

    }

}