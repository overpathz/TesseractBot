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

    @Override
    public void handle(Message message) {
        Long chatId = message.getChatId();
        String text = messageParser.getCmdText(message);

        if (userTagService.isLessThanFive(chatId)) {
            if (userTagService.getAllByTag(chatId, text).isEmpty()) {
                addTag(message, chatId);
            } else {
                messageSender.sendMessage(SendMessage
                        .builder()
                        .chatId(String.valueOf(chatId))
                        .text("Such tag is already exists")
                        .build());
            }
        } else {
            messageSender.sendMessage(SendMessage
                    .builder()
                    .chatId(String.valueOf(chatId))
                    .text("You have added more than 5 tags!")
                    .build());
        }
    }

    private void addTag(Message message, Long chatId) {
        String desiredTag = messageParser.getCmdText(message);
        UserTag userTag = new UserTag();
        userTag.setChatId(chatId);
        userTag.setTag(desiredTag);
        userTagService.save(userTag);
    }
}
