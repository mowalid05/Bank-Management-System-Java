package Classes;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LENOVO
 */
public class PendingApprovals implements Serializable{
    private int approvalId;
    private int requestedBy;
    private int accountId;
    private BigDecimal amount;
    private String transactionType;
    private LocalDateTime requestedAt;
    private String status;
    public PendingApprovals(int approvalId, int requestedBy, int accountId, BigDecimal amount, String transactionType,String status, LocalDateTime requestedAt) {
        this.approvalId = approvalId;
        this.requestedBy = requestedBy;
        this.accountId = accountId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.requestedAt = requestedAt;
        this.status=status;
    }

    public PendingApprovals() {}

    public int getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(int approvalId) {
        this.approvalId = approvalId;
    }

    public int getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(int requestedBy) {
        this.requestedBy = requestedBy;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
