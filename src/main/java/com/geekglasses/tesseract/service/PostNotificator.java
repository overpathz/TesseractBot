package com.geekglasses.tesseract.service;

import com.geekglasses.tesseract.entity.UserTag;
import com.geekglasses.tesseract.model.Article;
import com.geekglasses.tesseract.repo.UserTagRepo;
import com.geekglasses.tesseract.sender.MessageSender;
import com.geekglasses.tesseract.util.HabrParser;
import com.geekglasses.tesseract.util.TestTesseractRestService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class PostNotificator {

    private final HabrParser habrParser;
    private final TestTesseractRestService testParser;
    private final MessageSender messageSender;
    private final UserTagRepo userTagRepo;

    private static final long checkPeriod = 15;

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);

    public PostNotificator(HabrParser habrParser, TestTesseractRestService testParser, MessageSender messageSender, UserTagRepo userTagRepo) {
        this.habrParser = habrParser;
        this.testParser = testParser;
        this.messageSender = messageSender;
        this.userTagRepo = userTagRepo;
        run();
    }

    public void run() {
        executorService.scheduleAtFixedRate(new NewPostChecker(),
                0, checkPeriod, TimeUnit.SECONDS);
    }

    class NewPostChecker implements Runnable {
        @Override
        public void run() {
            if (testParser.isNewPost()) {
                System.out.println("new");
                List<UserTag> linkedUserTags = userTagRepo.findAll();
                Map<Long, List<String>> map = new HashMap<>();

                linkedUserTags.stream().map(UserTag::getChatId).forEach(x->{
                    List<String> tags = new ArrayList<>();
                    linkedUserTags.stream().filter(y->y.getChatId().equals(x)).forEach(y->tags.add(y.getTag()));
                    map.put(x, tags);
                });

                Article article = testParser.getArticle();
                List<String> articleTags = article.getTags();

                map.forEach((key, value) -> {
                    List<String> tempTags = new ArrayList<>(articleTags);
                    value.retainAll(tempTags);
                    if (!value.isEmpty()) {
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(String.valueOf(key));
                        sendMessage.setText(article.getUrl());
                        messageSender.sendMessage(sendMessage);
                    }
                });

            } else {
                System.out.println("no posts");
            }
        }
    }
}

/*
    tags = [java, python, c#]

    364479408  java
    364479408  python
    364479408  science
    364479408  наука
 */
