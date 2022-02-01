package com.geekglasses.tesseract.handler;

import com.geekglasses.tesseract.command.AddTagCommand;
import com.geekglasses.tesseract.command.EchoCommand;
import com.geekglasses.tesseract.command.DeleteTagCommand;
import com.geekglasses.tesseract.command.StartCommand;
import com.geekglasses.tesseract.util.BotCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public class MessageHandler implements Handler<Message> {

    private final AddTagCommand addTagCommand;
    private final StartCommand startCommand;
    private final EchoCommand echoCommand;
    private final DeleteTagCommand deleteTagCommand;

    public MessageHandler(AddTagCommand addTagCommand, StartCommand startCommand, EchoCommand echoCommand, DeleteTagCommand deleteTagCommand) {
        this.addTagCommand = addTagCommand;
        this.startCommand = startCommand;
        this.echoCommand = echoCommand;
        this.deleteTagCommand = deleteTagCommand;
    }

    @Override
    public void choose(Message message) {
        if (message.hasText()) {
            String text = message.getText();

            if (text.equals(BotCommands.START)) {
                startCommand.handle(message);
            } else if (text.contains(BotCommands.ADD_TAG)) {
                addTagCommand.handle(message);
            } else if(text.contains(BotCommands.DELETE_TAG)) {
                deleteTagCommand.handle(message);
            }
        }
    }
}
