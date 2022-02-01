package com.geekglasses.tesseract.command;

import com.geekglasses.tesseract.entity.User;
import com.geekglasses.tesseract.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class StartCommand implements Command {

    private final UserService userService;

    public StartCommand(UserService userService) {
        this.userService = userService;
    }

    public void handle(Message message) {
        Long chatId = message.getChatId();
        String userName = message.getChat().getUserName();
        User user = new User();
        user.setUsername(userName);
        user.setChatId(chatId);
        userService.save(user);
    }
}
