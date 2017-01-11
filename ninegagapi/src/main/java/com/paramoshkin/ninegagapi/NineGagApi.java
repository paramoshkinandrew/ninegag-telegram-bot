package com.paramoshkin.ninegagapi;
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

import com.paramoshkin.ninegagapi.classes.DocumentParser;
import com.paramoshkin.ninegagapi.classes.exceptions.NineGagApiException;
import com.paramoshkin.ninegagapi.classes.models.Post;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class NineGagApi {
    private static final String RANDOM_PATH = "http://9gag.com/random";
    private static final String POST_PATH_TEMPLATE = "http://9gag.com/gag/%s";

    private final DocumentParser documentParser;

    public NineGagApi() {
        documentParser = new DocumentParser();
    }

    public Post getRandomPost() throws NineGagApiException {
        Document doc;
        try {
            doc = Jsoup.connect(RANDOM_PATH).get();
        } catch (IOException e) {
            throw new NineGagApiException(e.getMessage());
        }
        return documentParser.parseOnePost(doc);
    }

    public Post getPost(String id) throws NineGagApiException {
        Document doc;
        try {
            doc = Jsoup.connect(String.format(POST_PATH_TEMPLATE, id)).get();
        } catch (IOException e) {
            throw new NineGagApiException(e.getMessage());
        }
        return documentParser.parseOnePost(doc);
    }
}
