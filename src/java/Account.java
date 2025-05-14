


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LENOVO
 */
public class Account implements Serializable{
    private int accountId;
    private String name;
    private String status;
    private String type;
    private String currency;
    private BigDecimal balance;
    private int clientId;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastTransactionDate;
    private List<Transaction> transactions;

    public Account() {}

    public Account(String name, String status, String type, BigDecimal balance,
                   int clientId, String phoneNumber, int accountId, String currency,LocalDateTime createdAt,LocalDateTime updatedAt ,LocalDateTime last_transction) {
        this.name = name;
        this.status = status;
        this.type = type;
        this.balance = balance;
        this.clientId = clientId;
        this.phoneNumber = phoneNumber;
        this.accountId = accountId;
        this.lastTransactionDate= last_transction;
        this.currency = currency;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.transactions = new ArrayList<Transaction>();

        loadTransactions();
    }
public static Account getAccountById(int id) throws SQLException {

    final String sql = "SELECT * FROM Account WHERE account_id = ?";
    DB_Connection db = new DB_Connection();

    try (Connection conn = db.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                /* reuse your existing constructor */
                return new Account(
                    rs.getString("name"),
                    rs.getString("status"),
                    rs.getString("type"),
                    rs.getBigDecimal("balance"),
                    rs.getInt("client_id"),
                    rs.getString("phone_number"),
                    rs.getInt("account_id"),
                    rs.getString("currency"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime(),
                    rs.getTimestamp("last_transaction_date").toLocalDateTime()
                );
            }
        }
    } finally {
        db.closeConnection();
    }
    return null;   // not found
}

    private void loadTransactions() {
        String sql = "SELECT * FROM Transactions WHERE account_id = ?";
        DB_Connection db= new DB_Connection();
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, this.accountId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                transactions.add(new Transaction(
                        rs.getInt("t_id"),
                        rs.getInt("account_id"),
                        rs.getString("type"),
                        rs.getBigDecimal("amount"),
                        rs.getTimestamp("txn_ts").toLocalDateTime(),
                        rs.getInt("other_account")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error loading transactions: " + e.getMessage());
        }finally{
        db.closeConnection();
    }
        
    }

    public Date getUpdatedAtAsDate() {
        return Date.from(updatedAt.atZone(ZoneId.systemDefault()).toInstant());
    }
    
    // For lastTransactionDate field
    public Date getLastTransactionDateAsDate() {
        return Date.from(lastTransactionDate.atZone(ZoneId.systemDefault()).toInstant());
    }
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getLast_transction() {
        return lastTransactionDate;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

}

