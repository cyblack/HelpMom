package org.androidtown.helpmom;

import java.io.Serializable;

public class Task implements Serializable {

    // 6-02: changed
    String taskName,progress,comment,created, point, changedName;

    public Task(String taskName,String progress,String comment,String point, String changedName,String created){
        this.taskName = taskName;

        this.progress = progress;
        this.comment = comment;
        this.point = point;
        this.changedName = changedName;
        this.created = created;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public void setChangedName(String changedName) {
        this.changedName = changedName;
    }

    public String getTaskName() {
        return taskName;
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
