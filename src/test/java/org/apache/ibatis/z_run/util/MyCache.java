package org.apache.ibatis.z_run.util;

import org.apache.ibatis.cache.Cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

public class MyCache implements Cache {

    private final String id;

    static Map<Object, Object> cache = null;

    static {
        cache = new HashMap<>();
    }

    public MyCache(String id) {
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
        return cache.get(key);
    }

    @Override
    public Object removeObject(Object key) {
        cache.remove(key);
        return null;
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public int getSize() {
        return Integer.valueOf(cache.size()+"");
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
