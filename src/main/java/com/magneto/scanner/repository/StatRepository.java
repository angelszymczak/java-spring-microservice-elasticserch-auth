package com.magneto.scanner.repository;

import com.magneto.scanner.models.Stat;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Repository
public class StatRepository {

    private static final String KEY = "Stat";
    private static final String STAT_ID = UUID.randomUUID().toString();

    private RedisTemplate<String, Stat> redisTemplate;
    private HashOperations hashOperations;

    public StatRepository(RedisTemplate<String, Stat> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    public Stat getStat() {
        return (Stat) hashOperations.get(KEY, STAT_ID);
    }

    public void save(Stat stat) {
        hashOperations.put(KEY, STAT_ID, stat);
    }
}
