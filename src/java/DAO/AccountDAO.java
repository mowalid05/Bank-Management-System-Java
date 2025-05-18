package DAO;

import Classes.Account;
import Classes.Transaction;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
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


    public static List<Transaction> getAccountTransactions(int accountId) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM Transactions WHERE account_id = ?";
        DB_Connection db=new DB_Connection();
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
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
        }
        return transactions;
    }
}