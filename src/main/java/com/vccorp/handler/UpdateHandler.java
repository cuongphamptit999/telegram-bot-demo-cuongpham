package com.vccorp.handler;

import com.pengrad.telegrambot.model.Update;

public interface UpdateHandler {

	void handleUpdate(Update update);
}