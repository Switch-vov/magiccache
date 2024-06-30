package com.switchvov.magiccache.core;

import com.switchvov.magiccache.core.opr.CommonOperator;
import com.switchvov.magiccache.core.opr.HashOperator;
import com.switchvov.magiccache.core.opr.ListOperator;
import com.switchvov.magiccache.core.opr.SetOperator;
import com.switchvov.magiccache.core.opr.StringOperator;
import com.switchvov.magiccache.core.opr.ZsetOperator;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * cache entries.
 *
 * @author switch
 * @since 2024/06/17
 */
public class MagicCache {

    private ScheduledExecutorService executor;

    private final CommonOperator commonOperator = new CommonOperator();
    private final StringOperator stringOperator = new StringOperator();
    private final ListOperator listOperator = new ListOperator();
    private final SetOperator setOperator = new SetOperator();
    private final ZsetOperator zsetOperator = new ZsetOperator();
    private final HashOperator hashOperator = new HashOperator();

    public void start() {
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(AbstractOperator::updateTs, 10, 10, TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        executor.shutdown();
        if (!executor.isTerminated()) {
            try {
                if (!executor.awaitTermination(3, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    // ===============  0. Common  ===========

    public int exists(String... keys) {
        return commonOperator.exists(keys);
    }

    public int del(String... keys) {
        return commonOperator.del(keys);
    }

    public boolean expire(String key, long ttl) {
        return commonOperator.expire(key, ttl);
    }

    public long ttl(String key) {
        return commonOperator.ttl(key);
    }


    // ===============  0. Common end ===========

    // ===============  1. String  ===========

    public void set(String key, String value) {
        stringOperator.set(key, value);
    }

    public String get(String key) {
        return stringOperator.get(key);
    }

    public String[] mget(String... keys) {
        return stringOperator.mget(keys);
    }

    public void mset(String[] keys, String[] vals) {
        stringOperator.mset(keys, vals);
    }

    public Integer strlen(String key) {
        return stringOperator.strlen(key);
    }

    public int incr(String key) {
        return stringOperator.incr(key);
    }

    public int decr(String key) {
        return stringOperator.decr(key);
    }

    // ===============  1. String end ===========

    // ===============  2. list  ===========

    public Integer lpush(String key, String... vals) {
        return listOperator.lpush(key, vals);
    }

    public String[] lpop(String key, int count) {
        return listOperator.lpop(key, count);
    }

    public Integer rpush(String key, String... vals) {
        return listOperator.rpush(key, vals);
    }

    public String[] rpop(String key, int count) {
        return listOperator.rpop(key, count);
    }

    public int llen(String key) {
        return listOperator.llen(key);
    }

    public String lindex(String key, int index) {
        return listOperator.lindex(key, index);
    }

    public String[] lrange(String key, int start, int end) {
        return listOperator.lrange(key, start, end);
    }

    // ===============  2. list end ===========

    // ===============  3. set  ===========

    public Integer sadd(String key, String[] vals) {
        return setOperator.sadd(key, vals);
    }

    public String[] smembers(String key) {
        return setOperator.smembers(key);
    }

    public Integer scard(String key) {
        return setOperator.scard(key);
    }

    public Integer sismember(String key, String val) {
        return setOperator.sismember(key, val);
    }

    public Integer srem(String key, String[] vals) {
        return setOperator.srem(key, vals);
    }

    public String[] spop(String key, int count) {
        return setOperator.spop(key, count);
    }

    // ===============  3. set end ===========

    // ===============  4. hash ===========

    public Integer hset(String key, String[] hkeys, String[] hvals) {
        return hashOperator.hset(key, hkeys, hvals);
    }

    public String hget(String key, String hkey) {
        return hashOperator.hget(key, hkey);
    }

    public String[] hgetall(String key) {
        return hashOperator.hgetall(key);
    }

    public String[] hmget(String key, String[] hkeys) {
        return hashOperator.hmget(key, hkeys);
    }

    public Integer hlen(String key) {
        return hashOperator.hlen(key);
    }

    public Integer hexists(String key, String hkey) {
        return hashOperator.hexists(key, hkey);
    }

    public Integer hdel(String key, String[] hkeys) {
        return hashOperator.hdel(key, hkeys);
    }

    // ===============  4. hash end ===========

    // ===============  5. zset end ===========

    public Integer zadd(String key, String[] values, double[] scores) {
        return zsetOperator.zadd(key, values, scores);
    }

    public Integer zcard(String key) {
        return zsetOperator.zcard(key);
    }

    public Integer zcount(String key, double min, double max) {
        return zsetOperator.zcount(key, min, max);
    }

    public Double zscore(String key, String val) {
        return zsetOperator.zscore(key, val);
    }

    public Integer zrank(String key, String val) {
        return zsetOperator.zrank(key, val);
    }

    public Integer zrem(String key, String[] vals) {
        return zsetOperator.zrem(key, vals);
    }

    // ===============  5. zset end ===========
}
