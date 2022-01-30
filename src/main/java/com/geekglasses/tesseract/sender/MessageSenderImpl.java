package com.geekglasses.tesseract.sender;

import com.geekglasses.tesseract.app.TesseractTelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class MessageSenderImpl implements MessageSender {

    private TesseractTelegramBot tgBot;

    @Override
    public void sendMessage(SendMessage sendMessage) {
        try {
            tgBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public void setMyTelegramBot(TesseractTelegramBot tgBot) {
        this.tgBot = tgBot;
    }

}
