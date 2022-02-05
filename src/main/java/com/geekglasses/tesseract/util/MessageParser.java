package com.geekglasses.tesseract.util;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.*;

@Component
public class MessageParser {
    private MessageParser() {}

    public String getCmdText(Message message) {
        String text = message.getText();
        String[] splitMsg = text.split(" ");
        if (splitMsg.length > 1) {
            ArrayList<String> arrayList = Lists.newArrayList(text.trim()
                    .replaceAll("( +)"," ")
                    .trim()
                    .split(" "));
            arrayList.remove(arrayList.get(0));
            return String.join(" ", arrayList);
        }
        return null;
    }
}
