package com.oycl.orm.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oycl.orm.model.TAuthBase;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cango
 * @since 2018-09-06
 */
public interface TUserInfoDao{

    void selectUser(@Param("loginId") String loginId, @Param("passWord") String passWord);

}
