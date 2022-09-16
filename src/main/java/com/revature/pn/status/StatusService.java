package com.revature.pn.status;


import com.revature.pn.common.exceptions.ResourceNotFoundException;
import com.revature.pn.reimbursements.Reimbursements;
import com.revature.pn.reimbursements.ReimbursementsDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StatusService {

    private static Logger logger = LogManager.getLogger(StatusService.class);

    private final ReimbursementsDAO reimbDAO;

    public StatusService(ReimbursementsDAO reimbDAO) {
        this.reimbDAO = reimbDAO;
    }



    public void updateStatusAndResolver(UpdateStatusRequest updateStatusAndResolver) {

        System.out.println(updateStatusAndResolver);

        Reimbursements statusAndResolver = reimbDAO.findReimbByStatus_id
                (updateStatusAndResolver.getReimbByReimbId()).orElseThrow(ResourceNotFoundException::new);

        if (updateStatusAndResolver.getResolverId() != null) {
            statusAndResolver.setResolverId(updateStatusAndResolver.getResolverId());
        }

        if (updateStatusAndResolver.getReimbByStatus_id() != null) {
            statusAndResolver.setReimbByStatus(updateStatusAndResolver.getReimbByStatus());
        }

        reimbDAO.UpdateReimbursementRequest(statusAndResolver);
    }
}