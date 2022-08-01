package com.vccorp;

import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
public class SimpleBot extends TelegramLongPollingBot {
    private final RestTemplate restTemplate;

    public SimpleBot() {
        this.restTemplate = new RestTemplateBuilder().build();
    }

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
            String message = "*Hello " + firstName + "*";
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(EmojiParser.parseToUnicode(message + " :smile:"));
            sendMessage.setParseMode(ParseMode.MARKDOWNV2);

            try {
                executeAsync(sendMessage);
            } catch (TelegramApiException e) {
                log.error("Exception: {}", e.getMessage());
            }
        } else if (command.startsWith("/weather")) {
            String city = command.substring(9);
            try {
                String url = "https://vi.wttr.in/" + city + "?m?T?tqp0";
                HttpHeaders headers = new HttpHeaders();
                headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
                HttpEntity<?> entity = new HttpEntity<>(headers);
                ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText(Objects.requireNonNull(result.getBody()).substring(result.getBody().indexOf("<pre>") + 5, result.getBody().indexOf("</pre>")));
                sendMessage.setParseMode(ParseMode.HTML);

                executeAsync(sendMessage);
            } catch (Exception e) {
                log.error("Exception: {}", e.getMessage());
            }

        }

    }
}
