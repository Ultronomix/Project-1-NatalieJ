package com.revature.pn.reimbursements;


import com.revature.pn.common.ResourceCreationResponse;
import com.revature.pn.common.exceptions.InvalidRequestException;
import com.revature.pn.common.exceptions.ResourceNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class ReimbService {

    private static Logger logger = LogManager.getLogger(ReimbService.class);

    private final ReimbursementsDAO reimbDAO;

    public ReimbService(ReimbursementsDAO reimbDAO) {
        this.reimbDAO = reimbDAO;
    }

    public List<ReimbursementsResponse> getAllReimb() {

        return reimbDAO.getAllReimbursement().stream()
                .map(ReimbursementsResponse::new)
                .collect(Collectors.toList());

    }

    public ReimbursementsResponse getReimbById(String reimbId) {

        if (reimbId == null || reimbId.length() <= 0) {
            throw new InvalidRequestException("A non-empty id must be provided!");
        }

        try {

            return reimbDAO.findReimbById(reimbId)
                    .map(ReimbursementsResponse::new)
                    .orElseThrow(ResourceNotFoundException::new);

        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("An invalid UUID string was provided.");
        }

    }

    public ResourceCreationResponse newReimb(NewReimbursementRequest newReimb) {

        NewReimbursementRequest newReimb1 = newReimb;
        if (newReimb1 == null) {
            throw new InvalidRequestException("Provided request payload was null.");

        }

        if (newReimb1.getAmount() == 0) {
            throw new InvalidRequestException("Provided request payload was null.");

        }

        if (newReimb1.getDescription() == null) {
            throw new InvalidRequestException("Provided request payload was null.");
        }

        if (newReimb1.getAuthor_id() == null) {
            throw new InvalidRequestException("Provided request payload was null.");
        }

        if (newReimb1.getType_id() == null) {
            throw new InvalidRequestException("Provided request payload was null.");

        }

            Reimbursements reimbToPersist = newReimb1.extractEntity();
            String newReimbId = reimbDAO.save(reimbToPersist);
            return new ResourceCreationResponse(newReimbId);
        }

    public void updateReimbursements(UpdateReimbursementRequest requestPayload) {
    }

    public ResourceCreationResponse create() {
        return create();
    }

}