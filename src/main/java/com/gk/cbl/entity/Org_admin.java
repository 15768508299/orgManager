package com.gk.cbl.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
public class Org_admin extends Model<Org_admin> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "aId", type = IdType.AUTO)
    private Integer aId;
    /**
     * 用户名
     */
    @TableField("aUserName")
    private String aUserName;
    /**
     * 密码
     */
    @TableField("aPassword")
    private String aPassword;


    public Integer getaId() {
        return aId;
    }

    public void setaId(Integer aId) {
        this.aId = aId;
    }

    public String getaUserName() {
        return aUserName;
    }

    public void setaUserName(String aUserName) {
        this.aUserName = aUserName;
    }

    public String getaPassword() {
        return aPassword;
    }

    public void setaPassword(String aPassword) {
        this.aPassword = aPassword;
    }

    @Override
    protected Serializable pkVal() {
        return this.aId;
    }

    @Override
    public String toString() {
        return "Org_admin{" +
        ", aId=" + aId +
        ", aUserName=" + aUserName +
        ", aPassword=" + aPassword +
        "}";
    }
}
