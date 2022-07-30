package com.vccorp;

import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class SimpleBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "adtech_cp_demo_bot";
    }

    @Override
    public String getBotToken() {
        return "5544928247:AAG-zD5kmpy6W2_dxBt9f0jcSqDHSQK0Zy4";
    }

    @Override
    public void onUpdateReceived(Update update) {
        String command = update.getMessage().getText();
        log.info("Command {}", command);
        String firstName = update.getMessage().getFrom().getFirstName();
        long chatId = update.getMessage().getChatId();

        if (command.equals("/hello")) {
            String message = "*Hello " + firstName+"*";
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(EmojiParser.parseToUnicode(message+" :smile:"));
            sendMessage.setParseMode(ParseMode.MARKDOWNV2);

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }
}
