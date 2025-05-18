package Classes;


import DAO.DB_Connection;
import Classes.Client;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LENOVO
 */

// Employee class
public class Employee extends Person {
    private int ssn;
    private String email;
    private String password;
    private BigDecimal salary;
    private String position;
    private int branchId;
    private Integer supervisorSsn;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Employee(String firstName, String lastName, LocalDate bdate, Gender gender,
                    int ssn, String email, String password, BigDecimal salary,
                    String position, int branchId, Integer supervisorSsn) {
        super(firstName, lastName, bdate, gender);
        this.ssn = ssn;
        this.email = email;
        this.password = password;
        this.salary = salary;
        this.position = position;
        this.branchId = branchId;
        this.supervisorSsn = supervisorSsn;
    }
    //methods that the Employee will use
    public Client searchClient(int clientId) throws SQLException {
        // Security issue: No permission checks
        return Client.getClientById(clientId);
    }

    public List<Client> searchClientsByCity(String city) {
        String sql = "SELECT * FROM Client WHERE city = ?";
        List<Client> clients = new ArrayList<>();
        DB_Connection db= new DB_Connection();
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, city);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                clients.add(new Client(
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
                ));
            }
        } catch (SQLException e) {
            System.err.println("City search error: " + e.getMessage());
        }
        return clients;
    }
 static  public ArrayList<PendingApprovals> getMyrequests(int ssn)
    {
        DB_Connection db = new DB_Connection();
         ArrayList<PendingApprovals> requests = new ArrayList<>();
                try (Connection conn = db.getConnection()) {
            String sql = "SELECT * FROM Pending_Approvals WHERE requested_by = ?";
                   
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, ssn);
                ResultSet rs = pstmt.executeQuery();
                
                while (rs.next()) {

                    requests.add(new PendingApprovals(
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

            
            
            
        } catch (SQLException e) {
            // Handle error
        }
                return requests;
    }
    

    public int getSsn() {
        return ssn;
    }

    public void setSsn(int ssn) {
        this.ssn = ssn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public Integer getSupervisorSsn() {
        return supervisorSsn;
    }

    public void setSupervisorSsn(Integer supervisorSsn) {
        this.supervisorSsn = supervisorSsn;
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
    
}