package com.oycl.mainservice.common.config;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import freemarker.template.utility.DateUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;


@Component
public class MetaObjectHandlerConfig extends MetaObjectHandler {

  /**
   * 插入时候的实体填充
   * @param metaObject
   */
  @Override
  public void insertFill(MetaObject metaObject) {
    String nowData = DateUtil.getCurrentDate(DateUtil.YYYYMMDD_HHMMSS);
    setFieldValByName("createDateTime", nowData, metaObject);
    setFieldValByName("lastUpdateTime", nowData, metaObject);
    setFieldValByName("createUser", Constants.FIVE_USER_ID, metaObject);
    setFieldValByName("updateUser", Constants.FIVE_USER_ID, metaObject);
  }

  /**
   * 更新时候的实体填充
   * @param metaObject
   */
  @Override
  public void updateFill(MetaObject metaObject) {
    setFieldValByName("lastUpdateTime", DateUtil.getCurrentDate(DateUtil.YYYYMMDD_HHMMSS), metaObject);
    setFieldValByName("updateUser", Constants.FIVE_USER_ID, metaObject);
  }
}
