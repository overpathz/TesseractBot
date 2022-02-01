package com.geekglasses.tesseract.handler;

import com.geekglasses.tesseract.entity.User;
import com.geekglasses.tesseract.entity.UserTag;
import com.geekglasses.tesseract.service.UserService;
import com.geekglasses.tesseract.service.UserTagService;
import com.geekglasses.tesseract.util.BotCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Slf4j
public class MessageHandler implements Handler<Message> {

    private final UserService userService;
    private final UserTagService userTagService;

    public MessageHandler(UserService userService, UserTagService userTagService) {
        this.userService = userService;
        this.userTagService = userTagService;
    }

    @Override
    public void choose(Message message) {
        if (message.hasText()) {
            String text = message.getText();

            if (text.equals(BotCommands.START)) {
                User user = new User();
                user.setChatId(message.getChatId());
                user.setUsername(message.getChat().getUserName());
                userService.save(user);
            } else if (text.contains(BotCommands.ADD_TAG)) {
                String desiredTag = text.split(" ")[1].trim();

                UserTag userTag = new UserTag();
                userTag.setChatId(message.getChatId());
                userTag.setTag(desiredTag);
                userTagService.save(userTag);
            }
        }
    }
}
