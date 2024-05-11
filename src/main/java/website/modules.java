package website;

import java.io.Serializable;

public class modules implements Serializable {
    
    private String moduleName;
    private int moduleId;
    paths pathId;
    
    public modules() {}
    
    public paths getPathId() {
        return pathId;
    }
    
    public void setPathId(paths pathId) {
        this.pathId = pathId;
    }
    
    public String getModuleName() {
        return moduleName;
    }
    
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
    
    public int getModuleId() {
        return moduleId;
    }
    
    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }
}
