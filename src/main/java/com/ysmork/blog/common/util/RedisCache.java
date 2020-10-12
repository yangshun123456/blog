package com.ysmork.blog.common.util;

import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author yangshun
 * @version 1.0
 * @program: blog
 * @description: redis工具类
 * @date 2020/10/4 20:41
 */
@Component
public class RedisCache {

    private final Long LOCKTIMEOUT = 30000L;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     * @return 缓存的对象
     */
    public <T> ValueOperations<String, T> setCacheObject(String key, T value)
    {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        operation.set(key, value);
        return operation;
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 时间
     * @param timeUnit 时间颗粒度
     * @return 缓存的对象
     */
    public <T> ValueOperations<String, T> setCacheObject(String key, T value, Integer timeout, TimeUnit timeUnit)
    {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        operation.set(key, value, timeout, timeUnit);
        return operation;
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(String key)
    {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public void deleteObject(String key)
    {
        redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     *
     * @param collection
     */
    public void deleteObject(Collection collection)
    {
        redisTemplate.delete(collection);
    }

    /**
     * 缓存List数据
     *
     * @param key 缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> ListOperations<String, T> setCacheList(String key, List<T> dataList)
    {
        ListOperations listOperation = redisTemplate.opsForList();
        if (null != dataList)
        {
            int size = dataList.size();
            for (int i = 0; i < size; i++)
            {
                listOperation.leftPush(key, dataList.get(i));
            }
        }
        return listOperation;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(String key)
    {
        List<T> dataList = new ArrayList<T> ();
        ListOperations<String, T> listOperation = redisTemplate.opsForList();
        Long size = listOperation.size(key);

        for (int i = 0; i < size; i++)
        {
            dataList.add(listOperation.index(key, i));
        }
        return dataList;
    }

    /**
     * 缓存Set
     *
     * @param key 缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(String key, Set<T> dataSet)
    {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext())
        {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public <T> Set<T> getCacheSet(String key)
    {
        Set<T> dataSet = new HashSet<T>();
        BoundSetOperations<String, T> operation = redisTemplate.boundSetOps(key);
        dataSet = operation.members();
        return dataSet;
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     * @return
     */
    public <T> HashOperations<String, String, T> setCacheMap(String key, Map<String, T> dataMap)
    {
        HashOperations hashOperations = redisTemplate.opsForHash();
        if (null != dataMap)
        {
            for (Map.Entry<String, T> entry : dataMap.entrySet())
            {
                hashOperations.put(key, entry.getKey(), entry.getValue());
            }
        }
        return hashOperations;
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    public <T> Map<String, T> getCacheMap(String key)
    {
        Map<String, T> map = redisTemplate.opsForHash().entries(key);
        return map;
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(String pattern)
    {
        return redisTemplate.keys(pattern);
    }

    /**
     * 设置递增
     * @param key
     * @return
     */
    public Long incr(String key) {
        Long value = redisTemplate.opsForValue().increment(key);
        if (value == 1) {
            redisTemplate.expire(key,1,TimeUnit.DAYS);
        }
        return value;
    }

    /**
     * 尝试获取锁，立即返回结果
     *
     * @param key 锁key
     * @return boolean
     */
    public boolean tryLock(String key) {
        if (StringUtils.isEmpty(key)) {
            return true;
        }
        return lock(key, LOCKTIMEOUT, TimeUnit.MILLISECONDS);
    }

    /**
     * 尝试获取锁，立即返回结果，指定锁过期时间
     *
     * @param key key
     * @param time 锁过期时间
     * @param unit 时间单位
     * @return boolean
     */
    public boolean lock(String key, long time, TimeUnit unit) {
        long lockTime = unit.toMillis(time);
        final ValueOperations<String, String> ops = redisTemplate.opsForValue();
        if (ops.setIfAbsent(key, getCacheValue(lockTime),LOCKTIMEOUT,TimeUnit.MILLISECONDS)) {
            return true;
        }
        String currentValue = ops.get(key);

        // 如果currentValue为空，说明锁拥有者已经释放掉锁了，可以进行再次尝试
        if (StringUtils.isEmpty(currentValue)) {
            return ops.setIfAbsent(key, getCacheValue(lockTime));
        }

        //判断上一个锁是否到期，到期尝试获取锁
        if (System.currentTimeMillis() >= getExpireTime(currentValue)) {
            //多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，它才有权利获取锁
            String oldValue = ops.getAndSet(key, getCacheValue(lockTime));
            return currentValue.equals(oldValue);
        }
        return false;
    }


    /**
     * 尝试获取锁，如果成功，立即返回，如果一直失败，等到超时之后返回
     *
     * @param key     锁key
     * @param timeout 等待时间
     * @param unit    TimeUnit
     * @return boolean
     * @throws InterruptedException InterruptedException
     */
    public boolean tryLock(String key, long timeout, TimeUnit unit) {

        long nanosTimeout = unit.toNanos(timeout);
        long lastTime = System.nanoTime();
        do {
            if (tryLock(key)) {
                return true;
            }

            long now = System.nanoTime();
            nanosTimeout -= now - lastTime;
            lastTime = now;

            //响应中断
            if (Thread.interrupted()) {
                return false;
//                throw new InterruptedException();
            }
        } while (nanosTimeout >= 0L);

        return false;
    }

    /**
     * 获取锁，如果成功，立即返回，如果失败，则不停的尝试，直到成功为止
     *
     * @param key 锁key
     * @throws InterruptedException InterruptedException
     */
    public void lock(String key) throws InterruptedException {

        while (true) {
            if (tryLock(key)) {
                return;
            }

            //响应中断
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
        }
    }

    /**
     * 释放锁
     *
     * @param key key
     */
    public void unlock(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 获取当期线程缓存value
     *
     * @param timeout 超时时间
     * @return 缓存值
     */
    private String getCacheValue(long timeout) {
        return Thread.currentThread().getName() + ";" + String.valueOf(System.currentTimeMillis() + timeout);
    }

    /**
     * 获取过期时间
     *
     * @param cacheValue 缓存值
     * @return 过期时间
     */
    private long getExpireTime(String cacheValue) {
        String[] values = cacheValue.split(";");
        return values.length > 1 ? Long.parseLong(values[1]) : 0L;
    }

}