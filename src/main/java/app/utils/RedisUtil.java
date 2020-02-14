package app.utils;

import conf.AppConf;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class RedisUtil {

    private static JedisPool pool = null;
    private static String url = "127.0.0.1";
    private static int port = 6379;
    private static int database = 0;
    private static String auth = "";
    private static String prefix = "";
    private static int timeout = 2000;
    private static int maxTotal = 500;
    private static int maxIdle = 8;
    private static int maxWaitMillis = 100000;
    private static Boolean testOnBorrow = true;

    private static RedisUtil instatnce = null;

    public RedisUtil(){
        ResourceBundle config = ResourceBundle.getBundle(AppConf.ENV);
        url = config.getString("redis.default.url");
        port  = Integer.parseInt(config.getString("redis.default.port"));
        database  = Integer.parseInt(config.getString("redis.default.database"));
        auth = config.getString("redis.default.auth");
        prefix = config.getString("redis.default.prefix");
        timeout  = Integer.parseInt(config.getString("redis.default.timeout"));
        maxTotal  = Integer.parseInt(config.getString("redis.default.max_total"));
        maxIdle  = Integer.parseInt(config.getString("redis.default.max_idle"));
        maxWaitMillis  = Integer.parseInt(config.getString("redis.default.max_wait_millis"));
        testOnBorrow  = Boolean.parseBoolean(config.getString("redis.default.test_on_borrow"));
        pool = getPool();
    }

    public static RedisUtil getInstatnce(){
        if (instatnce == null) {
            instatnce = new RedisUtil();
        }
        return instatnce;
    }

    private JedisPool getPool() {
        if (pool == null) {
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(maxTotal);
            poolConfig.setMaxIdle(maxIdle);
            poolConfig.setMaxWaitMillis(maxWaitMillis);
            poolConfig.setTestOnBorrow(testOnBorrow);
            if(auth.equals("")){
                pool = new JedisPool(poolConfig, url, port, timeout, null, database);
            }else{
                pool = new JedisPool(poolConfig, url, port, timeout, auth, database);
            }
        }
        return pool;
    }

    public boolean exists(String key) {
        key = prefix + "_" + key;
        boolean result = false;
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            result = jedis.exists(key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return result;
    }

    public boolean setValue(String key, String value, int expire) {
        key = prefix + "_" + key;
        boolean result = false;
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            jedis.set(key, value);
            jedis.expire(key, expire);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return result;
    }

    public boolean hmsetValue(String key, Map<String, String> valueMap, int expire) {
        key = prefix + "_" + key;
        boolean result = false;
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            jedis.hmset(key, valueMap);
            jedis.expire(key, expire);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return result;
    }

    public String getValue(String key) {
        key = prefix + "_" + key;
        String result = "";
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            result = jedis.get(key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return result;
    }

    public  Map<String, String> hmgetValue(String key) {
        key = prefix + "_" + key;
        Map<String, String> result = null;
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            result = jedis.hgetAll(key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return result;
    }

    public boolean delValue(String key) {
        key = prefix + "_" + key;
        boolean result = false;
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            jedis.del(key);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return result;
    }

    public boolean delAllValue(String keys) {
        boolean result = false;
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            Set<String> keySet = jedis.keys(keys);
            for (String key : keySet) {
                jedis.del(key);
            }
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return result;
    }

    public boolean expireKey(String key, int expire) {
        key = prefix + "_" + key;
        boolean result = false;
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            jedis.expire(key, expire);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return result;
    }

}
