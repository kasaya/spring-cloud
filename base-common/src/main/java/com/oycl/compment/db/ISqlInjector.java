package com.oycl.compment.db;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.session.Configuration;

public interface ISqlInjector {
    /**
     * 根据mapperClass注入SQL
     *
     * @param builderAssistant
     * @param mapperClass
     */
    void inject(MapperBuilderAssistant builderAssistant, Class<?> mapperClass);

    /**
     * 检查SQL是否注入(已经注入过不再注入)
     *
     * @param builderAssistant
     * @param mapperClass
     */
    void inspectInject(MapperBuilderAssistant builderAssistant, Class<?> mapperClass);

    /**
     * 注入SqlRunner相关
     *
     * @param configuration
     */
    void injectSqlRunner(Configuration configuration);
}
