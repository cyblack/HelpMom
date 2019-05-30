package org.androidtown.helpmom;

import java.io.Serializable;

public class Task implements Serializable {

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
