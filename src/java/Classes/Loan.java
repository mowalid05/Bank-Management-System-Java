package Classes;


import DAO.DB_Connection;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
public class Loan {
    private List<Repayment> repayments;
    private int loanId;
    private int clientId;
    private int disbursementAccountId;
    private int repaymentAccountId;
    private BigDecimal amount;
    private BigDecimal interestRate;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;

    public Loan( int loanId, int clientId, int disbursementAccountId, int repaymentAccountId, BigDecimal amount, BigDecimal interestRate, String status, LocalDate startDate, LocalDate endDate) {
        repayments = new ArrayList<>();
        this.loanId = loanId;
        this.clientId = clientId;
        this.disbursementAccountId = disbursementAccountId;
        this.repaymentAccountId = repaymentAccountId;
        this.amount = amount;
        this.interestRate = interestRate;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    
    /*  ── inside Loan.java ─────────────────────────────────────────── */
public static Loan getLoanById(int loanId) throws SQLException {

    final String sql = "SELECT * FROM Loan WHERE loan_id = ?";
    DB_Connection db = new DB_Connection();

    try (Connection conn = db.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, loanId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Loan loan = new Loan(
                    rs.getInt("loan_id"),
                    rs.getInt("client_id"),
                    rs.getInt("disbursement_account_id"),
                    rs.getInt("repayment_account_id"),
                    rs.getBigDecimal("amount"),
                    rs.getBigDecimal("interest_rate"),
                    rs.getString("status"),
                    rs.getDate("start_date").toLocalDate(),
                    rs.getDate("end_date").toLocalDate()
                );
                loan.loadRepayments();     // make sure repayments list is filled
                return loan;
            }
        }
    }
    return null;
}

    private void loadRepayments() {
        String sql = "SELECT * FROM Repayment WHERE loan_id = ?";
        DB_Connection db= new DB_Connection();
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, this.loanId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                repayments.add(new Repayment(
                        rs.getInt("repayment_id"),
                        rs.getInt("loan_id"),
                        rs.getInt("account_id"),
                        rs.getBigDecimal("amount"),
                        rs.getTimestamp("payment_date").toLocalDateTime(),
                        rs.getString("status")
                ));
            }
        } catch ( SQLException e) {
            System.err.println("Error loading repayments: " + e.getMessage());
        }
        finally{
        db.closeConnection();
    }
        
    }
        public Date getEndDateAsDate() {
        return Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    
        
    // For createdDate (if exists)
    public Date getCreatedAtAsDate() {
        return Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getDisbursementAccountId() {
        return disbursementAccountId;
    }

    public void setDisbursementAccountId(int disbursementAccountId) {
        this.disbursementAccountId = disbursementAccountId;
    }

    public int getRepaymentAccountId() {
        return repaymentAccountId;
    }

    public void setRepaymentAccountId(int repaymentAccountId) {
        this.repaymentAccountId = repaymentAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Repayment> getRepayments() {
        return repayments;
    }
    
    
}

