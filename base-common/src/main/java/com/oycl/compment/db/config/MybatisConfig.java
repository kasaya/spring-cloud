package com.oycl.compment.db.config;

import com.oycl.compment.db.MybatisConfiguration;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import static com.oycl.compment.db.config.DbPropertiesConstants.PACKAGE;
import static com.oycl.compment.db.config.DbPropertiesConstants.MAPPER_LOCATION;

@MapperScan(basePackages = PACKAGE, sqlSessionFactoryRef = "sqlSessionFactory", sqlSessionTemplateRef = "sqlSessionTemplate")
@EnableTransactionManagement
public class MybatisConfig {

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory getSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception{

        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment.Builder  builder = new Environment.Builder("main");
        builder.dataSource(dataSource);
        builder.transactionFactory(transactionFactory);

        org.apache.ibatis.session.Configuration configuration = new MybatisConfiguration(builder.build());
        configuration.setMapUnderscoreToCamelCase(true);
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION);
        configuration.setMapUnderscoreToCamelCase(true);
        sessionFactory.setConfiguration(configuration);
        sessionFactory.setMapperLocations(resources);
        sessionFactory.setDataSource(dataSource);
         //TODO: 翻页
        //sessionFactory.setPlugins(new Interceptor[]{new PageInterceptor()});
        return sessionFactory.getObject();

    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate erpSqlSessionTemplate(
            @Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
