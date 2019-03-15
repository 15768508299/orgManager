package com.gk.cbl.Bo;

import com.gk.cbl.entity.Org_user;

public class UserBo extends Org_user {

    private Integer mesid;
    private Integer jobNum;
    private String jobNumStr;

    public Integer getMesid() {
        return mesid;
    }

    public void setMesid(Integer mesid) {
        this.mesid = mesid;
    }

    public Integer getJobNum() {
        return jobNum;
    }

    public void setJobNum(Integer jobNum) {
        this.jobNum = jobNum;
    }

    public String getJobNumStr() {
        return jobNumStr;
    }

    public void setJobNumStr(String jobNumStr) {
        this.jobNumStr = jobNumStr;
    }

    public UserBo(Org_user user){
        this.setId(user.getId());
        this.setUserName(user.getUserName());
        this.setUserPassword(user.getUserPassword());
        this.setUserAge(user.getUserAge());
        this.setUserEmail(user.getUserEmail());
        this.setUserFavour(user.getUserFavour());
        this.setUserIdentifity(user.getUserIdentifity());
        this.setUserIntrod(user.getUserIntrod());
        this.setUserPhone(user.getUserPhone());
        this.setUserPhoto(user.getUserPhoto());
        this.setUserQQWei(user.getUserQQWei());
        this.setUserSex(user.getUserSex());
        this.setUserTrueName(user.getUserTrueName());
    }
}
