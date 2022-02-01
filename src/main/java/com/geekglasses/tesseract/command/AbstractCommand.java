package com.geekglasses.tesseract.command;

import com.geekglasses.tesseract.sender.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public abstract class AbstractCommand {
    private MessageSender messageSender;

    public abstract void handle(Message message);

    protected MessageSender getMessageSender() {
        return messageSender;
    }

    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }
}
