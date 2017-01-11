package com.paramoshkin.ninegagapi.classes;

import com.paramoshkin.ninegagapi.classes.common.UrlGenerator;
import com.paramoshkin.ninegagapi.classes.enums.ImageType;
import com.paramoshkin.ninegagapi.classes.enums.PostType;
import com.paramoshkin.ninegagapi.classes.enums.VideoType;
import com.paramoshkin.ninegagapi.classes.exceptions.NineGagApiException;
import com.paramoshkin.ninegagapi.classes.models.Post;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

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
public class DocumentParser {
    private static final String POST_PATH_TEMPLATE = "http://9gag.com/gag/%s";

    private final UrlGenerator urlGenerator;

    public DocumentParser() {
        urlGenerator = new UrlGenerator();
    }

    public Post parseOnePost(Document doc) throws NineGagApiException {
        // Parameters
        String title = "";
        PostType postType = PostType.IMAGE;
        String id = "";
        // Setting basic content
        Element contentContainer = doc.getElementsByClass("badge-entry-container").first();
        HashMap<ImageType, String> imageUrls = new HashMap<ImageType, String>();
        HashMap<VideoType, String> videoUrls = new HashMap<VideoType, String>();
        int votes, comments;
        String nextId = "";
        if (contentContainer != null) {
            // Setting title
            Element titleElement = contentContainer.getElementsByClass("badge-item-title").first();
            if (titleElement != null) {
                title = titleElement.text();
            }
            // Image element
            Element imageElement = contentContainer.getElementsByClass("badge-item-img").first();
            // Video element
            Element videoElement = contentContainer.getElementsByTag("video").first();
            // Setting up video
            if (videoElement != null) {
                imageElement = new Element(Tag.valueOf("img"), "");
                imageElement.attr("src", videoElement.attr("poster"));
                videoUrls = urlGenerator.generateVideoUrls(videoElement);
                postType = PostType.VIDEO;
            }
            // Setting up images
            if (imageElement != null) {
                imageUrls = urlGenerator.generateImageUrls(imageElement);
            }
            id = contentContainer.attr("data-entry-id");
            votes = Integer.parseInt(contentContainer.attr("data-entry-votes"));
            comments = Integer.parseInt(contentContainer.attr("data-entry-comments"));
            nextId = getNextId(doc);

        } else {
            throw new NineGagApiException("9GAG post parsing error");
        }
        return new Post(id, title, String.format(POST_PATH_TEMPLATE, id), imageUrls, videoUrls, postType, nextId, votes, comments);
    }

    /**
     * Parse next post ID
     *
     * @param doc - Element instance
     * @return - next post ID
     */
    private static String getNextId(Element doc) {
        Element nextPostIdElement = doc.select(".badge-fast-entry.badge-next-post-entry.next").first();
        if (nextPostIdElement != null)
            return nextPostIdElement.attr("data-entry-key");
        else
            return null;
    }

}
