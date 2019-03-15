package com.gk.cbl.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 轮播图表
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
public class Org_index_ol extends Model<Org_index_ol> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 保存目标id,用于编辑辅助使用
     */
    @TableField("choseTarget")
    private Integer choseTarget;
    /**
     * 类型(0,1)
     */
    private Integer type;
    /**
     * 跳转url
     */
    private String url;
    /**
     * 描述
     */
    private String descrption;
    /**
     * 图片路径
     */
    @TableField("photoPath")
    private String photoPath;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChoseTarget() {
        return choseTarget;
    }

    public void setChoseTarget(Integer choseTarget) {
        this.choseTarget = choseTarget;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Org_index_ol{" +
        ", id=" + id +
        ", choseTarget=" + choseTarget +
        ", type=" + type +
        ", url=" + url +
        ", descrption=" + descrption +
        ", photoPath=" + photoPath +
        "}";
    }
}
