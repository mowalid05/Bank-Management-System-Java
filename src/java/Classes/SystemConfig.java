package Classes;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author HP
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles system configuration settings
 */
public class SystemConfig {
    private static final String CONFIG_TABLE = "System_Config";
    
    /**
     * Ensures the configuration table exists
     */
    public static void ensureConfigTableExists() {
        DB_Connection db = new DB_Connection();
        try (Connection conn = db.getConnection()) {
            // Check if table exists
            ResultSet rs = conn.getMetaData().getTables(null, null, CONFIG_TABLE, null);
            if (!rs.next()) {
                // Table doesn't exist, create it
                Statement stmt = conn.createStatement();
                String createTableSQL = "CREATE TABLE " + CONFIG_TABLE + " (" +
                                        "config_key VARCHAR(100) PRIMARY KEY, " +
                                        "config_value TEXT, " +
                                        "description TEXT, " +
                                        "updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, " +
                                        "updated_by VARCHAR(100)" +
                                        ")";
                stmt.executeUpdate(createTableSQL);
                
                // Insert default configurations
                insertDefaultConfigs(conn);
            }
        } catch (SQLException e) {
            System.err.println("Error ensuring config table: " + e.getMessage());
        } finally {
            db.closeConnection();
        }
    }
    
    /**
     * Insert default configuration values
     */
    private static void insertDefaultConfigs(Connection conn) throws SQLException {
        String insertSQL = "INSERT INTO " + CONFIG_TABLE + " (config_key, config_value, description, updated_by) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(insertSQL);
        
        // Transaction limits
        pstmt.setString(1, "max_withdrawal_limit");
        pstmt.setString(2, "10000");
        pstmt.setString(3, "Maximum amount allowed for withdrawal in a single transaction");
        pstmt.setString(4, "System");
        pstmt.executeUpdate();
        
        pstmt.setString(1, "max_transfer_limit");
        pstmt.setString(2, "50000");
        pstmt.setString(3, "Maximum amount allowed for transfer in a single transaction");
        pstmt.setString(4, "System");
        pstmt.executeUpdate();
        
        // Interest rates
        pstmt.setString(1, "savings_interest_rate");
        pstmt.setString(2, "3.5");
        pstmt.setString(3, "Interest rate for savings accounts (%)");
        pstmt.setString(4, "System");
        pstmt.executeUpdate();
        
        pstmt.setString(1, "loan_base_interest_rate");
        pstmt.setString(2, "9.5");
        pstmt.setString(3, "Base interest rate for loans (%)");
        pstmt.setString(4, "System");
        pstmt.executeUpdate();
        
        // System settings
        pstmt.setString(1, "maintenance_mode");
        pstmt.setString(2, "false");
        pstmt.setString(3, "Whether the system is in maintenance mode");
        pstmt.setString(4, "System");
        pstmt.executeUpdate();
        
        pstmt.setString(1, "session_timeout_minutes");
        pstmt.setString(2, "30");
        pstmt.setString(3, "Session timeout in minutes");
        pstmt.setString(4, "System");
        pstmt.executeUpdate();
    }
    
    /**
     * Get all configuration settings
     */
    public static Map<String, String> getAllConfigs() {
        Map<String, String> configs = new HashMap<>();
        DB_Connection db = new DB_Connection();
        
        try (Connection conn = db.getConnection()) {
            ensureConfigTableExists();
            
            String sql = "SELECT config_key, config_value FROM " + CONFIG_TABLE;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                configs.put(rs.getString("config_key"), rs.getString("config_value"));
            }
        } catch (SQLException e) {
            System.err.println("Error getting configs: " + e.getMessage());
        } finally {
            db.closeConnection();
        }
        
        return configs;
    }
    
    /**
     * Get a specific configuration value
     */
    public static String getConfig(String key, String defaultValue) {
        DB_Connection db = new DB_Connection();
        
        try (Connection conn = db.getConnection()) {
            ensureConfigTableExists();
            
            String sql = "SELECT config_value FROM " + CONFIG_TABLE + " WHERE config_key = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, key);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("config_value");
            }
        } catch (SQLException e) {
            System.err.println("Error getting config: " + e.getMessage());
        } finally {
            db.closeConnection();
        }
        
        return defaultValue;
    }
    
    /**
     * Update a configuration value
     */
    public static boolean updateConfig(String key, String value, String updatedBy) {
        DB_Connection db = new DB_Connection();
        
        try (Connection conn = db.getConnection()) {
            ensureConfigTableExists();
            
            String sql = "UPDATE " + CONFIG_TABLE + " SET config_value = ?, updated_by = ? WHERE config_key = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, value);
            pstmt.setString(2, updatedBy);
            pstmt.setString(3, key);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating config: " + e.getMessage());
            return false;
        } finally {
            db.closeConnection();
        }
    }
    
    /**
     * Get all configuration settings with descriptions
     */
    public static List<Map<String, String>> getConfigsWithDescriptions() {
        List<Map<String, String>> configsList = new ArrayList<>();
        DB_Connection db = new DB_Connection();
        
        try (Connection conn = db.getConnection()) {
            ensureConfigTableExists();
            
            String sql = "SELECT config_key, config_value, description, updated_at, updated_by FROM " + CONFIG_TABLE + " ORDER BY config_key";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Map<String, String> config = new HashMap<>();
                config.put("key", rs.getString("config_key"));
                config.put("value", rs.getString("config_value"));
                config.put("description", rs.getString("description"));
                config.put("updatedAt", rs.getTimestamp("updated_at").toString());
                config.put("updatedBy", rs.getString("updated_by"));
                configsList.add(config);
            }
        } catch (SQLException e) {
            System.err.println("Error getting configs with descriptions: " + e.getMessage());
        } finally {
            db.closeConnection();
        }
        
        return configsList;
    }
}
