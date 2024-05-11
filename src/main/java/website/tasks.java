package website;

import java.io.Serializable;

public class tasks implements Serializable {
    
    private int taskId;
    private int sectionId;
    private String taskName;
    private String taskDescription;
    private String taskQuestion;
    private String correctAnswer;
    modules moduleId;
    
    public tasks() {}
    
    public int getTaskId() {
        return taskId;
    }
    
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    
    
    public int getSectionId() {
        return sectionId;
    }
    
    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }
    
    public String getTaskName() {
        return taskName;
    }
    
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    
    public String getTaskDescription() {
        return taskDescription;
    }
    
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
    
    public String getTaskQuestion() {
        return taskQuestion;
    }
    
    public void setTaskQuestion(String taskQuestion) {
        this.taskQuestion = taskQuestion;
    }
    
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    
    public modules getModuleId() {
        return moduleId;
    }
    
    public void setModuleId(modules moduleId) {
        this.moduleId = moduleId;
    }
}
