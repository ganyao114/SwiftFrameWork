package net.gy.SwiftFrameWork.Service.thread.taskqueue.impl;

import net.gy.SwiftFrameWork.Service.thread.taskqueue.ITaskQueue;
import net.gy.SwiftFrameWork.Service.thread.taskqueue.entity.TaskPrio;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by gy on 2016/4/2.
 */
public class SingleTaskQueue implements ITaskQueue{
    private Vector<Runnable> tasks;
    private boolean running = false;
    private ExecutorService executorService;
    private Future<?> futureTask;
    private int count = 0;
    public SingleTaskQueue() {
        tasks = new Vector<Runnable>();
        executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public boolean addTask(Runnable runnable, TaskPrio prio) {
        count++;
        synchronized (tasks) {
            if (tasks.isEmpty() && !running) {
                running = true;
                futureTask = executorService.submit(runnable);
                if (futureTask == null) {
                    count--;
                }
            } else {
                tasks.add(runnable);
            }
        }
        return true;
    }

    @Override
    public boolean runTask() {
        synchronized (tasks){
            running = false;
            futureTask = null;
            if (!tasks.isEmpty()) {
                Runnable runnable = (Runnable) tasks.get(0);
                tasks.remove(0);
                running = true;
                futureTask = executorService.submit(runnable);
                if (futureTask == null) {
                    count--;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeTask(Runnable runnable) {
        tasks.remove(runnable);
        return true;
    }

    @Override
    public boolean clearTaskQueue() {
        tasks.clear();
        return true;
    }
}
