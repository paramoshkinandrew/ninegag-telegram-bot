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

import org.telegram.telegrambots.api.objects.Update;

class MessageParser {

    /**
     * Define message command type or return default type
     *
     * @param update - Update instance
     * @return - message type
     */
    CommandType defineCommandType(Update update) {
        if (update.getMessage().getText() == null)
            return CommandType.DEFAULT;
        String[] parsed = update.getMessage().getText().split("\\s");
        if (parsed.length > 0)
            return CommandType.fromString(parsed[0]);
        return CommandType.DEFAULT;
    }
}
