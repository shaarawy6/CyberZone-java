package website;

import java.io.Serializable;

public class applications implements Serializable {
    
    private String applicationName;
    private int applicationId;
    
    public applications() {}
    
    public String getApplicationName() {
        return applicationName;
    }
    
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
    
    public int getApplicationId() {
        return applicationId;
    }
    
    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }
}
