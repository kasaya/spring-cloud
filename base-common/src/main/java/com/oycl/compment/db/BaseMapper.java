package com.oycl.compment.db;


import org.apache.ibatis.annotations.*;

/**
 * <p>
 * BaseMapper 接口
 * </p>
 *
 * @author cango
 * @since 2019-01-15
 */
public interface BaseMapper<T, K> {

    @Lang(BaseMapperDriver.class)
    @Insert({"<script>", "INSERT INTO ${table} ${values}", "</script>"})
    public Long insert(T model);

    @Lang(BaseMapperDriver.class)
    @Update({"<script>", "UPDATE ${table} ${sets} WHERE ${id}=#{id}", "</script>"})
    public Long updateById(T model);

    @Lang(BaseMapperDriver.class)
    @Delete("DELETE FROM ${table} WHERE ${id}=#{id}")
    public Long deleteById(@Param("id") K id);

    @Lang(BaseMapperDriver.class)
    @Select("SELECT ${values} FROM ${table} WHERE ${id}=#{id}")
    public T getById(@Param("id") K id);

    @Lang(BaseMapperDriver.class)
    @Select("SELECT COUNT(1) FROM ${table} WHERE ${id}=#{id}")
    public Boolean existById(@Param("id") K id);
}
