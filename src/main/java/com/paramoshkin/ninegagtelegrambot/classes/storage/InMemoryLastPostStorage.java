package com.paramoshkin.ninegagtelegrambot.classes.storage;

import com.paramoshkin.ninegagapi.classes.models.Post;

import java.util.HashMap;

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
public class InMemoryLastPostStorage implements LastPostStorage {

    private HashMap<Long, Post> storage = new HashMap<Long, Post>();

    public void set(long chatId, Post lastPost) {
        storage.put(chatId, lastPost);
    }

    public Post get(long chatId) {
        return storage.get(chatId);
    }
}
