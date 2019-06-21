package com.oycl.compment.async;

public abstract class Task {
    public enum TaskType {
        /* 立刻消费. */
        ITC,
        /* 阻塞.*/
        BLOCKING;
    }

    /**
     * 任务类型
     */
    public TaskType type;

    /**
     * 任务名称
     */
    public String name;

    public Task(TaskType type) {
        this.type = type;
        this.name = this.getClass().getSimpleName();
    }


}
