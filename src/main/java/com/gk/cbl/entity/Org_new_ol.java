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
public class Org_new_ol extends Model<Org_new_ol> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 描述
     */
    private String description;
    /**
     * 链接至
     */
    private String target;
    /**
     * 图片
     */
    private String photo;
    @TableField("olLevel")
    private Integer olLevel;
    /**
     * 外键
     */
    private Integer newid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getOlLevel() {
        return olLevel;
    }

    public void setOlLevel(Integer olLevel) {
        this.olLevel = olLevel;
    }

    public Integer getNewid() {
        return newid;
    }

    public void setNewid(Integer newid) {
        this.newid = newid;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Org_new_ol{" +
        ", id=" + id +
        ", description=" + description +
        ", target=" + target +
        ", photo=" + photo +
        ", olLevel=" + olLevel +
        ", newid=" + newid +
        "}";
    }
}
