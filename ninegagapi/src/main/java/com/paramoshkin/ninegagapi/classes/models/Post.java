package com.paramoshkin.ninegagapi.classes.models;

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

import com.paramoshkin.ninegagapi.classes.enums.ImageType;
import com.paramoshkin.ninegagapi.classes.enums.PostType;
import com.paramoshkin.ninegagapi.classes.enums.VideoType;

import java.util.HashMap;

public class Post {

    private String id;
    private String title;
    private String link;
    private HashMap<ImageType, String> imageUrls;
    private HashMap<VideoType, String> videoUrls;
    private PostType postType;
    private String nextId;
    private int votes;
    private int comments;

    public Post(String id, String title, String link, HashMap<ImageType, String> imageUrls, HashMap<VideoType, String> videoUrls, PostType postType, String nextId, int votes, int comments) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.imageUrls = imageUrls;
        this.videoUrls = videoUrls;
        this.postType = postType;
        this.nextId = nextId;
        this.votes = votes;
        this.comments = comments;
    }
}
