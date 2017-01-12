package com.paramoshkin.ninegagapi.classes.common;

import com.paramoshkin.ninegagapi.classes.enums.ImageType;
import com.paramoshkin.ninegagapi.classes.enums.VideoType;
import org.jsoup.nodes.Element;

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
public class UrlGenerator {
    /**
     * Generating HashMap with video types
     *
     * @param videoElement - video node
     * @return - HashMap with video urls
     */
    public HashMap<VideoType, String> generateVideoUrls(Element videoElement) {
        HashMap<VideoType, String> out = new HashMap<VideoType, String>();
        for (VideoType type : VideoType.values()) {
            Element sourceElement = videoElement.select(String.format("source[type=%s]", type.toString())).first();
            String videoUrl = "";
            if (sourceElement != null)
                videoUrl = sourceElement.attr("src");
            out.put(type, videoUrl);
        }
        return out;
    }

    /**
     * Generating HashMap with different types of images
     *
     * @param imageElement - image node
     * @return - HashMap with images
     */
    public HashMap<ImageType, String> generateImageUrls(Element imageElement) {
        HashMap<ImageType, String> out = new HashMap<ImageType, String>();
        String initialUrl = imageElement.attr("src");
        // Define initial type
        ImageType initialType = null;
        for (ImageType type : ImageType.values()) {
            if (initialUrl.contains(type.toString())) {
                initialType = type;
                break;
            }
        }
        for (ImageType type : ImageType.values()) {
            if (initialType == null) {
                out.put(type, initialUrl);
            } else {
                if (type != initialType) {
                    int index = initialUrl.lastIndexOf(initialType.toString());
                    out.put(type, new StringBuilder(initialUrl)
                            .replace(index, index + initialType.toString().length(), type.toString())
                            .toString());
                } else
                    out.put(type, initialUrl);
            }
        }
        return out;
    }
}
