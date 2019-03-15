package com.gk.cbl.Bo;

import com.gk.cbl.entity.Org_activity;

public class ActivityBo extends Org_activity {
    private String actDateStr;
    private String startTime;
    private String endTime;

    public String getActDateStr() {
        return actDateStr;
    }

    public void setActDateStr(String actDateStr) {
        this.actDateStr = actDateStr;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
