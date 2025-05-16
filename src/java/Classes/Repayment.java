package Classes;



import java.math.BigDecimal;
import java.time.LocalDateTime;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LENOVO
 */
public class Repayment {
    private int repaymentId;
    private int loanId;
    private int accountId;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String status;

    public Repayment(int repaymentId,int loanId, int accountId, BigDecimal amount, LocalDateTime paymentDate, String status) {
        this.repaymentId = repaymentId;
        this.loanId = loanId;
        this.accountId = accountId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.status = status;

    }

    
    
    
    
    
    
    
    
    
    public int getRepaymentId() {
        return repaymentId;
    }

    public void setRepaymentId(int repaymentId) {
        this.repaymentId = repaymentId;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
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

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
    
    
    
}
