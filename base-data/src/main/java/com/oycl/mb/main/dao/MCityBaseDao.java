package com.oycl.mb.main.dao;


import com.oycl.model.MCityBase;

public interface MCityBaseDao {

    int deleteByPrimaryKey(String cityId);

    int insert(MCityBase record);

    int insertSelective(MCityBase record);

	MCityBase selectByPrimaryKey(String cityId);

    int updateByPrimaryKeySelective(MCityBase record);

    int updateByPrimaryKey(MCityBase record);
}