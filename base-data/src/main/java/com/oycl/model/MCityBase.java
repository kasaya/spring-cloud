package com.oycl.model;

import com.oycl.common.base.BaseModel;

public class MCityBase extends BaseModel {
    /** 城市ID */
    private String cityId;

    /** 城市编号 */
    private String cityCd;

    /** ,城市名称 */
    private String cityName;

    /** 省ID */
    private String provinceId;

    /** 有效 */
    private String validflg;

    /** 备注 */
    private String comment;

    /** 高风险城市FLG */
    private String cityHighriskFlg;

    /** 预备数值1 */
    private String prepNum1;

    /** 预备数值2 */
    private String prepNum2;

    /** 预备字符1 */
    private String prepChar1;

    /** 预备字符2 */
    private String prepChar2;

    /** 新增人 */
    private String createUser;

    /** 更新人 */
    private String updateUser;
    
    //查询需要字段
    /**省名称*/
    private String provinceName;

    public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	/**
     * return city_id 城市ID
     */
    public String getCityId() {
        return cityId;
    }

    /**
     * param cityId 城市ID
     */
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    /**
     * return city_cd 城市编号
     */
    public String getCityCd() {
        return cityCd;
    }

    /**
     * param cityCd 城市编号
     */
    public void setCityCd(String cityCd) {
        this.cityCd = cityCd;
    }

    /**
     * return city_name ,城市名称
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * param cityName ,城市名称
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * return province_id 省ID
     */
    public String getProvinceId() {
        return provinceId;
    }

    /**
     * param provinceId 省ID
     */
    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    /**
     * return validflg 有效
     */
    public String getValidflg() {
        return validflg;
    }

    /**
     * param validflg 有效
     */
    public void setValidflg(String validflg) {
        this.validflg = validflg;
    }

    /**
     * return comment 备注
     */
    public String getComment() {
        return comment;
    }

    /**
     * param comment 备注
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * return city_highrisk_flg 高风险城市FLG
     */
    public String getCityHighriskFlg() {
        return cityHighriskFlg;
    }

    /**
     * param cityHighriskFlg 高风险城市FLG
     */
    public void setCityHighriskFlg(String cityHighriskFlg) {
        this.cityHighriskFlg = cityHighriskFlg;
    }

    /**
     * return prep_num1 预备数值1
     */
    public String getPrepNum1() {
        return prepNum1;
    }

    /**
     * param prepNum1 预备数值1
     */
    public void setPrepNum1(String prepNum1) {
        this.prepNum1 = prepNum1;
    }

    /**
     * return prep_num2 预备数值2
     */
    public String getPrepNum2() {
        return prepNum2;
    }

    /**
     * param prepNum2 预备数值2
     */
    public void setPrepNum2(String prepNum2) {
        this.prepNum2 = prepNum2;
    }

    /**
     * return prep_char1 预备字符1
     */
    public String getPrepChar1() {
        return prepChar1;
    }

    /**
     * param prepChar1 预备字符1
     */
    public void setPrepChar1(String prepChar1) {
        this.prepChar1 = prepChar1;
    }

    /**
     * return prep_char2 预备字符2
     */
    public String getPrepChar2() {
        return prepChar2;
    }

    /**
     * param prepChar2 预备字符2
     */
    public void setPrepChar2(String prepChar2) {
        this.prepChar2 = prepChar2;
    }

   

    /**
     * return create_user 新增人
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * param createUser 新增人
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }


    /**
     * return update_user 更新人
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * param updateUser 更新人
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}