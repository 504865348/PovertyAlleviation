package com.example.jayny.povertyalleviation;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by jayny on 2016/12/19.
 */

public class Executor {
    private static final int CORE_POOL_SIZE =5;//5个核心工作线程
    private static final int MAXIMUM_POOL_SIZE = 128;//最多128个工作线程
    private static final int KEEP_ALIVE = 1;//空闲线程的超时时间为1秒

    public static java.util.concurrent.Executor exec = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
            TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(10));
}
