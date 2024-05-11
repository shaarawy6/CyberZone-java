package website;

import java.io.Serializable;
import java.sql.Date;

public class taskCompletion implements Serializable {
    
    private int id;
    private boolean isCompleted;
    private Date completionDate;
    tasks taskId;
    users userId;
    modules moduleId;
    
    public taskCompletion() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public tasks getTaskId() {
		return taskId;
	}

	public void setTaskId(tasks taskId) {
		this.taskId = taskId;
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
