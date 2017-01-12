package com.paramoshkin.ninegagtelegrambot.classes;/*
 * Copyright 2017 Andrew Paramoshkin <paramoshkin.andrew@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.paramoshkin.ninegagtelegrambot.classes.core.ResponseBuilder;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    private final BotConfig config;
    private final ResponseBuilder responseBuilder;

    public Bot(BotConfig config) {
        super();
        this.config = config;
        responseBuilder = new ResponseBuilder(this);
    }

    public Bot(BotConfig config, DefaultBotOptions options) {
        super(options);
        this.config = config;
        responseBuilder = new ResponseBuilder(this);
    }

    /**
     * Answer message on received
     *
     * @param update - message
     */
    public void onUpdateReceived(Update update) {
        try {
            responseBuilder.answer(update);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return config.getBotName();
    }

    public String getBotToken() {
        return config.getBotToken();
    }
}
