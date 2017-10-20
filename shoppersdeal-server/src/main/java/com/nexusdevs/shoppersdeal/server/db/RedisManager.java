package com.nexusdevs.shoppersdeal.server.db;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisManager {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisManager.class);

	private JedisPool jedisPool;

	public RedisManager(String host) {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPool = new JedisPool(jedisPoolConfig, host);
	}

	public void shutdown() {
		try {
			jedisPool.destroy();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void set(String key, String val) {
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.set(key, val);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void set(String key, String val, long expiryInMillis) {
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.set(key, val, "NX", "PX", expiryInMillis);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void setex(String key, String val, int expiryInSeconds) {
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.setex(key, expiryInSeconds, val);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public long expire(String key, int expiryInSeconds) {
		try (Jedis jedis = jedisPool.getResource()) {
			long k = jedis.expire(key, expiryInSeconds);
			return k;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return 0;
	}

	public String get(String key) {
		try (Jedis jedis = jedisPool.getResource()) {
			String val = jedis.get(key);
			return val;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public long incr(String key) {
		try (Jedis jedis = jedisPool.getResource()) {
			long v = jedis.incr(key);
			return v;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return 0;
	}

	public void del(String key) {
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.del(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void del(String[] keys) {
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.del(keys);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public Set<String> keys(String keyRegex) {
		try (Jedis jedis = jedisPool.getResource()) {
			Set<String> keys = jedis.keys(keyRegex);
			return keys;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public void hdel(String key,String field){
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.hdel(key, field);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void hdel(String key,List<String> fields){
		if(fields==null||fields.size()==0){
			return;
		}
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.hdel(key, fields.toArray(new String[0]));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public String hget(String key,String field){
		try (Jedis jedis = jedisPool.getResource()) {
			String val = jedis.hget(key, field);
			return val;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public Map<String,String> hgetAll(String key){
		try (Jedis jedis = jedisPool.getResource()) {
			Map<String,String> val = jedis.hgetAll(key);
			return val;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public Set<String> hkeys(String key) {
		try (Jedis jedis = jedisPool.getResource()) {
			Set<String> keys = jedis.hkeys(key);
			return keys;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public long hlen(String key) {
		try (Jedis jedis = jedisPool.getResource()) {
			long keyLength = jedis.hlen(key);
			return keyLength;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return 0;
	}

	public List<String> hmget(String key, List<String> fields) {
		try (Jedis jedis = jedisPool.getResource()) {
			List<String> hmget = jedis.hmget(key, fields.toArray(new String[0]));
			return hmget;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public void hmset(String key, Map<String, String> fieldValueMap) {
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.hmset(key, fieldValueMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void hset(String key, String field, String value) {
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.hset(key, field, value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	public void hsetnx(String key, String field, String value) {
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.hsetnx(key, field, value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	

	public List<String> hvals(String key) {
		try (Jedis jedis = jedisPool.getResource()) {
			List<String> hvals = jedis.hvals(key);
			return hvals;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static void main(String[] args) {
		RedisManager redisManager = new RedisManager("localhost");
		redisManager.set("ram", "kaam", 20000);
		Object lock = new Object();
		for (int i = 0; i < 100; i++) {
			String val = redisManager.get("ram");
			System.out.println(i + " got value = " + val);
			if (val == null) {
				break;
			}
			synchronized (lock) {
				try {
					lock.wait(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
		redisManager.shutdown();
	}
	
}
