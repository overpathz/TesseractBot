package com.geekglasses.tesseract.handler;

import com.geekglasses.tesseract.entity.TagLink;
import com.geekglasses.tesseract.entity.User;
import com.geekglasses.tesseract.repo.TagLinkRepo;
import com.geekglasses.tesseract.repo.UserRepo;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MessageHandler implements Handler<Message> {

    private final UserRepo userRepo;
    private final TagLinkRepo tagLinkRepo;

    public MessageHandler(UserRepo userRepo, TagLinkRepo tagLinkRepo) {
        this.userRepo = userRepo;
        this.tagLinkRepo = tagLinkRepo;
    }

    @Override
    public void choose(Message message) {
        if (message.hasText()) {
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
                tagLinkRepo.save(tagLink);
            }
        }
    }
}
