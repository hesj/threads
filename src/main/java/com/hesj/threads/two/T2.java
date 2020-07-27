package com.hesj.threads.two;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 2、在Java中Lock接口比synchronized块的优势是什么?你需要实现一个高效的缓存，它允许多个用户读，但只允许一个用户写，以此来保持它的完整性，你会怎样去实现它?
 * <p>
 * lock接口在多线程和并发编程中最大的优势是它们为读和写分别提供了锁，它能满足你写像ConcurrentHashMap这样的高性能数据结构和有条件的阻塞。
 * Java线程面试的问题越来越会根据面试者的回答来提问。我强烈建议在你去参加多线程的面试之前认真读一下Locks，因为当前其大量用于构建电子交易终统的客户端缓存和交易连接空间。
 */
public class T2 {
    public static void main(String[] args) {

    }

}

class MyCache1<K, V> {

    final Map<K, V> cache = new HashMap<>();

    final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    final Lock readLock = readWriteLock.readLock();
    final Lock writeLock = readWriteLock.writeLock();

    public V getCache(K k) {
        V v = null;
        readLock.lock();
        try {
            v = cache.get(k);

        } finally {
            readLock.unlock();
        }
        if (v != null) {
            return v;
        }
        writeLock.lock();
        try {
            v = cache.get(k);
            if (v == null) {
                //doSomething();
                v = (V) new Object();
                cache.put(k, v);
            }
        } finally {
            writeLock.unlock();
        }
        return v;
    }

}

class MyCache<K, V> {

    final Map<K, V> cache = new HashMap<>();

    final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    final Lock readLock = readWriteLock.readLock();
    final Lock writeLock = readWriteLock.writeLock();

    public V getCache(K k) {
        readLock.lock();
        try {
            return cache.get(k);
        } finally {
            readLock.unlock();
        }
    }

    public V put(K k, V v) {
        writeLock.lock();
        try {
            return cache.put(k, v);
        } finally {
            writeLock.unlock();
        }
    }
}
