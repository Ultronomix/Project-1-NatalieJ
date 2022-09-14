package com.revature.pn;

import com.revature.pn.auth.AuthService;
import com.revature.pn.auth.AuthServlet;
import com.revature.pn.users.UserDAO;
import com.revature.pn.users.UserService;
import com.revature.pn.users.UserServlet;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class Project1App {

    public static void main(String[] args) throws LifecycleException {
        String docBase = System.getProperty("java.io.tmpdir");
        Tomcat webServer = new Tomcat();

        // Web server base configurations
        webServer.setBaseDir(docBase);
        webServer.setPort(5000); // defaults to 8080, but we can set it to whatever port we want (as long as its open)
        webServer.getConnector(); // formality, required in order for the server to receive requests

        // App component instantiation
        UserDAO userDAO = new UserDAO();
        AuthService authService = new AuthService(userDAO);
        UserService userService = new UserService(userDAO);
        UserServlet userServlet = new UserServlet(userService);
        AuthServlet authServlet = new AuthServlet(authService);

        // Web server context and servlet configurations
        final String rootContext = "/pn ";
        webServer.addContext(rootContext, docBase);
        webServer.addServlet(rootContext, "UserServlet ", userServlet).addMapping("/users");
        webServer.addServlet(rootContext, "AuthServlet ", authServlet).addMapping("/auth");

        // Starting and awaiting web requests
        webServer.start();
        webServer.getServer().await();

    }

}