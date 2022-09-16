package com.revature.pn.status;

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

public class StatusServlet extends HttpServlet {

    private static Logger logger = LogManager.getLogger(StatusServlet.class);

    private final StatusService statusService;
    private final ObjectMapper jsonMapper;

    public StatusServlet(StatusService statusService, ObjectMapper jsonMapper) {
        this.statusService = statusService;
        this.jsonMapper = jsonMapper;
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");

        HttpSession reimbSession = req.getSession(false);

        if(reimbSession == null) {
            resp.setStatus(401);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(401, "Requester not authenticated with server, log in")));
            return;
        }

        UserResponse requester = (UserResponse) reimbSession.getAttribute("authUser");

        if (!isFinanceMan(requester)) {
            resp.setStatus(403);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(403, "Requester not permitted to communicate with this endpoint.")));
            return;
        }

        try {

            UpdateStatusRequest requestPayload = jsonMapper.readValue(req.getInputStream(), UpdateStatusRequest.class);
            statusService.updateStatusAndResolver(requestPayload);
            resp.setStatus(204);

        } catch (InvalidRequestException | JsonMappingException e) {
            resp.setStatus(400);// * bad request
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));
        } catch (AuthenticationException e) {
            resp.setStatus(409); // * conflict; indicate that provided resource could not be saved
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(409, e.getMessage())));
        } catch (DataSourceException e) {
            e.printStackTrace();
            resp.setStatus(500); // * internal error
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(500, e.getMessage())));
        }

    }

    private boolean isFinanceMan(UserResponse requester) {
        return true;
    }

}
