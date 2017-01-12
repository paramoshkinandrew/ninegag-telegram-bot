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

import com.paramoshkin.ninegagtelegrambot.classes.exceptions.InvalidBotConfigException;

public class BotConfig {

    private String botName;
    private String botToken;

    public BotConfig() {
    }

    public BotConfig(String botName, String botToken) {
        this.botName = botName;
        this.botToken = botToken;
    }

    /**
     * Setting bot name and bot token from entry point arguments
     *
     * @param args - array of string arguments
     * @throws InvalidBotConfigException - invalid config. Unexpected count of arguments in this case
     */
    public void setParametersFromArgs(String[] args) throws InvalidBotConfigException {
        if (args.length != 2)
            throw new InvalidBotConfigException("Unexpected count of arguments: " + args.length + " received but 2 expected. First argument is a bot name, second - bot token.");
        this.botName = args[0];
        this.botToken = args[1];
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }
}
