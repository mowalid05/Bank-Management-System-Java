package Classes;


import DAO.DB_Connection;
import Classes.Account;
import java.math.BigDecimal;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author LENOVO
 */
// Client class
public class Client extends Person {

    private int clientId;
    private int nationalId;
    private String government;
    private String city;
    private String street;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Account> accounts;
    private List<Loan> loans;

    public Client(int clientId, int nationalId, String government, String city, String street, String email, LocalDateTime createdAt, LocalDateTime updatedAt, String firstName, String lastName, LocalDate bdate, Gender gender) {
        super(firstName, lastName, bdate, gender);
        this.clientId = clientId;
        this.nationalId = nationalId;
        this.government = government;
        this.city = city;
        this.street = street;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        accounts = new ArrayList<>();
        loans = new ArrayList<>();
        loadAccounts(clientId);
        loadLoans();
    }

    private void loadAccounts(int clientId) {
        String sql = "SELECT * FROM Account WHERE client_id = ?";
        DB_Connection db = new DB_Connection();
        try (Connection conn = db.getConnection(); PreparedStatement ptmt = conn.prepareStatement(sql)) {

            ptmt.setInt(1, clientId);
            ResultSet rs = ptmt.executeQuery();

            System.out.println("first account ");
            while (rs.next()) {
                accounts.add(new Account(
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
                        rs.getTimestamp("updated_at").toLocalDateTime()
                ));
            }
            for (Account a : accounts) {
                System.out.println("the  acc name is" + a.getName());
            }
        } catch (SQLException e) {
            System.err.println("Error loading accounts: " + e.getMessage());
        } finally {
            db.closeConnection();
        }
    }

    private void loadLoans() {
        String sql = "SELECT * FROM Loan WHERE client_id = ?";
        DB_Connection db = new DB_Connection();
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, this.clientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                loans.add(new Loan(
                        rs.getInt("loan_id"),
                        this.clientId,
                        rs.getInt("disbursement_account_id"),
                        rs.getInt("repayment_account_id"),
                        rs.getBigDecimal("amount"),
                        rs.getBigDecimal("interest_rate"),
                        rs.getString("status"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error loading loans: " + e.getMessage());
        }
    }

    public static Client getClientById(int clientId) throws SQLException {
        String sql = "SELECT * FROM Client WHERE client_id = ?";

        // TODO: see why connection is not working TRYno stattic
        DB_Connection db = new DB_Connection();
    

        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Client(
                        rs.getInt("client_id"),
                        rs.getInt("national_id"),
                        rs.getString("government"),
                        rs.getString("city"),
                        rs.getString("street"),
                        rs.getString("email"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getString("fname"),
                        rs.getString("lname"),
                        rs.getDate("bdate").toLocalDate(),
                        Gender.valueOf(rs.getString("gender"))
                );
            }
        } catch (SQLException e) {
            System.err.println("Client fetch error: " + e.getMessage());
        }
        return null;
    }
 public static Map<String, String> getClientBynationalId(int nationalId) throws SQLException {
    String sql = "SELECT fname, lname, city FROM Client WHERE national_id = ?";
    Map<String, String> clientData = new HashMap<>();
    DB_Connection db= new DB_Connection();
    try (Connection conn = db.getConnection(); 
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, nationalId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            clientData.put("firstName", rs.getString("fname"));
            clientData.put("lastName", rs.getString("lname"));
            clientData.put("city", rs.getString("city"));
        }
    }
    return clientData;
}
//    method to get the total balance for client summary

    public BigDecimal getOverallBalance() {
        BigDecimal sum = BigDecimal.ZERO;
        for (Account a : accounts) {
            sum = sum.add(a.getBalance());        
        }
        return sum;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getNationalId() {
        return nationalId;
    }

    public void setNationalId(int nationalId) {
        this.nationalId = nationalId;
    }

    public String getGovernment() {
        return government;
    }

    public void setGovernment(String government) {
        this.government = government;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBdate() {
        return bdate;
    }

    public void setBdate(LocalDate bdate) {
        this.bdate = bdate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public List<Loan> getLoans() {
        return loans;
    }

}
