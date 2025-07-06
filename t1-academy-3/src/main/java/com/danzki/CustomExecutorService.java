package com.danzki;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class CustomExecutorService {
    private final BlockingQueue<Runnable> taskQueue;
    private final List<Worker> workers;
    private volatile boolean isShutdown = false;

    public CustomExecutorService(int poolSize) {
        this.taskQueue = new LinkedBlockingQueue<>();
        this.workers = new LinkedList<>();

        for (int i = 0; i < poolSize; i++) {
            Worker worker = new Worker("Worker-" + (i + 1));
            worker.start();
            workers.add(worker);
        }
    }

    public void execute(Runnable task) {
        if (isShutdown) {
            throw new IllegalStateException("Executor has been shutdown");
        }
        boolean isOffered = taskQueue.offer(task);
        if (!isOffered) {
            System.out.println("Task is taked to run");
        }
    }

    public void shutdown() {
        isShutdown = true;
        for (Worker worker : workers) {
            worker.interrupt();
        }
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        long remainingNanos = unit.toNanos(timeout);
        final long endTime = System.nanoTime() + remainingNanos;

        for (Worker worker : workers) {
            if (remainingNanos <= 0L) {
                return false;
            }

            long millis = TimeUnit.NANOSECONDS.toMillis(remainingNanos);
            worker.join(millis > 0 ? millis : 1);

            remainingNanos = endTime - System.nanoTime();
        }

        return remainingNanos > 0;
    }

    private class Worker extends Thread {
        public Worker(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                while (!isShutdown || !taskQueue.isEmpty()) {
                    Runnable task;
                    if (isShutdown) {
                        task = taskQueue.poll(100, TimeUnit.MILLISECONDS);
                        if (task == null) break;
                    } else {
                        task = taskQueue.take();
                    }
                    task.run();
                }
            } catch (InterruptedException e) {
                System.out.println(getName() + " is interrupting");
            } finally {
                System.out.println(getName() + " is shutting down");
            }
        }
    }
}