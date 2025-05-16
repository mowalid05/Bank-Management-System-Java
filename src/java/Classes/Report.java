package Classes;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a report in the banking system
 */
public class Report {
    private int reportId;
    private String name;
    private String type;
    private LocalDateTime generatedAt;
    private String generatedBy;
    private Map<String, Object> parameters;
    private String content;
    
    public Report() {
        this.parameters = new HashMap<>();
        this.generatedAt = LocalDateTime.now();
    }
    
    public Report(String name, String type, String generatedBy) {
        this();
        this.name = name;
        this.type = type;
        this.generatedBy = generatedBy;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public String getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(String generatedBy) {
        this.generatedBy = generatedBy;
    }
    
    public void addParameter(String key, Object value) {
        this.parameters.put(key, value);
    }
    
    public Object getParameter(String key) {
        return this.parameters.get(key);
    }
    
    public Map<String, Object> getParameters() {
        return this.parameters;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
