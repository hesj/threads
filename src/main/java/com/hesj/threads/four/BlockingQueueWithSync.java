package com.hesj.threads.four;

import java.util.ArrayList;
import java.util.List;

public class BlockingQueueWithSync<E> implements BlockingQueue<E> {

    private List<E> queue = new ArrayList<>();
    private int maxSize;

    BlockingQueueWithSync(int maxSize) {
        this.maxSize = maxSize;
    }


    @Override
    public synchronized void put(E e) throws InterruptedException {
        while (this.size() == maxSize) {
            wait();
        }
        queue.add(e);
        notifyAll();
    }

    @Override
    public synchronized E take() throws InterruptedException {
        while (this.size() == 0) {
            wait();
        }
        E remove = queue.remove(0);
        notifyAll();
        return remove;
    }

    @Override
    public synchronized int size() {
        return queue.size();
    }

    @Override
    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
