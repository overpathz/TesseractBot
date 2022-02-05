package com.geekglasses.tesseract.command;

import com.geekglasses.tesseract.service.UserTagService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class DeleteTagCommand extends AbstractCommand {

    private final UserTagService userTagService;

    public DeleteTagCommand(UserTagService userTagService) {
        this.userTagService = userTagService;
    }

    @Override
    public void handle(Message message) {
        userTagService.delete(
                userTagService.findByChatIdAndTag(message.getChatId(),
                        messageParser.getCmdText(message))
        );
    }
}
