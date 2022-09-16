package com.revature.pn.reimbursements;

import com.revature.pn.auth.AuthService;
import com.revature.pn.auth.AuthServlet;
import com.revature.pn.users.UserDAO;
import com.revature.pn.users.UserService;
import com.revature.pn.users.UserServlet;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class ReimbursementAPI {
    private static Logger logger = LogManager.getLogger(ReimbursementAPI.class);
    private static String AuthServlet;
    private static String ReimbServlet;
    private static com.revature.pn.users.UserService UserService;
    private static com.revature.pn.auth.AuthService AuthService;

    public static void main(String[]args, String UserServlet) throws LifecycleException{
        String docBase = System.getProperty("java.io.tmpdir");
        Tomcat webServer = new Tomcat();

        webServer.setBaseDir(docBase);
        webServer.setPort(5000);
        webServer.getConnector();

        UserDAO userDao = new UserDAO();
        ReimbursementsDAO reimbursementDao = new ReimbursementsDAO();
        AuthService authService = new AuthService(userDao);
        UserService userService = new UserService(userDao);
        ReimbService reimbursementService = new ReimbService(reimbursementDao);
        UserServlet userServlet = new UserServlet(UserService);
        AuthServlet authServlet = new AuthServlet(AuthService);
        ReimbServlet reimbursementServlet = new ReimbServlet(reimbursementService);

        final String rootContext = "/ers";
        webServer.addContext(rootContext,docBase);
        webServer.addServlet(rootContext,"UserServlet",userServlet).addMapping("/users");
        webServer.addServlet(rootContext,"AuthServlet", authServlet).addMapping("/auth");
        webServer.addServlet(rootContext,"ReimbursementServlet",reimbursementServlet).addMapping("/reimbursement");

        webServer.start();
        webServer.getServer().await();
    }
}
