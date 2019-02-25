package com.oycl.common.config;




import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import com.oycl.util.normalutil.DateUtil;


@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {

  /**
   * 插入时候的实体填充
   * @param metaObject
   */
  @Override
  public void insertFill(MetaObject metaObject) {
    String nowData = DateUtil.getCurrentDate(DateUtil.YYYYMMDD_HHMMSS);
    setFieldValByName("createDateTime", nowData, metaObject);
    setFieldValByName("lastUpdateTime", nowData, metaObject);

  }

  /**
   * 更新时候的实体填充
   * @param metaObject
   */
  @Override
  public void updateFill(MetaObject metaObject) {
    setFieldValByName("lastUpdateTime", DateUtil.getCurrentDate(DateUtil.YYYYMMDD_HHMMSS), metaObject);
  }
}
