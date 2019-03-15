package com.gk.cbl.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 社团与用户关系表
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
public class Org_mes_user extends Model<Org_mes_user> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 社团ID
     */
    @TableField("mesId")
    private Integer mesId;
    /**
     * 用户Id
     */
    @TableField("userId")
    private Integer userId;
    /**
     * 社团的职位  1社长，2负责人，3普通成员
     */
    @TableField("jobNum")
    private Integer jobNum;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMesId() {
        return mesId;
    }

    public void setMesId(Integer mesId) {
        this.mesId = mesId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getJobNum() {
        return jobNum;
    }

    public void setJobNum(Integer jobNum) {
        this.jobNum = jobNum;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Org_mes_user{" +
        ", id=" + id +
        ", mesId=" + mesId +
        ", userId=" + userId +
        ", jobNum=" + jobNum +
        "}";
    }
}
