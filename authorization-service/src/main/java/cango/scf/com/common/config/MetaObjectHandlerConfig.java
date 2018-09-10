package cango.scf.com.common.config;


import cango.scf.com.common.definitions.Constants;
import cango.scf.com.common.util.DateUtil;
import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
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
    setFieldValByName("createUser", Constants.DEFAULT_USER_ID, metaObject);
    setFieldValByName("updateUser", Constants.DEFAULT_USER_ID, metaObject);
  }

  /**
   * 更新时候的实体填充
   * @param metaObject
   */
  @Override
  public void updateFill(MetaObject metaObject) {
    setFieldValByName("lastUpdateTime", DateUtil.getCurrentDate(DateUtil.YYYYMMDD_HHMMSS), metaObject);
    setFieldValByName("updateUser", Constants.DEFAULT_USER_ID, metaObject);
  }
}
