package website;

import java.io.Serializable;

public class downloads implements Serializable {
    
    private String downloadName;
    private int downloadId;
    
    public downloads() {}
    
    public String getDownloadName() {
        return downloadName;
    }
    
    public void setDownloadName(String downloadName) {
        this.downloadName = downloadName;
    }
    
    public int getDownloadId() {
        return downloadId;
    }
    
    public void setDownloadId(int downloadId) {
        this.downloadId = downloadId;
    }
}
