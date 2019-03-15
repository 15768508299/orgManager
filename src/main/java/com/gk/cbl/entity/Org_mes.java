package com.gk.cbl.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 社团信息表
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
public class Org_mes extends Model<Org_mes> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 社团名称
     */
    @TableField("mesName")
    private String mesName;
    /**
     * 封面图
     */
    @TableField("mesPhoto")
    private String mesPhoto;
    /**
     * 社团简介
     */
    @TableField("mesIntroduction")
    private String mesIntroduction;
    /**
     * 详细介绍,以html的形式
     */
    @TableField("mesDetail")
    private String mesDetail;
    /**
     * 社团创建者
     */
    @TableField("mesCreate")
    private String mesCreate;
    /**
     * 社团创建时间
     */
    @TableField("mesCreateTime")
    private Date mesCreateTime;
    /**
     * 社团总人数
     */
    @TableField("mesNum")
    private Integer mesNum;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMesName() {
        return mesName;
    }

    public void setMesName(String mesName) {
        this.mesName = mesName;
    }

    public String getMesPhoto() {
        return mesPhoto;
    }

    public void setMesPhoto(String mesPhoto) {
        this.mesPhoto = mesPhoto;
    }

    public String getMesIntroduction() {
        return mesIntroduction;
    }

    public void setMesIntroduction(String mesIntroduction) {
        this.mesIntroduction = mesIntroduction;
    }

    public String getMesDetail() {
        return mesDetail;
    }

    public void setMesDetail(String mesDetail) {
        this.mesDetail = mesDetail;
    }

    public String getMesCreate() {
        return mesCreate;
    }

    public void setMesCreate(String mesCreate) {
        this.mesCreate = mesCreate;
    }

    public Date getMesCreateTime() {
        return mesCreateTime;
    }

    public void setMesCreateTime(Date mesCreateTime) {
        this.mesCreateTime = mesCreateTime;
    }

    public Integer getMesNum() {
        return mesNum;
    }

    public void setMesNum(Integer mesNum) {
        this.mesNum = mesNum;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Org_mes{" +
        ", id=" + id +
        ", mesName=" + mesName +
        ", mesPhoto=" + mesPhoto +
        ", mesIntroduction=" + mesIntroduction +
        ", mesDetail=" + mesDetail +
        ", mesCreate=" + mesCreate +
        ", mesCreateTime=" + mesCreateTime +
        ", mesNum=" + mesNum +
        "}";
    }
}
