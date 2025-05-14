
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
public class Transaction {

    private int id;
    private int accountId;
    private String type;
    private BigDecimal amount;
    private LocalDateTime txnTs;
    private Integer otherAccount;

    public Transaction(int tId, int accountId, String type,
                       BigDecimal amount, LocalDateTime txnTs,
                       Integer otherAccount) {
        this.id = tId;
        this.txnTs = txnTs;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.otherAccount = otherAccount;
    }

    public int getId() {
        return id;
    }

    public void settId(int tId) {
        this.id = tId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTxnTs() {
        return txnTs;
    }

    public void setTxnTs(LocalDateTime txnTs) {
        this.txnTs = txnTs;
    }

    public Integer getOtherAccount() {
        return otherAccount;
    }

    public void setOtherAccount(Integer otherAccount) {
        this.otherAccount = otherAccount;
    }



}
