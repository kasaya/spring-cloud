package com.oycl.mbplus.model;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * 城市
 * </p>
 *
 * @author cango
 * @since 2019-01-15
 */
public class MCityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 城市ID
     */
    @TableId(value = "city_id", type = IdType.INPUT)
    private String cityId;
    /**
     * 城市编号
     */
    private String cityCd;
    /**
     * ,城市名称
     */
    private String cityName;
    /**
     * 省ID
     */
    private String provinceId;
    /**
     * 有效
     */
    private String validflg;
    /**
     * 备注
     */
    private String comment;
    /**
     * 高风险城市FLG
     */
    private String cityHighriskFlg;
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


    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityCd() {
        return cityCd;
    }

    public void setCityCd(String cityCd) {
        this.cityCd = cityCd;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getValidflg() {
        return validflg;
    }

    public void setValidflg(String validflg) {
        this.validflg = validflg;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCityHighriskFlg() {
        return cityHighriskFlg;
    }

    public void setCityHighriskFlg(String cityHighriskFlg) {
        this.cityHighriskFlg = cityHighriskFlg;
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
        return "MCity{" +
        "cityId=" + cityId +
        ", cityCd=" + cityCd +
        ", cityName=" + cityName +
        ", provinceId=" + provinceId +
        ", validflg=" + validflg +
        ", comment=" + comment +
        ", cityHighriskFlg=" + cityHighriskFlg +
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
