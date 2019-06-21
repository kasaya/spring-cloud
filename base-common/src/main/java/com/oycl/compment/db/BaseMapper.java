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
public interface BaseMapper<T, K>  {

    @Lang(BaseMapperDriver.class)
    @Insert({"<script>", "INSERT INTO ${table} ${values}", "</script>"})
    public Long insert(T model);

    @Lang(BaseMapperDriver.class)
    @Update({"<script>", "UPDATE ${table} ${sets} WHERE ${id}=#{id}", "</script>"})
    public Long updateById(T model);

    @Lang(BaseMapperDriver.class)
    @Update({"<script>", "selective UPDATE ${table} ${sets} WHERE ${id}=#{id}", "</script>"})
    public Long updateByIdSelective(T model);

    @Lang(BaseMapperDriver.class)
    @Update({"<script>", "notnull UPDATE ${table} ${sets} WHERE ${id}=#{id}", "</script>"})
    public Long updateByIdNotNull(T model);

    @Lang(BaseMapperDriver.class)
    @Delete({"<script>","DELETE FROM ${table} WHERE ${id}=#{id}","</script>"})
    public Long deleteById(@Param("id") K id);

    @Lang(BaseMapperDriver.class)
    @Select({"<script>","SELECT ${values} FROM ${table} WHERE ${id}=#{id}","</script>"})
    public T getById(@Param("id") K id);

    @Lang(BaseMapperDriver.class)
    @Select({"<script>","SELECT COUNT(1) FROM ${table} WHERE ${id}=#{id}","</script>"})
    public Boolean existById(@Param("id") K id);
}
