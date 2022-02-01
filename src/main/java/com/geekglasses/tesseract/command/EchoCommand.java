package com.geekglasses.tesseract.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class EchoCommand extends AbstractCommand {

    public void handle(Message message) {
        getMessageSender().sendMessage(SendMessage.builder()
                .chatId(String.valueOf(message.getChatId()))
                .text(message.getText())
                .build());
    }
}
