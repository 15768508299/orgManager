package com.gk.cbl.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 社团留言表
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
public class Org_word extends Model<Org_word> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    private Integer userid;
    /**
     * 内容
     */
    private String content;
    /**
     * 社团id
     */
    private Integer mesid;
    /**
     * 时间
     */
    @TableField("wordTime")
    private Date wordTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getMesid() {
        return mesid;
    }

    public void setMesid(Integer mesid) {
        this.mesid = mesid;
    }

    public Date getWordTime() {
        return wordTime;
    }

    public void setWordTime(Date wordTime) {
        this.wordTime = wordTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Org_word{" +
        ", id=" + id +
        ", userid=" + userid +
        ", content=" + content +
        ", mesid=" + mesid +
        ", wordTime=" + wordTime +
        "}";
    }
}
