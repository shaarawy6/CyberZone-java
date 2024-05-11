package website;

import java.util.*;
import java.io.Serializable;

public class enrollment implements Serializable {
	
    private int enrollmentId;
    private Date enrollmentDate;
    users userId;
    modules moduleId;
    
	public enrollment() {}

	public int getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(int enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public Date getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(Date enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
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