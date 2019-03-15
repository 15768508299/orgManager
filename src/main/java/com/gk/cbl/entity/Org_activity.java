package com.gk.cbl.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
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
public class Org_activity extends Model<Org_activity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 活动主题
     */
    @TableField("actTheme")
    private String actTheme;
    /**
     * 活动简介
     */
    @TableField("actIntroduction")
    private String actIntroduction;
    /**
     * 活动明细（存储HTML文本）
     */
    @TableField("actDetail")
    private String actDetail;
    /**
     * 活动时间
     */
    @TableField("actDate")
    private Date actDate;
    /**
     * 存储时间段的字段
     */
    @TableField("actTimeInterval")
    private String actTimeInterval;
    /**
     * 活动发起者
     */
    @TableField("issueBy")
    private Integer issueBy;
    /**
     * 发布时间
     */
    @TableField("issueDate")
    private Date issueDate;
    /**
     * 阅读量
     */
    @TableField("readNum")
    private Integer readNum;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActTheme() {
        return actTheme;
    }

    public void setActTheme(String actTheme) {
        this.actTheme = actTheme;
    }

    public String getActIntroduction() {
        return actIntroduction;
    }

    public void setActIntroduction(String actIntroduction) {
        this.actIntroduction = actIntroduction;
    }

    public String getActDetail() { return actDetail; }

    public void setActDetail(String actDetail) {
        this.actDetail = actDetail;
    }

    public Date getActDate() {
        return actDate;
    }

    public void setActDate(Date actDate) {
        this.actDate = actDate;
    }

    public String getActTimeInterval() {
        return actTimeInterval;
    }

    public void setActTimeInterval(String actTimeInterval) {
        this.actTimeInterval = actTimeInterval;
    }

    public Integer getIssueBy() {
        return issueBy;
    }

    public void setIssueBy(Integer issueBy) {
        this.issueBy = issueBy;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Integer getReadNum() {
        return readNum;
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Org_activity{" +
        ", id=" + id +
        ", actTheme=" + actTheme +
        ", actIntroduction=" + actIntroduction +
        ", actDetail=" + actDetail +
        ", actDate=" + actDate +
        ", actTimeInterval=" + actTimeInterval +
        ", issueBy=" + issueBy +
        ", issueDate=" + issueDate +
        ", readNum=" + readNum +
        "}";
    }
}
