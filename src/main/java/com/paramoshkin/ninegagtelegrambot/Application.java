package com.paramoshkin.ninegagtelegrambot;

import com.paramoshkin.ninegagtelegrambot.classes.Bot;
import com.paramoshkin.ninegagtelegrambot.classes.BotConfig;
import com.paramoshkin.ninegagtelegrambot.classes.exceptions.InvalidBotConfigException;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import static sun.tools.javac.Main.EXIT_ERROR;

/*
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
public class Application {

    public static void main(String[] args) {

        // Initialize context
        ApiContextInitializer.init();

        // Create and parse bot config
        BotConfig config = new BotConfig();
        try {
            config.setParametersFromArgs(args);
        } catch (InvalidBotConfigException e) {
            handleException(e);
        }

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        Bot bot = new Bot(config);

        // Register bot
        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiRequestException e) {
            if (e.getErrorCode() == 404)
                handleException(new InvalidBotConfigException("Wrong bot username or API key"));
            else if (e.getErrorCode() == 401)
                handleException(new InvalidBotConfigException("Wrong API key in second argument"));
            else
                handleException(new InvalidBotConfigException(e.getMessage()));
        }
    }

    /**
     * Print exception message and exit
     *
     * @param exception - throed exception
     */
    private static void handleException(Exception exception) {
        System.out.println(exception.getMessage());
        System.exit(EXIT_ERROR);
    }

}
