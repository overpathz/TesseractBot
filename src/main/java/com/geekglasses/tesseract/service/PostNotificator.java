package com.geekglasses.tesseract.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class PostNotificator {
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);

    public void run() {
        executorService.scheduleAtFixedRate(()-> System.out.println("Hello"), 1, 5, TimeUnit.SECONDS);
    }
}
