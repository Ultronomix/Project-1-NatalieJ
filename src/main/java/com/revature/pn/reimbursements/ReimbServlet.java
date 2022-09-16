package com.revature.pn.reimbursements;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.pn.common.ErrorResponse;
import com.revature.pn.common.ResourceCreationResponse;
import com.revature.pn.common.exceptions.AuthenticationException;
import com.revature.pn.common.exceptions.DataSourceException;
import com.revature.pn.common.exceptions.InvalidRequestException;
import com.revature.pn.common.exceptions.ResourceNotFoundException;
import com.revature.pn.users.UserResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
public class ReimbServlet extends HttpServlet {

    private final ReimbService reimbService;

    public ReimbServlet(ReimbService reimbService) {
        this.reimbService = reimbService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ObjectMapper jsonMapper = new ObjectMapper();
        resp.setContentType("application/json");

        HttpSession reimbSession = req.getSession(false);

        if (reimbSession == null) {  //add logger
            resp.setStatus(401);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(401, "Requester not authenticated with server, log in")));
            return;
        }

        UserResponse requester = (UserResponse) reimbSession.getAttribute("loggedInUser");

        String reimb_idToSearchFor = req.getParameter("reimb_id");
        String status_idToSearchFor = req.getParameter("status_id");
        String type_idToSearchFor = req.getParameter("type_id");


        if ((!requester.getRole().equals("TRAVIS(ADMIN)") && !requester.getRole().equals("JANET(FINANCE MANAGERS)")) && !requester.getRole().equals("WINNIE(EMPLOYEES)")) {
            resp.setStatus(403); // Forbidden
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(403, "Requester not permitted to communicate with this endpoint.")));
            return;
        }


        try {


            if (reimb_idToSearchFor != null) {

                ReimbursementsResponse foundRequest = reimbService.getReimbByReimb_id(reimb_idToSearchFor);
                resp.getWriter().write(jsonMapper.writeValueAsString(foundRequest));
                //! resp.getWriter().write("\nGet reimburse request by id");
            }
            if (status_idToSearchFor != null) {

                List<ReimbursementsResponse> foundStatus_id = reimbService.getReimbByStatus_id(status_idToSearchFor);
                resp.getWriter().write(jsonMapper.writeValueAsString(foundStatus_id));
                //! resp.getWriter().write("\nGet reimburse by status");
            }
            if (type_idToSearchFor != null) {
                // TODO add log
                List<ReimbursementsResponse> foundType_id = reimbService.getReimbByType_id(type_idToSearchFor);
                resp.getWriter().write(jsonMapper.writeValueAsString(foundType_id));
                //! resp.getWriter().write("\nGet reimburse by type");

            }
        } catch (InvalidRequestException | JsonMappingException e) {

            resp.setStatus(400);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));
        } catch (ResourceNotFoundException e) {

            resp.setStatus(404);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(404, e.getMessage())));
        } catch (DataSourceException e) {

            resp.setStatus(500);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(500, e.getMessage())));
        }


        resp.getWriter().write("Reimbursement is working");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ObjectMapper jsonMapper = new ObjectMapper();
        resp.setContentType("application/json");

        HttpSession reimbSession = req.getSession(false);

        if (reimbSession == null) {
            resp.setStatus(401);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(401, "Requester not authenticated with server, log in")));
            return;
        }

        resp.getWriter().write("Reimbursements are working ");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ObjectMapper jsonMapper = new ObjectMapper();
        resp.setContentType("application/json");

        HttpSession reimbSession = req.getSession(false);

        if (reimbSession == null) {
            resp.setStatus(401);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(401, "Requester not authenticated with server, log in")));
            return;
        }
        UserResponse requester = (UserResponse) reimbSession.getAttribute("loggedInUser");

        String userIdToSearchFor = req.getParameter("user_id");
        String reimb_idToSearchFor = req.getParameter("reimb_id");

        if ((!requester.getRole().equals("TRAVIS(ADMIN)") && !requester.getRole().equals("JANET(FINANCE MANAGER)"))
                && !requester.getRole().equals("WINNIE(EMPLOYEES)")) {

            resp.setStatus(403); // Forbidden
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(403, "Requester not permitted to communicate with this endpoint.")));
            return;
        }
        ReimbursementsResponse foundReimb = reimbService.getReimbByReimb_id(userIdToSearchFor);
        resp.getWriter().write(jsonMapper.writeValueAsString(foundReimb));

        try {

            if (requester.getUserId().equals(userIdToSearchFor)) {


                ResourceCreationResponse responseBody =
                        reimbService.updateReimb(jsonMapper.readValue(req.getInputStream(), UpdateReimbursementRequest.class), reimb_idToSearchFor);
                resp.getWriter().write(jsonMapper.writeValueAsString(responseBody));

                return;
            }
            ResourceCreationResponse responseBody =
                    reimbService.updateReimb(jsonMapper.readValue(req.getInputStream(), UpdateReimbursementRequest.class), userIdToSearchFor);
        } catch (InvalidRequestException | JsonMappingException e) {

            resp.setStatus(400);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));
        } catch (AuthenticationException e) {

            resp.setStatus(409);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(409, e.getMessage())));
        } catch (DataSourceException e) {

            resp.setStatus(500);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(500, e.getMessage())));
        }


        resp.getWriter().write("Still working on reimbursement update");
    }
}