package com.oycl.compment.db;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseMapperDriver extends XMLLanguageDriver {


    //private final Pattern inPattern = Pattern.compile("(?<=(\\$\\{))([^\\$\\{\\}]+)");


    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        Class<?> mapperClass = MybatisMapperRegistry.getCurrentMapper();

        //取得实现的 泛型类型
        Class<?>[] generics = MybatisReflectUtil.getMapperGenerics(mapperClass);
        // 实体类类型
        Class<?> modelClass = generics[0];
        //主键数据类型
        Class<?> idClass = generics[1];

        ResultMap resultMap = getResultMap(configuration.getResultMaps(), modelClass);
        script = setTable(script, mapperClass, modelClass, idClass, resultMap);
        script = setId(script, mapperClass, modelClass, idClass, resultMap);
        script = setValues(script, mapperClass, modelClass, idClass, resultMap);
        script = setSets(script, mapperClass, modelClass, idClass, resultMap);

        return super.createSqlSource(configuration, script, parameterType);
    }


    private ResultMap getResultMap(Collection<ResultMap> resultMaps, Class<?> modelClass) {
        for (ResultMap resultMap : resultMaps){
            if (modelClass == resultMap.getType() && !resultMap.getId().contains("-")){
                return resultMap;
            }
        }

        return null;
    }

    /**
     * 设置表名
     * 把 ResultMap 的 id 值作为表名，
     * 若 ResultMap 不存在，则把驼峰命名法的 Model 类名转以下划线命名作为表名
     *
     * @param script
     * @param modelClass
     * @param resultMap
     * @return
     */
    private String setTable(String script, Class<?> mapperClass, Class<?> modelClass, Class<?> idClass, ResultMap resultMap) {
        if (script.contains("${table}")) {
            String tableName = null;
            if (resultMap != null){
                String [] names = resultMap.getId().split("\\.");
                tableName = toUnderline(names[names.length-1]);
            }
            if (tableName == null){
                tableName = toUnderline(modelClass.getSimpleName());
            }

            script = script.replace("${table}", tableName);
        }
        return script;
    }

    private String setId(String script, Class<?> mapperClass, Class<?> modelClass, Class<?> idClass, ResultMap resultMap) {
        ResultMapping resultMapping = null;
        if (script.contains("${id}")) {
            String idName = null;
            resultMapping = getIdResultMapping(resultMap, null);
            if (resultMapping != null){
                idName = resultMapping.getColumn();
            }

            if (idName == null){
                idName = "id";
            }

            script = script.replace("${id}", idName);
        }

        if (script.contains("${sets}")){
            if(resultMapping != null){
                String idName = null;
                idName = resultMapping.getProperty();
                script = script.replace("#{id}", "#{"+idName+"}");
            }
        }
        return script;
    }

    private String setValues(String script, Class<?> mapperClass, Class<?> modelClass, Class<?> idClass, ResultMap resultMap) {
        if (script.contains("${values}")) {
            StringBuilder buf = new StringBuilder();

            Field[] fields = MybatisReflectUtil.getModelField(modelClass);

            buf.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
            for (Field field : fields) {
                ResultMapping resultMapping = getResultMapping(resultMap, field);
                buf.append(String.format("<if test=\"%s\">", getEmptyTesting(field)));
                buf.append(String.format("%s,", getColumnName(resultMapping, field)));
                buf.append("</if>");
            }
            buf.append("</trim>");

            buf.append("<trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\">");
            for (Field field : fields) {
                ResultMapping resultMapping = getResultMapping(resultMap, field);
                buf.append(String.format("<if test=\"%s\">", getEmptyTesting(field)));
                buf.append(String.format("%s,", getColumnValue(resultMapping, field)));
                buf.append("</if>");
            }
            buf.append("</trim>");

            script = script.replace("${values}", buf.toString());
        }
        return script;
    }

    private String setSets(String script, Class<?> mapperClass, Class<?> modelClass, Class<?> idClass, ResultMap resultMap) {

        boolean isSelective = false;
        boolean isNotnull = false;
        if (script.contains("selective")){
            isSelective = true;
        }else if (script.contains("notnull")){
            isNotnull = true;
        }
        if (script.contains("${sets}")) {
            StringBuilder buf = new StringBuilder();

            Field[] fields = MybatisReflectUtil.getModelField(modelClass);

            buf.append("<set>");
            for (Field field : fields) {
                if (isIdField(resultMap, field)){
                    continue;
                }
                if(isSelective || isNotnull){
                    buf.append(String.format("<if test=\"%s\">", getEmptyTesting(field, isNotnull)));
                }
                ResultMapping resultMapping = getResultMapping(resultMap, field);
                buf.append(String.format("%s = %s,", getColumnName(resultMapping, field), getColumnValue(resultMapping, field)));
                if(isSelective || isNotnull){
                    buf.append("</if>");
                }
            }
            buf.append("</set>");

            script = script.replace("${sets}", buf.toString()).replace("selective","").replace("notnull","");
        }
        return script;
    }

    private boolean isIdField(ResultMap resultMap, Field field) {
        if (resultMap != null) {
            for (ResultMapping resultMapping : resultMap.getIdResultMappings()) {
                if (resultMapping.getProperty().equals(field.getName())){
                    return true;
                }
            }
        }
        return false;
    }

    private ResultMapping getIdResultMapping(ResultMap resultMap, Field field) {
        if (resultMap != null) {
            if (resultMap.getIdResultMappings().size() > 0){
                return resultMap.getIdResultMappings().get(0);
            }

        }
        return null;
    }

    private ResultMapping getResultMapping(ResultMap resultMap, Field field) {
        if (resultMap != null) {
            for (ResultMapping resultMapping : resultMap.getPropertyResultMappings()) {
                if (resultMapping.getProperty().equals(field.getName())){
                    return resultMapping;
                }
            }
        }
        return null;
    }

    private String getEmptyTesting(Field field) {
        return getEmptyTesting(field,false);
    }

    private String getEmptyTesting(Field field, boolean isNotNull) {
        if (String.class == field.getType()) {
            if (!isNotNull) {
                return String.format("%s != null and %s != ''", field.getName(), field.getName());
            }else{
                return String.format("%s != null", field.getName());
            }
        }else {
            return String.format("%s != null", field.getName());
        }
    }

    private String getColumnName(ResultMapping resultMapping, Field field) {
        if (resultMapping == null){
            return field.getName();
        }

        return resultMapping.getColumn();
    }

    private String getColumnValue(ResultMapping resultMapping, Field field) {
        if (resultMapping == null || resultMapping.getJdbcType() == null){
            return String.format("#{%s}", field.getName());
        }

        return String.format("#{%s,jdbcType=%s}", field.getName(), resultMapping.getJdbcType());
    }

    private String toUnderline(String str) {
        StringBuilder buf = new StringBuilder();
        buf.append(Character.toLowerCase(str.charAt(0)));
        for (int i = 1; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                buf.append("_" + Character.toLowerCase(c));
            } else {
                buf.append(c);
            }
        }
        return buf.toString();
    }
}
