package com.paramoshkin.ninegagtelegrambot.classes.core;/*
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

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class ResponseBuilder {

    private final AbsSender bot;
    private final MessageParser messageParser;

    public ResponseBuilder(AbsSender bot) {
        this.messageParser = new MessageParser();
        this.bot = bot;
    }

    public void answer(Update update) throws TelegramApiException {
        CommandType commandType = messageParser.defineCommandType(update);
        switch (commandType) {
            case START:
                bot.sendMessage(buildStartMessage(update.getMessage().getChatId()));
                break;
            case HELP:
                bot.sendMessage(buildHelpMessage(update.getMessage().getChatId()));
                break;
            case STOP:
                bot.sendMessage(buildStopMessage(update.getMessage().getChatId()));
                break;
            case DEFAULT:
                // TODO Implement
        }
    }

    /**
     * Build stop response message
     *
     * @param chatId - chat id for response
     * @return - completed response message
     */
    private SendMessage buildStopMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Stop message");
        return message;
    }

    /**
     * Build help response message
     *
     * @param chatId - chat id for response
     * @return - completed response message
     */
    private SendMessage buildHelpMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Help message");
        return message;
    }

    /**
     * Building start response message
     *
     * @param chatId - chat id for response
     * @return - completed response message
     */
    private SendMessage buildStartMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Start message");
        return message;
    }
}
