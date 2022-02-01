package com.geekglasses.tesseract.task;

import com.geekglasses.tesseract.entity.UserTag;
import com.geekglasses.tesseract.model.Article;
import com.geekglasses.tesseract.sender.MessageSender;
import com.geekglasses.tesseract.service.UserTagService;
import com.geekglasses.tesseract.util.HabrParser;
import com.geekglasses.tesseract.util.TestTesseractRestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class PostCheckerRunnable implements Runnable {

    private final HabrParser habrParser;
    private final TestTesseractRestService testParser;
    private final MessageSender messageSender;
    private final UserTagService userTagService;

    private final ExecutorService exService;

    public PostCheckerRunnable(HabrParser habrParser, TestTesseractRestService testParser,
                               MessageSender messageSender, UserTagService userTagService) {
        this.habrParser = habrParser;
        this.testParser = testParser;
        this.messageSender = messageSender;
        this.userTagService = userTagService;
        this.exService = Executors.newFixedThreadPool(10);
    }

    @Override
    public void run() {
        if (testParser.isNewPost()) {
            log.info("Pinging Habr.. -- New post!");
            exService.submit(this::notifyUsersAboutNewPost);
        } else {
            log.info("Pinging Habr.. -- No new posts");
        }
    }

    public void notifyUsersAboutNewPost() {
        List<UserTag> linkedUserTags = userTagService.getAll();
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
                messageSender.sendMessage(SendMessage.builder()
                        .chatId(String.valueOf(key))
                        .text(article.getUrl())
                        .build());
            }
        });
    }
}