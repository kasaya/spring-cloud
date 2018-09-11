package com.oycl.common.util;


import com.oycl.service.IRedisService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * REDIS连接工具类
 *
 * @author cango
 */
public class RedisUtil {

    private static IRedisService redisService;

    /*********************************String start**********************************************/
    /**
     * 将字符串值 value 关联到 key
     */
    public static boolean set(String key, String value) {
        return redisService.set(key, value);
    }

    /**
     * 将字符串值 value 关联到 key，并设置过期时间，单位秒
     */
    public static boolean set(String key, String value, long seconds) {
        return redisService.set(key, value, seconds);
    }

    /**
     * 将 key 的值设为 value ，当且仅当 key 不存在。
     * 若给定的 key 已经存在，则 SETNX 不做任何动作。
     */
    public static Boolean setnx(String key, String value) {
        return redisService.setnx(key, value);
    }

    /**
     * 返回 key 所关联的字符串值。
     */
    public static String get(String key) {
        return redisService.get(key);
    }

    /**
     * 设置过期时间
     */
    public static boolean expire(String key, long expire) {
        return redisService.expire(key, expire);
    }

    /**
     * 将list以json的形式关联到key
     */
    public static <T> boolean setList(String key, List<T> list) {
        return redisService.setList(key, list);
    }

    /**
     * 返回key值所对应的对象的list
     */
    public static <T> List<T> getList(String key, Class<T> clz) {
        return redisService.getList(key, clz);
    }
    /*********************************String end**********************************************/

    /*********************************Hash start**********************************************/
    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中。
     */
    public static boolean hmset(String key, Map<String, String> hashMap) {
        return redisService.hmset(key, hashMap);
    }

    /**
     * 返回哈希表 key 中，一个或多个给定域的值。
     */
    public static List<String> hmget(String key) {
        return redisService.hmget(key);
    }

    /**
     * 将哈希表 key 中的域 field 的值设为 value 。
     */
    public static boolean hset(String key, String hashKey, String value) {
        return redisService.hset(key, hashKey, value);
    }

    /**
     * 返回哈希表 key 中给定域 field 的值。
     */
    public static String hget(String key, String hashKey) {
        return redisService.hget(key, hashKey);
    }

    /*********************************Hash end**********************************************/

    /*********************************List start**********************************************/
    /**
     * 将一个或多个值 value 插入到列表 key 的表头
     */
    public static long lpush(String key, Object obj) {
        return redisService.lpush(key, obj);
    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表尾
     */
    public static long rpush(String key, Object obj) {
        return redisService.rpush(key, obj);
    }

    /**
     * 移除并返回列表 key 的头元素。
     */
    public static String lpop(String key) {
        return redisService.lpop(key);
    }
    /*********************************List end**********************************************/

    /*********************************key start**********************************************/
    /**
     * 查找所有符合给定模式 pattern 的 key 。
     * KEYS * 匹配数据库中所有 key 。
     * KEYS h?llo 匹配 hello ， hallo 和 hxllo 等。
     * KEYS h*llo 匹配 hllo 和 heeeeello 等。
     * KEYS h[ae]llo 匹配 hello 和 hallo ，但不匹配 hillo 。
     * 特殊符号用 \ 隔开
     */
    public static Set<String> keys(String pattern) {
        return redisService.keys(pattern);
    }

    /**
     * 删除给定的key 。
     */
    public static Boolean del(String key) {
        return redisService.del(key);
    }

    /**
     * 判断是否存在key 。
     */
    public static Boolean exists(String key) {
        return redisService.exists(key);
    }

    /*********************************key end**********************************************/

    /*********************************other start**********************************************/
    /**
     * 查找所有符合给定模式 pattern 的 key 。
     * KEYS * 匹配数据库中所有 key 。
     * KEYS h?llo 匹配 hello ， hallo 和 hxllo 等。
     * KEYS h*llo 匹配 hllo 和 heeeeello 等。
     * KEYS h[ae]llo 匹配 hello 和 hallo ，但不匹配 hillo 。
     * 特殊符号用 \ 隔开
     */
//  public Boolean deleteByPattern(String pattern);

    /**
     * 功能描述： 减1操作,并设置过期时间，并事务乐观锁
     *
     */
    public static boolean decrAndExpireByLock(String key, int seconds) {
        return redisService.decrAndExpireByLock(key, seconds);
    }

    /**
     * 功能描述： 减1操作,并在减为0时设置过期时间，并事务乐观锁
     *
     */
    public static boolean decrAndLastExpireByLock(String key, int seconds) {
        return redisService.decrAndLastExpireByLock(key, seconds);
    }
    /*********************************other end**********************************************/

    public static void setRedisService(IRedisService redisService) {
        RedisUtil.redisService = redisService;
    }

    /*********************************sorted set start**********************************************/
    /*
     * redis zadd
     */
    public static Boolean zadd(String key, List<String> values) {
        return redisService.zadd(key, values);
    }

    /*
     * redis zrange 所有元素
     */
    public static Set<String> zrangeAll(String key) {
        return redisService.zrangeAll(key);
    }

    /*
     * redis zrange 指定元素
     */
    public static Set<String> zrange(String key, long start, long end) {
        return redisService.zrange(key, start, end);
    }

    /*
     * redis zadd
     */
    public static Boolean zadd(String key, double score, String value) {
        return redisService.zadd(key, score, value);
    }



    public static Boolean removeRangeByScore(String key, double start, double end) {
        return redisService.removeRangeByScore(key, start, end);
    }
    /*********************************sorted set  end**********************************************/

}
