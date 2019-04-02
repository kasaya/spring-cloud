package com.oycl.service.impl;


import com.oycl.service.IRedisService;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisServiceImpl implements IRedisService {
    private static Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    private RedisTemplate<String, ?> redisTemplate;

    private Gson gson = new Gson();

    private RedisSerializer<String> serializer = new StringRedisSerializer();

    public RedisServiceImpl(RedisTemplate<String, ?> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /*
     * redis的set
     *
     * @see com.cango.com.oycl.service.redis.IRedisService#set(java.lang.String,
     * java.lang.String)
     */
    @Override
    public boolean set(String key, String value) {


        try {
            boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.set(serializer.serialize(key), serializer.serialize(value));
                    return true;
                }
            });
            return result;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean setnx(String key, String value) {
        try {
            return redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    Boolean result = connection.setNX(serializer.serialize(key), serializer.serialize(value));
                    return result;
                }
            });
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * redis的get
     *
     * @see com.cango.com.oycl.service.redis.IRedisService#get(java.lang.String)
     */
    @Override
    public String get(String key) {
        try {
            String result = redisTemplate.execute(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    byte[] value = connection.get(serializer.serialize(key));
                    return serializer.deserialize(value);
                }
            });
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * redis设置超时时间
     *
     * @see com.cango.com.oycl.service.redis.IRedisService#expire(java.lang.String, long)
     */
    @Override
    public boolean expire(String key, long expire) {
        try {
            return redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    Boolean result = connection.expire(serializer.serialize(key), expire);
                    return result;
                }
            });
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * redis设置List的json
     *
     * @see com.cango.com.oycl.service.redis.IRedisService#expire(java.lang.String, long)
     */
    @Override
    public <T> boolean setList(String key, List<T> list) {
        String value = gson.toJson(list);
        return set(key, value);
    }

    /*
     * redis获取List的json
     *
     * @see com.cango.com.oycl.service.redis.IRedisService#expire(java.lang.String, long)
     */
    @Override
    public <T> List<T> getList(String key, Class<T> clz) {
        String json = get(key);
        if (json != null) {
            List<T> list = JSON.parseArray(json, clz);
            return list;
        }
        return null;
    }

    /*
     * redis 列表(List) 左插入
     *
     * @see com.cango.com.oycl.service.redis.IRedisService#expire(java.lang.String, long)
     */
    @Override
    public long lpush(String key, Object obj) {
        try {
            final String value = JSON.toJSONString(obj);
            long result = redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    long count = connection.lPush(serializer.serialize(key), serializer.serialize(value));
                    return count;
                }
            });
            return result;
        } catch (Exception e) {
            return 0L;
        }
    }

    /*
     * redis 列表(List) 右插入
     *
     * @see com.cango.com.oycl.service.redis.IRedisService#expire(java.lang.String, long)
     */
    @Override
    public long rpush(String key, Object obj) {
        try {
            final String value = JSON.toJSONString(obj);
            long result = redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    long count = connection.rPush(serializer.serialize(key), serializer.serialize(value));
                    return count;
                }
            });
            return result;
        } catch (Exception e) {
            return 0L;
        }
    }

    /*
     * redis 列表(List) 移出并获取列表的第一个元素
     *
     * @see com.cango.com.oycl.service.redis.IRedisService#expire(java.lang.String, long)
     */
    @Override
    public String lpop(String key) {
        try {
            String result = redisTemplate.execute(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    byte[] res = connection.lPop(serializer.serialize(key));
                    return serializer.deserialize(res);
                }
            });
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * redis 设置过期时间
     *
     * @see com.cango.com.oycl.service.redis.IRedisService#expire(java.lang.String, long)
     */
    @Override
    public boolean set(String key, String value, long seconds) {
        try {
            return redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    try {
                        connection.setEx(serializer.serialize(key), seconds, serializer.serialize(value));
                    } catch (Exception ex) {
                        logger.error("redis set error", ex);
                        return false;
                    }
                    return true;
                }
            });
        } catch (Exception e) {
            return false;
        }

    }

    /*
     * redis 批量设置hashmap
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public boolean hmset(String key, Map<String, String> hashMap) {
        try {
            return redisTemplate.execute(new SessionCallback<Boolean>() {
                @Override
                public <K, V> Boolean execute(RedisOperations<K, V> operations) throws DataAccessException {
                    BoundHashOperations<String, String, String> hv = ((RedisTemplate) operations).boundHashOps(key);
                    operations.multi();
                    hv.putAll(hashMap);
                    List<Object> result = operations.exec();
                    return result != null && !result.isEmpty() ? true : false;
                }
            });
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * redis 批量获取hashmap
     */
    @SuppressWarnings("rawtypes")
    @Override
    public List<String> hmget(String key) {
        try {
            return redisTemplate.execute(new SessionCallback<List<String>>() {
                @SuppressWarnings("unchecked")
                @Override
                public <K, V> List<String> execute(RedisOperations<K, V> operations) throws DataAccessException {
                    BoundHashOperations<String, String, String> hv = ((RedisTemplate) operations).boundHashOps(key);

                    Set<String> set = hv.keys();
                    if (set != null && set.size() > 0) {
                        return hv.multiGet(set);
                    }

                    return null;
                }
            });
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * redis 删除hashkey
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Long hdel(String key, String hashKeys) {
        try {
            return redisTemplate.execute(new SessionCallback<Long>() {
                @SuppressWarnings("unchecked")
                @Override
                public <K, V> Long execute(RedisOperations<K, V> operations) throws DataAccessException {
                    BoundHashOperations<String, String, String> hv = ((RedisTemplate) operations).boundHashOps(key);
                    return hv.delete(hashKeys);
                }
            });
        } catch (Exception e) {
            return 0L;
        }
    }

    /*
     * redis 取得hashmap的值
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public String hget(String key, String hashKey) {
        try {
            return redisTemplate.execute(new SessionCallback<String>() {
                @Override
                public <K, V> String execute(RedisOperations<K, V> operations) throws DataAccessException {
                    BoundHashOperations<String, String, String> hv = ((RedisTemplate) operations).boundHashOps(key);
                    String value = hv.get(hashKey);
                    if (StringUtils.isEmpty(value)) {
                        return null;
                    }
                    return value;
                }
            });
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * redis 模糊删除值
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Set<String> keys(String pattern) {
        try {
            return redisTemplate.execute(new SessionCallback<Set>() {
                @Override
                public <K, V> Set execute(RedisOperations<K, V> operations) throws DataAccessException {
                    Set<String> keySet = ((StringRedisTemplate) operations).keys(pattern);
                    return keySet;
                }
            });
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * redis 判断是否存在key
     */
    @Override
    public Boolean exists(String key) {
        try {
            return redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.exists(serializer.serialize(key));
                }
            });
        } catch (Exception e) {
            return false;
        }
    }
    /*
     * redis 模糊删除值
     */
    // @SuppressWarnings({ "rawtypes", "unchecked" })
    // @Override
    // public Boolean deleteByPattern(String pattern) {
    // return redisTemplate.execute(new SessionCallback<Boolean>() {
    // @Override
    // public <K, V> Boolean execute(RedisOperations<K, V> operations)
    // throws DataAccessException {
    //
    // Set<String> keySet = ((StringRedisTemplate)operations).keys(pattern);
    //
    // if (keySet != null && keySet.size() > 0) {
    // operations.multi();
    // for (Object key : keySet) {
    // ((RedisTemplate)operations).delete(key);
    // }
    // List<Object> result = operations.exec();
    // return result != null && !result.isEmpty() ? true : false;
    // }
    //
    // return true;
    // }
    // });
    // }

    /*
     * redis 删除值
     */
    @Override
    public Boolean del(String key) {
        try {
            boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    try {
                        connection.del(serializer.serialize(key));
                    } catch (Exception ex) {
                        logger.error("redis set error", ex);
                        return false;
                    }
                    return true;
                }
            });

            return result;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 功能描述： 减1操作,并设置过期时间，并事务乐观锁
     *
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean decrAndExpireByLock(String key, int seconds) {
        try {
            return redisTemplate.execute(new SessionCallback<Boolean>() {
                @Override
                public <K, V> Boolean execute(RedisOperations<K, V> operations) throws DataAccessException {

                    BoundValueOperations<String, String> bv = ((RedisTemplate) operations).boundValueOps(key);
                    String stock = bv.get();
                    if (!StringUtils.isEmpty(stock)) {
                        operations.multi();
                        int iStock = Integer.valueOf(stock);
                        if (iStock > 0) {
                            bv.increment(-1);
                            bv.expire(seconds, TimeUnit.SECONDS);
                        }
                        List<Object> result = operations.exec();
                        return result != null && !result.isEmpty() ? true : false;
                    }

                    return true;
                }
            });
        } catch (Exception e) {
            return false;
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean decrAndLastExpireByLock(String key, int seconds) {
        try {
            return redisTemplate.execute(new SessionCallback<Boolean>() {
                @Override
                public <K, V> Boolean execute(RedisOperations<K, V> operations) throws DataAccessException {

                    BoundValueOperations<String, String> bv = ((RedisTemplate) operations).boundValueOps(key);
                    String stock = bv.get();
                    if (!StringUtils.isEmpty(stock)) {
                        operations.multi();
                        int iStock = Integer.valueOf(stock);
                        if (iStock > 0) {
                            bv.increment(-1);
                            if (iStock == 1) {
                                bv.expire(seconds, TimeUnit.SECONDS);
                            }
                        }
                        List<Object> result = operations.exec();
                        return result != null && !result.isEmpty() ? true : false;
                    }

                    return true;
                }
            });
        } catch (Exception e) {
            return false;
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean hset(String key, String hashKey, String value) {
        try {
            return redisTemplate.execute(new SessionCallback<Boolean>() {
                @Override
                public <K, V> Boolean execute(RedisOperations<K, V> operations) throws DataAccessException {
                    BoundHashOperations<String, String, String> hv = ((RedisTemplate) operations).boundHashOps(key);
                    operations.multi();
                    hv.put(hashKey, value);
                    List<Object> result = operations.exec();
                    return result != null && !result.isEmpty() ? true : false;
                }
            });
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * redis zadd
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Boolean zadd(String key, double score, String value) {
        try {
            return redisTemplate.execute(new SessionCallback<Boolean>() {
                @Override
                public <K, V> Boolean execute(RedisOperations<K, V> operations) throws DataAccessException {
                    BoundZSetOperations<String, String> bz = ((RedisTemplate) operations).boundZSetOps(key);
                    return bz.add(value, score);
                }
            });
        } catch (Exception e) {
            return false;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Boolean removeRangeByScore(String key, double start, double end) {
        try {
            return redisTemplate.execute(new SessionCallback<Boolean>() {
                @Override
                public <K, V> Boolean execute(RedisOperations<K, V> operations) throws DataAccessException {
                    BoundZSetOperations<String, String> bz = ((RedisTemplate) operations).boundZSetOps(key);
                    // 移除原先的
                    bz.removeRangeByScore(start, end);
                    return true;
                }
            });
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * redis zadd
     */
    @Override
    public Boolean zadd(String key, List<String> values) {
        try {
            return redisTemplate.execute(new SessionCallback<Boolean>() {
                @Override
                public <K, V> Boolean execute(RedisOperations<K, V> operations) throws DataAccessException {

                    @SuppressWarnings({ "rawtypes", "unchecked" })
                    BoundZSetOperations<String, String> bz = ((RedisTemplate) operations).boundZSetOps(key);
                    for (int i = 0; i < values.size(); i++) {
                        bz.add(values.get(i), Double.valueOf(i+1));
                    }
                    return true;

                }
            });
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * redis zrange 所有元素
     */
    @Override
    public Set<String> zrangeAll(String key) {
        return this.zrange(key, 0L, -1L);
    }

    /*
     * redis zrange 指定元素
     */
    @Override
    public Set<String> zrange(String key, long start, long end) {
        return redisTemplate.execute(new SessionCallback<Set<String>>() {
            @Override
            public <K, V> Set<String> execute(RedisOperations<K, V> operations) throws DataAccessException {
                BoundZSetOperations<String, String> bound = ((StringRedisTemplate) operations).boundZSetOps(key);
                return bound.range(start, end);
            }
        });
    }

}
