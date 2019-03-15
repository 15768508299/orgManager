package com.gk.cbl.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 消息传递表
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
public class Org_send_rece extends Model<Org_send_rece> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 发送者id
     */
    @TableField("sendId")
    private Integer sendId;
    /**
     * 接受者id
     */
    @TableField("receId")
    private Integer receId;
    /**
     * 消息类型（0：社团申请通知；1：社团通知；2：个人消息）
     */
    @TableField("mesType")
    private Integer mesType;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息时间
     */
    @TableField("mesTime")
    private Date mesTime;
    /**
     * 未读|已读--> 0：代表未读;1：代表已读
     */
    private Integer isread;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSendId() {
        return sendId;
    }

    public void setSendId(Integer sendId) {
        this.sendId = sendId;
    }

    public Integer getReceId() {
        return receId;
    }

    public void setReceId(Integer receId) {
        this.receId = receId;
    }

    public Integer getMesType() {
        return mesType;
    }

    public void setMesType(Integer mesType) {
        this.mesType = mesType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getMesTime() {
        return mesTime;
    }

    public void setMesTime(Date mesTime) {
        this.mesTime = mesTime;
    }

    public Integer getIsread() {
        return isread;
    }

    public void setIsread(Integer isread) {
        this.isread = isread;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Org_send_rece{" +
        ", id=" + id +
        ", sendId=" + sendId +
        ", receId=" + receId +
        ", mesType=" + mesType +
        ", content=" + content +
        ", mesTime=" + mesTime +
        ", isread=" + isread +
        "}";
    }
}
