package com.geekglasses.tesseract.command;

import com.geekglasses.tesseract.sender.MessageSender;
import com.geekglasses.tesseract.util.MessageParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public abstract class AbstractCommand {
    protected MessageSender messageSender;
    protected MessageParser messageParser;

    public abstract void handle(Message message);

    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Autowired
    public void setMessageParser(MessageParser messageParser) {
        this.messageParser = messageParser;
    }
}
