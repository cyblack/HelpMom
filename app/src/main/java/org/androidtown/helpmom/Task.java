package org.androidtown.helpmom;

public class Task {

    String taskName,taskId,progress,comment,created,point,changedName;

    public Task(String taskName,String taskId,String progress,String comment,String point, String changedName){
        this.taskName = taskName;
        this.taskId = taskId;
        this.progress = progress;
        this.comment = comment;
        this.point = point;
        this.changedName = changedName;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getProgress() {
        return progress;
    }

    public String getComment() {
        return comment;
    }

    public String getCreated() {
        return created;
    }

    public String getPoint() {
        return point;
    }

    public String getChangedName() {
        return changedName;
    }
}
