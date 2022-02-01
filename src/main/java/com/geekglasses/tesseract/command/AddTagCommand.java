package com.geekglasses.tesseract.command;

import com.geekglasses.tesseract.entity.UserTag;
import com.geekglasses.tesseract.service.UserTagService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class AddTagCommand extends AbstractCommand {

    private final UserTagService userTagService;

    public AddTagCommand(UserTagService userTagService) {
        this.userTagService = userTagService;
    }

    public void handle(Message message) {
        String text = message.getText();
        Long chatId = message.getChatId();

        if (userTagService.isLessThanFive(chatId)) {
            String desiredTag = text.split(" ")[1].trim();
            UserTag userTag = new UserTag();
            userTag.setChatId(chatId);
            userTag.setTag(desiredTag);
            userTagService.save(userTag);
        } else {
            messageSender.sendMessage(SendMessage
                    .builder()
                    .chatId(String.valueOf(chatId))
                    .text("You have added than 5 tags!")
                    .build());
        }
    }
}
