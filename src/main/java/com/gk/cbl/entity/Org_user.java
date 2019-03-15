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
public class Org_user extends Model<Org_user> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("userName")
    private String userName;
    /**
     * 密码
     */
    @TableField("userPassword")
    private String userPassword;
    @TableField("userTrueName")
    private String userTrueName;
    /**
     * 性别
     */
    @TableField("userAge")
    private Integer userAge;
    @TableField("userSex")
    private String userSex;
    /**
     * 爱好
     */
    @TableField("userFavour")
    private String userFavour;
    @TableField("userIdentifity")
    private String userIdentifity;
    /**
     * 个人介绍
     */
    @TableField("userIntrod")
    private String userIntrod;
    @TableField("userEmail")
    private String userEmail;
    /**
     * QQ微信信息
     */
    @TableField("userQQWei")
    private String userQQWei;
    @TableField("userPhone")
    private String userPhone;
    /**
     * 图片路径
     */
    @TableField("userPhoto")
    private String userPhoto;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserTrueName() {
        return userTrueName;
    }

    public void setUserTrueName(String userTrueName) {
        this.userTrueName = userTrueName;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserFavour() {
        return userFavour;
    }

    public void setUserFavour(String userFavour) {
        this.userFavour = userFavour;
    }

    public String getUserIdentifity() {
        return userIdentifity;
    }

    public void setUserIdentifity(String userIdentifity) {
        this.userIdentifity = userIdentifity;
    }

    public String getUserIntrod() {
        return userIntrod;
    }

    public void setUserIntrod(String userIntrod) {
        this.userIntrod = userIntrod;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserQQWei() {
        return userQQWei;
    }

    public void setUserQQWei(String userQQWei) {
        this.userQQWei = userQQWei;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Org_user{" +
        ", id=" + id +
        ", userName=" + userName +
        ", userPassword=" + userPassword +
        ", userTrueName=" + userTrueName +
        ", userAge=" + userAge +
        ", userSex=" + userSex +
        ", userFavour=" + userFavour +
        ", userIdentifity=" + userIdentifity +
        ", userIntrod=" + userIntrod +
        ", userEmail=" + userEmail +
        ", userQQWei=" + userQQWei +
        ", userPhone=" + userPhone +
        ", userPhoto=" + userPhoto +
        "}";
    }
}
