package com.geekglasses.tesseract.service;

import com.geekglasses.tesseract.entity.TagLink;
import com.geekglasses.tesseract.model.Article;
import com.geekglasses.tesseract.repo.TagLinkRepo;
import com.geekglasses.tesseract.sender.MessageSender;
import com.geekglasses.tesseract.util.HabrParser;
import com.geekglasses.tesseract.util.TestTesseractRestService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class PostNotificator {

    private final TagLinkRepo tagLinkRepo;
    private final HabrParser habrParser;
    private final TestTesseractRestService testParser;
    private final MessageSender messageSender;

    private static final long checkPeriod = 5;

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);

    public PostNotificator(TagLinkRepo tagLinkRepo, HabrParser habrParser, TestTesseractRestService testParser, MessageSender messageSender) {
        this.tagLinkRepo = tagLinkRepo;
        this.habrParser = habrParser;
        this.testParser = testParser;
        this.messageSender = messageSender;
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
                Article article = testParser.getArticle();
                List<String> tags = article.getTags();

                for (String tag: tags) {
                    List<TagLink> allByTag = tagLinkRepo.findAllByTag(tag);
                    allByTag.stream().filter(x->x.getTag().equals(tag)).forEach(x->{
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(String.valueOf(x.getChatId()));
                        sendMessage.setText(article.getUrl());
                        messageSender.sendMessage(sendMessage);
                        System.out.println("--------------------------------");
                    });
                }
            } else {
                System.out.println("There is no new articles(");
            }
        }
    }
}
