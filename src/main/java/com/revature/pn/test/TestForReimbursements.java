package com.revature.pn.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.pn.auth.AuthService;
import com.revature.pn.auth.AuthServlet;
import com.revature.pn.reimbursements.ReimbService;
import com.revature.pn.reimbursements.ReimbServlet;
import com.revature.pn.reimbursements.Reimbursements;
import com.revature.pn.reimbursements.ReimbursementsDAO;
import com.revature.pn.users.UserDAO;
import com.revature.pn.users.UserService;
import com.revature.pn.users.UserServlet;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestForReimbursements {

    private static Logger logger = LogManager.getLogger(Reimbursements.class);

    public static void main(String[] args) throws LifecycleException {

        logger.info("Starting the test for Reimbursements");

        String docBase = System.getProperty("java.io.tmpdir");
        Tomcat webServer = new Tomcat();


        webServer.setBaseDir(docBase);
        webServer.setPort(5000); //
        webServer.getConnector();


        UserDAO userDAO = new UserDAO();
        ReimbursementsDAO reimbursementsDAO = new ReimbursementsDAO();
        AuthService authService = new AuthService(userDAO);
        UserService userService = new UserService(userDAO);
        ReimbService reimbService = new ReimbService(reimbursementsDAO);
        ObjectMapper jsonMapper = new ObjectMapper();
        UserServlet userServlet = new UserServlet(userService, jsonMapper);
        AuthServlet authServlet = new AuthServlet(authService, jsonMapper);
        ReimbServlet reimbServlet = new ReimbServlet(reimbService);


        final String rootContext = "/shinobi";
        webServer.addContext(rootContext, docBase);
        webServer.addServlet(rootContext, "UserServlet", userServlet).addMapping("/users");
        webServer.addServlet(rootContext, "AuthServlet", authServlet).addMapping("/auth");
        webServer.addServlet(rootContext, "ReimbServlet", reimbServlet).addMapping("/reimb");


        webServer.start();

        logger.info("It is impressive if you've managed to get this far!");

        webServer.getServer().await();

    }
}