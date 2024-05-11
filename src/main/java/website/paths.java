package website;

import java.io.Serializable;

public class paths implements Serializable {
    
    private String pathName;
    private int pathId;
    
    public paths() {}
    
    public String getPathName() {
        return pathName;
    }
    
    public void setPathName(String pathName) {
        this.pathName = pathName;
    }
    
    public int getPathId() {
        return pathId;
    }
    
    public void setPathId(int pathId) {
        this.pathId = pathId;
    }
}
