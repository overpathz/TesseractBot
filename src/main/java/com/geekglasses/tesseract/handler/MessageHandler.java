package com.geekglasses.tesseract.handler;

import com.geekglasses.tesseract.entity.User;
import com.geekglasses.tesseract.entity.UserTag;
import com.geekglasses.tesseract.repo.UserRepo;
import com.geekglasses.tesseract.repo.UserTagRepo;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MessageHandler implements Handler<Message> {

    private final UserRepo userRepo;
    private final UserTagRepo userTagRepo;

    public MessageHandler(UserRepo userRepo, UserTagRepo userTagRepo) {
        this.userRepo = userRepo;
        this.userTagRepo = userTagRepo;
    }

    @Override
    public void choose(Message message) {
        if (message.hasText()) {
            String text = message.getText();

            if (text.equals("/start")) {
                User user = new User();
                user.setChatId(message.getChatId());
                user.setUsername(message.getChat().getUserName());
                userRepo.save(user);
            } else if (text.contains("/addtag")) {
                String desiredTag = text.split(" ")[1].trim();

                UserTag userTag = new UserTag();
                userTag.setChatId(message.getChatId());
                userTag.setTag(desiredTag);
                userTagRepo.save(userTag);
            }
        }
    }
}
