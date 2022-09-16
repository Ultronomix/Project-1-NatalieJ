package com.revature.pn.reimbursements;

import java.util.Objects;
import java.time.LocalDateTime;

public class Reimbursements {
    private String reimb_id;
    private double amount;

    private String submitted;

    private String resolved;

    private String description;
    private LocalDateTime author_id;
    private LocalDateTime resolved_id;
    private String status_id;
    private String type_id;
    private Object resolverId;

    public String getReimb_id() {
        return reimb_id;
    }

    public void setReimb_id(String reimb_id) {
        this.reimb_id = reimb_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    public String getResolved() {
        return resolved;
    }

    public void setResolved(String resolved) {
        this.resolved = resolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor_id() {
        return String.valueOf(author_id);
    }

    public void setAuthor_id(String author_id) {
        this.author_id = LocalDateTime.parse(author_id);
    }

    public String getResolved_id() {
        return String.valueOf(resolved_id);
    }

    public void setResolved_id(String resolved_id) {
        this.resolved_id = LocalDateTime.parse(resolved_id);
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reimbursements that = (Reimbursements) o;
        return amount == that.amount && Objects.equals(reimb_id, that.reimb_id) && Objects.equals(submitted, that.submitted) && Objects.equals(resolved, that.resolved)
                && Objects.equals(description, that.description) && Objects.equals(author_id, that.author_id) && Objects.equals(resolved_id, that.resolved_id)
                && Objects.equals(status_id, that.status_id) && Objects.equals(type_id, that.type_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reimb_id, amount, submitted, resolved, description, author_id, resolved_id, status_id, type_id);
    }

    @Override
    public String toString() {
        return "Reimbursements{" +
                "reimb_id='" + reimb_id + '\'' +
                ", amount=" + amount +
                ", submitted='" + submitted + '\'' +
                ", resolved='" + resolved + '\'' +
                ", description='" + description + '\'' +
                ", author_id='" + author_id + '\'' +
                ", resolver_id='" + resolved_id + '\'' +
                ", status_id='" + status_id + '\'' +
                ", type_id='" + type_id + '\'' +
                '}';
    }

    public void setReimbByStatus(boolean reimbByStatus) {
    }

    public void setResolverId(Object resolverId) {
        this.resolverId = resolverId;
    }

    public Object getResolverId() {
        return resolverId;
    }
}