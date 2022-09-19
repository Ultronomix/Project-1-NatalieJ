package com.revature.pn.reimbursements;

import com.revature.pn.common.Request;

public class UpdateReimbursementRequest implements Request<Reimbursements> {

    private String status_id;
    private double amount;
    private String description;
    private String type_id;
    private Object resolverId;
    private String reimbByReimbId;
    private Object reimbByStatus_id;

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_(String type_id) {
        this.type_id = type_id;
    }

    @Override
    public String toString() {
        return "UpdateReimbursementRequest{" +
                "status='" + status_id + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", type_='" + type_id + '\'' +
                '}';
    }

    @Override
    public Reimbursements extractEntity() {
           Reimbursements extractedEntity = new Reimbursements();
           extractedEntity.setStatus_id(this.status_id);
           extractedEntity.setAmount((float) this.amount);
           extractedEntity.setDescription(this.description);
           extractedEntity.setType_id(this.type_id);

        return extractedEntity;
    }


    public boolean getReimbByStatus() {
        return true;
    }

    public Object getResolverId() {
        Object resolverId = null;
        return resolverId;
    }

    public void setResolverId(Object resolverId) {
        this.resolverId = resolverId;
    }

    public String getReimbByReimbId() {
        String reimbByReimbId = null;
        return reimbByReimbId;
    }

    public void setReimbByReimbId(String reimbByReimbId) {
        this.reimbByReimbId = reimbByReimbId;
    }

    public Object getReimbByStatus_id() {
        return reimbByStatus_id;
    }

    public void setReimbByStatus_id(Object reimbByStatus_id) {
        this.reimbByStatus_id = reimbByStatus_id;
    }

    public String getAuthor_id() {
        String Author_id = null;
        return Author_id;
    }


    public Object reimbByReimbId() {
        return getReimbByReimbId();
    }
}