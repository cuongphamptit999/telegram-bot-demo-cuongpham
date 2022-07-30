package com.vccorp;

import java.util.List;

import com.vccorp.handler.UpdateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.GetUpdatesResponse;


@RestController
public class BotController {

	@Autowired
	private UpdateHandler handler;
	
	@Autowired
	private TelegramBot bot;
	
	@RequestMapping("/poll")
	public void poll() {

		GetUpdates getUpdates = new GetUpdates().limit(100).offset(0).timeout(0);
		GetUpdatesResponse getUpdatesResponse = bot.execute(getUpdates);

		List<Update> updates = getUpdatesResponse.updates();
		updates.forEach(this::webhook);
	}

	@RequestMapping(value = "/webhook", method = RequestMethod.POST)
	public void webhook(@RequestBody Update update) {
		handler.handleUpdate(update);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String healthyCheck(){
		return "Hello world, I'm Cuong Pham";
	}
}
