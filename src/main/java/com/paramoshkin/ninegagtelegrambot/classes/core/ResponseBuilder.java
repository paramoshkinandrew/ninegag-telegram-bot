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

import com.paramoshkin.ninegagapi.NineGagApi;
import com.paramoshkin.ninegagapi.classes.enums.ImageType;
import com.paramoshkin.ninegagapi.classes.enums.VideoType;
import com.paramoshkin.ninegagapi.classes.exceptions.NineGagApiException;
import com.paramoshkin.ninegagapi.classes.models.Post;
import com.paramoshkin.ninegagtelegrambot.classes.storage.InMemoryLastPostStorage;
import com.paramoshkin.ninegagtelegrambot.classes.storage.LastPostStorage;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.send.SendVideo;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class ResponseBuilder {

    private final AbsSender bot;
    private final MessageParser messageParser;
    private final NineGagApi nineGagApi;
    private final LastPostStorage lastPostStorage;

    public ResponseBuilder(AbsSender bot) {
        this.messageParser = new MessageParser();
        this.nineGagApi = new NineGagApi();
        this.lastPostStorage = new InMemoryLastPostStorage();
        this.bot = bot;
    }

    public void answer(Update update) throws TelegramApiException {
        answer(update, messageParser.defineCommandType(update));
    }

    public void answer(Update update, CommandType commandType) throws TelegramApiException {
        answer(update, commandType, null);
    }

    // TODO Refactor that method and message builders
    public void answer(Update update, CommandType commandType, String id) throws TelegramApiException {
        Post lastPost;
        switch (commandType) {
            case START:
                bot.sendMessage(buildStartMessage(update.getMessage().getChatId()));
                return;
            case HELP:
                bot.sendMessage(buildHelpMessage(update.getMessage().getChatId()));
                return;
            case STOP:
                bot.sendMessage(buildStopMessage(update.getMessage().getChatId()));
                return;
            case NEXT:
                lastPost = lastPostStorage.get(update.getMessage().getChatId());
                if (lastPost == null) {
                    answer(update, CommandType.DEFAULT);
                } else {
                    answer(update, CommandType.DEFAULT, lastPost.getNextId());
                }
                return;
            case INFO:
                lastPost = lastPostStorage.get(update.getMessage().getChatId());
                if (lastPost == null) {
                    answer(update, CommandType.DEFAULT);
                } else {
                    bot.sendMessage(buildInfoMessage(update.getMessage().getChatId(), lastPost));
                }
                return;
            case LINK:
                lastPost = lastPostStorage.get(update.getMessage().getChatId());
                if (lastPost == null) {
                    answer(update, CommandType.DEFAULT);
                } else {
                    bot.sendMessage(buildLinkMessage(update.getMessage().getChatId(), lastPost));
                }
                return;
            case DEFAULT:
                // Answer with random 9Gag Post
                try {
                    Post post;
                    if (id == null)
                        post = nineGagApi.getRandomPost();
                    else
                        post = nineGagApi.getPost(id);
                    lastPostStorage.set(update.getMessage().getChatId(), post);
                    switch (post.getPostType()) {
                        case IMAGE:
                            bot.sendPhoto(buildPhotoMessage(post, update.getMessage().getChatId()));
                            break;
                        case VIDEO:
                            bot.sendVideo(buildVideoMessage(post, update.getMessage().getChatId()));
                            break;
                    }
                    return;
                } catch (NineGagApiException e) {
                    answer(update, CommandType.FALLBACK);
                    return;
                }
            case FALLBACK:
                bot.sendMessage(buildFallbackMessage(update.getMessage().getChatId()));
                return;
            default:
                answer(update, CommandType.DEFAULT);
        }


    }

    private SendMessage buildLinkMessage(Long chatId, Post lastPost) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(lastPost.getLink());
        message.setReplyMarkup(getKeyboard(lastPost));
        return message;
    }

    private SendMessage buildInfoMessage(Long chatId, Post lastPost) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Post info:\nID: " + lastPost.getId() + "\nComments: " + lastPost.getComments() + "\nVotes: " + lastPost.getVotes());
        message.setReplyMarkup(getKeyboard(lastPost));
        return message;
    }

    /**
     * Build video response message
     *
     * @param post   - 9GAG post with video
     * @param chatId - chat id for response
     * @return - completed response message
     */
    private SendVideo buildVideoMessage(Post post, Long chatId) {
        // TODO Fallback if MP4 not exists
        SendVideo message = new SendVideo();
        message.setChatId(chatId);
        message.setCaption(post.getTitle());
        message.setVideo(post.getVideoUrls().get(VideoType.MP4));
        message.setReplyMarkup(getKeyboard(post));
        return message;
    }

    /**
     * Build image response message
     *
     * @param post   - 9GAG post with image
     * @param chatId - chat id for response
     * @return - completed response message
     */
    private SendPhoto buildPhotoMessage(Post post, Long chatId) {
        // TODO Fallback if LARGE not exists
        SendPhoto message = new SendPhoto();
        message.setChatId(chatId);
        message.setCaption(post.getTitle());
        message.setPhoto(post.getImageUrls().get(ImageType.LARGE));
        message.setReplyMarkup(getKeyboard(post));
        return message;
    }

    /**
     * Build fallback response message
     *
     * @param chatId - chat id for response
     * @return - completed response message
     */
    private SendMessage buildFallbackMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Fallback message");
        message.setReplyMarkup(getKeyboard());
        return message;
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
        message.setReplyMarkup(getKeyboard());
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
        message.setReplyMarkup(getKeyboard());
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
        message.setReplyMarkup(getKeyboard());
        return message;
    }

    private ReplyKeyboardMarkup getKeyboard() {
        return getKeyboard(null);
    }

    private ReplyKeyboardMarkup getKeyboard(Post post) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<KeyboardRow>();
        // Row for all cases
        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add("Random \uD83D\uDC4B");
        keyboardRows.add(firstRow);
        // Optional row
        if (post != null) {
            KeyboardRow secondRow = new KeyboardRow();
            secondRow.add(StringUtils.capitalize(CommandType.NEXT.toString().toLowerCase()));
            secondRow.add(StringUtils.capitalize(CommandType.INFO.toString().toLowerCase()));
            secondRow.add(StringUtils.capitalize(CommandType.LINK.toString().toLowerCase()));
            keyboardRows.add(secondRow);
        }
        keyboardMarkup.setKeyboard(keyboardRows);
        return keyboardMarkup;
    }
}
