package com.geekglasses.tesseract.service;

import com.geekglasses.tesseract.task.PostCheckerRunnable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class PostNotificator {

    private static final long CHECK_PERIOD_SECONDS = 15;
    private final PostCheckerRunnable postCheckerRunnable;

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);

    public PostNotificator(PostCheckerRunnable postCheckerRunnable) {
        this.postCheckerRunnable = postCheckerRunnable;
        run();
    }

    public void run() {
        log.info("START CHECKING FOR NEW POSTS..");
        executorService.scheduleAtFixedRate(postCheckerRunnable, 0, CHECK_PERIOD_SECONDS, TimeUnit.SECONDS);
    }
}