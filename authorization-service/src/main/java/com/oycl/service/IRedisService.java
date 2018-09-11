package com.oycl.service;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * redis的操作接口
 * @author 谢捷峰
 *
 */
public interface IRedisService {

    /*********************************String start**********************************************/
    /*
     * 将字符串值 value 关联到 key
     */
    public boolean set(String key, String value);

    /*
     * 将字符串值 value 关联到 key，并设置过期时间，单位秒
     */
    public boolean set(String key, String value, long seconds);

    /*
     * 将 key 的值设为 value ，当且仅当 key 不存在。
     * 若给定的 key 已经存在，则 SETNX 不做任何动作。
     */
    public Boolean setnx(String key, String value);

    /*
     * 返回 key 所关联的字符串值。
     */
    public String get(String key);

    /*
     * 设置过期时间
     */
    public boolean expire(String key, long expire);

    /*
     * 将list以json的形式关联到key
     */
    public <T> boolean setList(String key, List<T> list);

    /*
     * 返回key值所对应的对象的list
     */
    public <T> List<T> getList(String key, Class<T> clz);
    /*********************************String end**********************************************/

    /*********************************List start**********************************************/
    /*
     * 将一个或多个值 value 插入到列表 key 的表头
     */
    public long lpush(String key, Object obj);

    /*
     * 将一个或多个值 value 插入到列表 key 的表尾
     */
    public long rpush(String key, Object obj);

    /*
     * 移除并返回列表 key 的头元素。
     */
    public String lpop(String key);
    /*********************************List end**********************************************/

    /*********************************Hash start**********************************************/
    /*
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中。
     */
    public boolean hmset(String key, Map<String, String> hashMap);

    /*
     * 返回哈希表 key 中，一个或多个给定域的值。
     */
    public List<String> hmget(String key);

    /*
     * 将哈希表 key 中的域 field 的值设为 value 。
     */
    public boolean hset(String key, String hashKey, String value);

    /*
     * 返回哈希表 key 中给定域 field 的值。
     */
    public String hget(String key, String hashKey);

    /*
     * redis 删除hashkey
     */
    public Long hdel(String key, String hashKeys);

    /*********************************Hash end**********************************************/

    /*********************************key start**********************************************/
    /*
     * 查找所有符合给定模式 pattern 的 key 。
     * KEYS * 匹配数据库中所有 key 。
     * KEYS h?llo 匹配 hello ， hallo 和 hxllo 等。
     * KEYS h*llo 匹配 hllo 和 heeeeello 等。
     * KEYS h[ae]llo 匹配 hello 和 hallo ，但不匹配 hillo 。
     * 特殊符号用 \ 隔开
     */
    public Set<String> keys(String pattern);

    /*
     * 删除给定的key 。
     */
    public Boolean del(String key);

    /*
     * 判断是否存在key 。
     */
    public Boolean exists(String key);

    /*********************************key end**********************************************/
    /*********************************sorted set start**********************************************/
    /*
     * redis zadd
     */
    public Boolean zadd(String key, double score, String value);

    /*
     * redis zadd
     */
    public Boolean zadd(String key, List<String> values);


    public Boolean removeRangeByScore(String key, double start, double end);

    /*
     * redis zrange 所有元素
     */
    public Set<String> zrangeAll(String key);

    /*
     * redis zrange 指定元素
     */
    public Set<String> zrange(String key, long start, long end);
    /*********************************sorted set  end**********************************************/

    /*********************************other start**********************************************/
    /*
     * 查找所有符合给定模式 pattern 的 key 。
     * KEYS * 匹配数据库中所有 key 。
     * KEYS h?llo 匹配 hello ， hallo 和 hxllo 等。
     * KEYS h*llo 匹配 hllo 和 heeeeello 等。
     * KEYS h[ae]llo 匹配 hello 和 hallo ，但不匹配 hillo 。
     * 特殊符号用 \ 隔开
     */
//	public Boolean deleteByPattern(String pattern);

    /**
     * 功能描述： 减1操作,并设置过期时间，并事务乐观锁
     *
     */
    boolean decrAndExpireByLock(String key, int seconds);

    /**
     * 功能描述： 减1操作,并在减为0时设置过期时间，并事务乐观锁
     *
     */
    boolean decrAndLastExpireByLock(String key, int seconds);
    /*********************************other end**********************************************/
}