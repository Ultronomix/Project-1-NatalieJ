package com.revature.pn.reimbursements;

import com.revature.pn.common.Request;

public class UpdateReimbursementRequest implements Request<Reimbursements> {

    private String status_id;
    private double amount;
    private String description;
    private String type_id;

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
        extractedEntity.setAmount(this.amount);
        extractedEntity.setDescription(this.description);
        extractedEntity.setType_id(this.type_id);

        return extractedEntity;
    }


}