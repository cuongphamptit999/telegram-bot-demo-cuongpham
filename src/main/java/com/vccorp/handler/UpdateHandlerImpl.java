package com.vccorp.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;

import okhttp3.ResponseBody;

@Component
public class UpdateHandlerImpl implements UpdateHandler {

    private Logger LOG = LoggerFactory.getLogger(UpdateHandlerImpl.class);

    @Autowired
    private TelegramBot telegramBot;

    @Override
    public void handleUpdate(Update update) {
        Message message = update.message();

        Long chatId = message.chat().id();
        String text = message.text();

        String firstName = message.senderChat().firstName();

        LOG.debug("Chat id:" + chatId);
        LOG.debug("Text : " + text);
        LOG.debug("FirstName : " + firstName);

        if (text.startsWith("/hello")) {
            SendMessage sendMessage = new SendMessage(chatId, text.substring(1) + firstName);
            telegramBot.execute(sendMessage);
        }

    }

}
