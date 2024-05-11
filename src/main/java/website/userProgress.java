package website;

import java.io.Serializable;

public class userProgress implements Serializable {
    
    private int id;
    private float progress;
    users userId;
    modules moduleId;
    
    public userProgress() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getProgress() {
		return progress;
	}

	public void setProgress(float progress) {
		this.progress = progress;
	}

	public users getUserId() {
		return userId;
	}

	public void setUserId(users userId) {
		this.userId = userId;
	}

	public modules getModuleId() {
		return moduleId;
	}

	public void setModuleId(modules moduleId) {
		this.moduleId = moduleId;
	}
}
