package com.gk.cbl.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 社团新闻表
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
public class Org_news extends Model<Org_news> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 新闻主题
     */
    @TableField("newTheme")
    private String newTheme;
    /**
     * 新闻封面图
     */
    @TableField("newPhoto")
    private String newPhoto;
    /**
     * 新闻简介
     */
    @TableField("newIntroduction")
    private String newIntroduction;
    /**
     * 详细介绍(HTML格式)
     */
    @TableField("newDetail")
    private String newDetail;
    /**
     * 新闻发布时间
     */
    @TableField("issueTime")
    private Date issueTime;
    /**
     * 新闻发布人(社团发布就社团id,管理员发布就管理员id)
     */
    @TableField("issueBy")
    private Integer issueBy;
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

    public String getNewTheme() {
        return newTheme;
    }

    public void setNewTheme(String newTheme) {
        this.newTheme = newTheme;
    }

    public String getNewPhoto() {
        return newPhoto;
    }

    public void setNewPhoto(String newPhoto) {
        this.newPhoto = newPhoto;
    }

    public String getNewIntroduction() {
        return newIntroduction;
    }

    public void setNewIntroduction(String newIntroduction) {
        this.newIntroduction = newIntroduction;
    }

    public String getNewDetail() {
        return newDetail;
    }

    public void setNewDetail(String newDetail) {
        this.newDetail = newDetail;
    }

    public Date getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Date issueTime) {
        this.issueTime = issueTime;
    }

    public Integer getIssueBy() {
        return issueBy;
    }

    public void setIssueBy(Integer issueBy) {
        this.issueBy = issueBy;
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
        return "Org_news{" +
        ", id=" + id +
        ", newTheme=" + newTheme +
        ", newPhoto=" + newPhoto +
        ", newIntroduction=" + newIntroduction +
        ", newDetail=" + newDetail +
        ", issueTime=" + issueTime +
        ", issueBy=" + issueBy +
        ", readNum=" + readNum +
        "}";
    }
}
