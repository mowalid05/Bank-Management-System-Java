
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LENOVO
 */
public class Manager extends Employee {
    
    public Manager(String firstName, String lastName, LocalDate bdate, Gender gender, int ssn, String email, String password, BigDecimal salary, String position, int branchId, Integer supervisorSsn) {
        super(firstName, lastName, bdate, gender, ssn, email, password, salary, position, branchId, supervisorSsn);
    }
    public static ArrayList<PendingApprovals> get_approval_requests(int managerSSN) throws SQLException {
        ArrayList<PendingApprovals> approvals = new ArrayList<>();
        String sql = "SELECT pa.* FROM Pending_Approvals pa "
                   + "JOIN Employee e ON pa.requested_by = e.ssn "
                   + "WHERE e.supervisor = ? AND pa.status = 'PENDING'";
        
        try (Connection conn = new DB_Connection().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, managerSSN);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                approvals.add(new PendingApprovals(
                    rs.getInt("approval_id"),
                    rs.getInt("requested_by"),
                    rs.getInt("account_id"),
                    rs.getBigDecimal("amount"),
                    rs.getString("transaction_type"),
                    rs.getString("status"),
                        rs.getTimestamp("requested_at").toLocalDateTime()
                ));
            }
        }
        return approvals;
    }
    
}
