package com.danzki;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class CustomExecutorService {
    private final List<Runnable> taskQueue = new LinkedList<>();
    private final List<Worker> workers = new LinkedList<>();
    private volatile boolean isShutdown = false;
    private final ReentrantLock putLock = new ReentrantLock();
    private final ReentrantLock pollLock = new ReentrantLock();

    public CustomExecutorService(int poolSize) {
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
        final ReentrantLock putLock = this.putLock;
        boolean isOffered;
        putLock.lock();
        try {
            isOffered = taskQueue.add(task);
        } finally {
            putLock.unlock();
        }
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
            final ReentrantLock runLock = pollLock;
            while (!isShutdown || !taskQueue.isEmpty() ) {
                Runnable task = null;
                runLock.lock();
                try {
                    if (!taskQueue.isEmpty()) {
                        task = taskQueue.removeFirst();
                    }
                } finally {
                    runLock.unlock();
                }

                if (task == null) {
                    if (isShutdown) break;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        break;
                    }
                    continue;
                }
                task.run();
            }
        }
    }
}