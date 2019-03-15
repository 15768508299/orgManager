package com.gk.cbl.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 申请社团表
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
public class Org_apply_mes extends Model<Org_apply_mes> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 申请人id
     */
    @TableField("userId")
    private Integer userId;
    /**
     * 社团id
     */
    @TableField("mesId")
    private Integer mesId;
    /**
     * 申请理由
     */
    @TableField("applyReason")
    private String applyReason;
    /**
     * 申请日期
     */
    @TableField("applyTime")
    private Date applyTime;
    /**
     * 是否同意(1：同意；0：拒绝)
     */
    @TableField("isAllow")
    private Integer isAllow;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMesId() {
        return mesId;
    }

    public void setMesId(Integer mesId) {
        this.mesId = mesId;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getIsAllow() {
        return isAllow;
    }

    public void setIsAllow(Integer isAllow) {
        this.isAllow = isAllow;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Org_apply_mes{" +
        ", id=" + id +
        ", userId=" + userId +
        ", mesId=" + mesId +
        ", applyReason=" + applyReason +
        ", applyTime=" + applyTime +
        ", isAllow=" + isAllow +
        "}";
    }
}
