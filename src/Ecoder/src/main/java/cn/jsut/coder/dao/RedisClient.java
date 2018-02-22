package cn.jsut.coder.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisPool;

/**
 * @author:
 * @date:2018/1/16 10:05
 * @version:
 * @copyright:
 */
@Repository
public class RedisClient {
    @Autowired
    private JedisPool jedisPool;

    public void addJsonData(String key,String target){
        jedisPool.getResource().set(key,target);
    }

    public boolean isExistKey(String key){
        Boolean result = jedisPool.getResource().exists(key);
        return result;
    }

}
