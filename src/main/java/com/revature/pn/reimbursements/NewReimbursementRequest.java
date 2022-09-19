package com.revature.pn.reimbursements;

import com.revature.pn.common.Request;

public class NewReimbursementRequest implements Request<Reimbursements> {


    private String reimb_id;
    private double amount;
    private String submitted;
    private String description;
    private String author_id;
    private String status_id;
    private String type_id;
    private Object resolver_id;

    public String NewReimbursementRequest(NewReimbursementRequest newReimb1) {
        String NewReimbursementRequest = "";
        return NewReimbursementRequest;
    }


    public String getReimb_id() {
        return reimb_id;

    }

    public void setReimb_id(String reimb_id) {
        this.reimb_id = reimb_id;
    }

    public double getAmount(){
        return amount;
    }

    public void setAmount(double amount){
        this.amount = amount;

    }

    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id){
        this.status_id = status_id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    @Override
    public String toString() {
        return "NewReimbursementRequest{" +
                "reimb_id='" + reimb_id + '\'' +
                ", amount=" + amount +
                ", submitted='" + submitted + '\'' +
                ", description='" + description + '\'' +
                ", author_id='" + author_id + '\'' +
                ", status_id='" + status_id + '\'' +
                ", type_id='" + type_id + '\'' +
                '}';
    }



    @Override
    public Reimbursements extractEntity() {
        Reimbursements extractEntity = new Reimbursements();
        extractEntity.setReimb_id(this.reimb_id);
        extractEntity.setAmount(this.amount);
        extractEntity.setSubmitted(this.submitted);
        extractEntity.setDescription(this.description);
        extractEntity.setAuthor_id(this.author_id);
        extractEntity.setStatus_id(this.status_id);
        extractEntity.setType_id(this.type_id);
        return null;
    }


    public void setAuthor_Id(String userId) {
    }
}