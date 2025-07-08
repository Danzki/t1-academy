package com.danzki;

import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("CustorExecutorService working...");
        CustomExecutorService customExecutor = new CustomExecutorService(3);
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            customExecutor.execute(() -> {
                System.out.println(Thread.currentThread().getName() +
                        " executing task " + taskId);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        customExecutor.shutdown();

        try {
            boolean terminated = customExecutor.awaitTermination(3, TimeUnit.SECONDS);
            if (terminated) {
                System.out.println("All tasks completed successfully");
            } else {
                System.out.println("Timeout reached, not all tasks completed");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("awaitTermination interrupted");
        }
    }

}
