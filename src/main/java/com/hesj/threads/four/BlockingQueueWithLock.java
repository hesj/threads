package com.hesj.threads.four;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueueWithLock<E> implements BlockingQueue<E> {

    private List<E> queue = new ArrayList<>();
    private int maxSize;

    final Lock lock = new ReentrantLock();
    // 条件变量：队列不满
    final Condition notFull = lock.newCondition();
    // 条件变量：队列不空
    final Condition notEmpty = lock.newCondition();

    BlockingQueueWithLock(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public void put(E e) throws InterruptedException {
        lock.lock();
        try {
            while (this.size() == maxSize) {
                notFull.await();
            }
            queue.add(e);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public E take() throws InterruptedException {
        lock.lock();
        try {
            while (size() == 0) {
                notEmpty.await();
            }

            E remove = queue.remove(0);
            notFull.signal();
            return remove;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
