package DAO;

import Classes.PendingApprovals;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerDAO {
    public static ArrayList<PendingApprovals> getPendingApprovals(int managerSSN) throws SQLException {
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