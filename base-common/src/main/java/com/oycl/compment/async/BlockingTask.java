package com.oycl.compment.async;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;


/**
 * 说明：
 * 思路来自 博客 ：https://www.cnblogs.com/skyice/p/10080612.html
 * 不是由线程池去创建线程来处理，而是创建几个线程，然后抢占式的去消费任务，而过来的每次请求，都会放入到队列中。
 *
 *  @author oycl
 */
public class BlockingTask extends Task {

     /**
     * 等待处理的Consumer.
     */
    private ConcurrentLinkedQueue<TaskInfo> cs = new ConcurrentLinkedQueue<>();
    /**
     * 拥有线程的个数(默认1个)
     */
    private int tc = 1;

    private static final int  CORE_POOL_SIZE = 0;

    private static final long KEEP_LIVE_TIME = 60;

    /**
     * 容器大小，如果使用cs.size(),每次调用都会遍历链表，开销大
     */
    private AtomicInteger size = new AtomicInteger(0);


    private ExecutorService ex;

    /**
     * 默认为阻塞线程
     */
    public BlockingTask() {
        super(TaskType.BLOCKING);
    }


    public CountDownLatch getLatch() {
        return latch;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    private  CountDownLatch latch = new CountDownLatch(1);

    /**
     * 队列尺寸.
     */
    public int size() {
        return this.size.get();
    }

    public int getTc() {
        return tc;
    }

    public void setTc(int tc) {
        this.tc = tc < 1 ? 1 : tc;
    }

    /**
     * 启动线程
     */
    protected void start() {
        final BlockingTask blockingTask = this;
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("service-pool-%d")
                .build();

        //创建线程池
        ex = new ThreadPoolExecutor(CORE_POOL_SIZE, this.tc,
                KEEP_LIVE_TIME, TimeUnit.SECONDS,
                new SynchronousQueue<>(), threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
        //开启线程
        for (int i = 0; i < tc; i++) {
            ex.execute(() -> {
                while (true) {
                    blockingTask.run();
                }
            });
        }
    }

    /**
     * 抢占式消费任务
     */
    private void run() {
        try {
            TaskInfo c;

            while ((c = this.cs.poll()) == null) {
                latch.await();
            }
            //-1成功后则执行处理（抢占）
            this.size.decrementAndGet();
            Execution.execute(c);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            synchronized (this.getLatch()){
                while(this.size() == 0 && this.getLatch().getCount() == 0){
                    //重置门闩
                    this.setLatch(new CountDownLatch(1));
                }
            }
        }

    }

    /**
     * 添加任务.
     */
    public void push(TaskInfo c) {
        try {
            this.cs.offer(c);
            this.size.incrementAndGet();
        }finally {
            //通知线程消费信息
            latch.countDown();

        }
    }


    protected void shutdown() {
        ex.shutdown();
    }
}
