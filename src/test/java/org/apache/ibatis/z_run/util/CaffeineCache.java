package org.apache.ibatis.z_run.util;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.ibatis.cache.Cache;

import java.time.Duration;
import java.util.concurrent.locks.ReadWriteLock;

public class CaffeineCache implements Cache {

    private final String id;

    static com.github.benmanes.caffeine.cache.Cache<Object, Object> cache = null;

    static {
        cache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(Long.valueOf(100)))
                .maximumSize(Long.valueOf(10000))
                .initialCapacity(10)
                .build();
    }

    public CaffeineCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        cache.put(key, value);
    }

    @Override
    public Object getObject(Object key) {
        return cache.getIfPresent(key);
    }

    @Override
    public Object removeObject(Object key) {
        cache.invalidate(key);
        return null;
    }

    @Override
    public void clear() {
        cache.cleanUp();
    }

    @Override
    public int getSize() {
        return Integer.valueOf(cache.estimatedSize()+"");
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
