package com.vccorp;

import com.vccorp.translate.TranslationApi;
import com.vccorp.weather.DmiApi;
import org.springframework.context.annotation.Bean;

import com.pengrad.telegrambot.TelegramBot;

@org.springframework.context.annotation.Configuration
public class Configuration {

	@Bean
	public TelegramBot telegramBot(BotProperties botProperties) {
		return new TelegramBot(botProperties.getApiKey());
	}

}
