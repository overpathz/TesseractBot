package com.geekglasses.tesseract.app;

import com.geekglasses.tesseract.entity.TagLink;
import com.geekglasses.tesseract.entity.User;
import com.geekglasses.tesseract.repo.TagLinkRepo;
import com.geekglasses.tesseract.repo.UserRepo;
import com.geekglasses.tesseract.service.PostNotificator;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TesseractTelegramBot extends TelegramLongPollingBot {

    @Autowired
    private PostNotificator postNotificator;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TagLinkRepo tagRepo;

    @Value("${telegram.bot.username}")
    private String username;

    @Value("${telegram.bot.token}")
    private String token;

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            handler(update.getMessage());
//            SendMessage sendMessage = new SendMessage();
//            sendMessage.setChatId(String.valueOf(message.getChatId()));
//            sendMessage.setText(text);
//            execute(sendMessage);
        }
    }

    private void handler(Message message) {
        String text = message.getText();
        if (text.equals("/start")) {
            User user = new User();
            user.setChatId(message.getChatId());
            userRepo.save(user);
        } else if (text.contains("/addtag")) {
            String desiredTag = text.split(" ")[1].trim();
            System.out.println(desiredTag);
            TagLink tagLink = new TagLink();
            tagLink.setChatId(message.getChatId());
            tagLink.setTag(desiredTag);
            tagRepo.save(tagLink);
        }
    }
}

/*
    html, css, adaptive, flexbox, gridflex, вёрстка, верстка, стили
 */
