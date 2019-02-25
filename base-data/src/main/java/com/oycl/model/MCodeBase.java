package com.oycl.model;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * CODE定义
 * </p>
 *
 * @author cango
 * @since 2019-01-15
 */
public class MCodeBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分类编号
     */
    @TableId(value = "class_cd", type = IdType.INPUT)
    private String classCd;
    /**
     * 分类名称
     */
    private String className;
    /**
     * 选项编号
     */
    private String itemCd;
    /**
     * 选项值
     */
    private String itemValue;
    /**
     * 表示内容
     */
    private String itemContent;
    /**
     * 备用信息
     */
    private String itemInfo;
    /**
     * 预备数值1
     */
    private String prepNum1;
    /**
     * 预备数值2
     */
    private String prepNum2;
    /**
     * 预备字符1
     */
    private String prepChar1;
    /**
     * 预备字符2
     */
    private String prepChar2;
    /**
     * 新增日期时间
     */
    @TableField(fill = FieldFill.INSERT)
    private String createDateTime;
    /**
     * 新增人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createUser;
    /**
     * 更新日期时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String lastUpdateTime;
    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT)
    private String updateUser;


    public String getClassCd() {
        return classCd;
    }

    public void setClassCd(String classCd) {
        this.classCd = classCd;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getItemCd() {
        return itemCd;
    }

    public void setItemCd(String itemCd) {
        this.itemCd = itemCd;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }

    public String getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(String itemInfo) {
        this.itemInfo = itemInfo;
    }

    public String getPrepNum1() {
        return prepNum1;
    }

    public void setPrepNum1(String prepNum1) {
        this.prepNum1 = prepNum1;
    }

    public String getPrepNum2() {
        return prepNum2;
    }

    public void setPrepNum2(String prepNum2) {
        this.prepNum2 = prepNum2;
    }

    public String getPrepChar1() {
        return prepChar1;
    }

    public void setPrepChar1(String prepChar1) {
        this.prepChar1 = prepChar1;
    }

    public String getPrepChar2() {
        return prepChar2;
    }

    public void setPrepChar2(String prepChar2) {
        this.prepChar2 = prepChar2;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    public String toString() {
        return "MCode{" +
        "classCd=" + classCd +
        ", className=" + className +
        ", itemCd=" + itemCd +
        ", itemValue=" + itemValue +
        ", itemContent=" + itemContent +
        ", itemInfo=" + itemInfo +
        ", prepNum1=" + prepNum1 +
        ", prepNum2=" + prepNum2 +
        ", prepChar1=" + prepChar1 +
        ", prepChar2=" + prepChar2 +
        ", createDateTime=" + createDateTime +
        ", createUser=" + createUser +
        ", lastUpdateTime=" + lastUpdateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
