/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LENOVO
 */
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class TransactionHandler {
    private static final BigDecimal APPROVAL_THRESHOLD = new BigDecimal("500000");
    
    public static boolean requiresApproval(BigDecimal amount) {
        return amount.compareTo(APPROVAL_THRESHOLD) > 0;
    }
    
    public static void processDeposit(Connection conn, int accountId, BigDecimal amount) 
        throws SQLException {
        
        String updateSQL = "UPDATE Account SET balance = balance + ?, updated_at = NOW() "
                         + "WHERE account_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setBigDecimal(1, amount);
            pstmt.setInt(2, accountId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
    System.err.println("procces deposte failed: " + e.getMessage());
    throw e;
}

    }
    
    public static void processWithdrawal(Connection conn, int accountId, BigDecimal amount) 
        throws SQLException {
        
        String updateSQL = "UPDATE Account SET balance = balance - ?, updated_at = NOW() "
                         + "WHERE account_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setBigDecimal(1, amount);
            pstmt.setInt(2, accountId);
            pstmt.executeUpdate();
        }
    }
    
    public static void recordTransaction(Connection conn, int accountId, 
                                       String type, BigDecimal amount) 
        throws SQLException {
        
        String txnSQL = "INSERT INTO Transactions (account_id, type, amount, txn_ts) "
                      + "VALUES (?, ?, ?, NOW())";
        try (PreparedStatement pstmt = conn.prepareStatement(txnSQL)) {
            pstmt.setInt(1, accountId);
            pstmt.setString(2, type);
            pstmt.setBigDecimal(3, amount);
            pstmt.executeUpdate();
        }
    }
        public static void insertApprovalRequest(Connection conn, int employeeSSN,
                                           int accountId, BigDecimal amount,
                                           String transactionType) throws SQLException {
        
        String sql = "INSERT INTO Pending_Approvals "
                   + "(requested_by, account_id, amount, target_account, transaction_type) "
                   + "VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, employeeSSN);
            pstmt.setInt(2, accountId);
            pstmt.setBigDecimal(3, amount);
            pstmt.setInt(4, accountId); // Same account for deposit/withdrawal
            pstmt.setString(5, transactionType);
            pstmt.executeUpdate();
        } catch (SQLException e) {
    System.err.println("record Transaction failed: " + e.getMessage());
    throw e;
}

    }
  public static ArrayList<PendingApprovals> getPendingRequests(Connection conn, int employeeSSN) 
    throws SQLException {
    
    ArrayList<PendingApprovals> requests = new ArrayList<>();
    String sql = "SELECT * FROM Pending_Approvals WHERE requested_by = ? AND status = 'PENDING'"; // Add status filter
    
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, employeeSSN);
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            requests.add(new PendingApprovals(
                rs.getInt("approval_id"), // Match exact DB column name
                rs.getInt("requested_by"),
                rs.getInt("account_id"),
                rs.getBigDecimal("amount"),
                rs.getString("transaction_type"),
                rs.getString("status"),
                rs.getTimestamp("requested_at").toLocalDateTime()
            ));
        }
    }
    return requests;
}
}