package com.hesj.threads.four;

public interface BlockingQueue<E> {

    void put(E e) throws InterruptedException;


    E take() throws InterruptedException;

    int size();

    boolean isEmpty();
}
