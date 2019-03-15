package com.gk.cbl.Bo;

import com.gk.cbl.entity.Org_send_rece;

public class sendReceBo extends Org_send_rece {
    private String sendName;
    private String mesTypeTheme;


    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getMesTypeTheme() {
        return mesTypeTheme;
    }

    public void setMesTypeTheme(String mesTypeTheme) {
        this.mesTypeTheme = mesTypeTheme;
    }

    public sendReceBo(Org_send_rece orgSendRece){
        this.setId(orgSendRece.getId());
        this.setSendId(orgSendRece.getSendId());
        this.setReceId(orgSendRece.getReceId());
        this.setMesTime(orgSendRece.getMesTime());
        this.setIsread(orgSendRece.getIsread());
        this.setContent(orgSendRece.getContent());
        this.setMesType(orgSendRece.getMesType());
    }
}
