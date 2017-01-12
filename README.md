9GAG Telegram bot
========

This telegram bot is made for sending a random image or video from 9gag.com site.
You can find implementation on https://telegram.me/ninegag_unofficial_bot

Run
--
* Download latest release or build package from source
* Register your bot with [BotFather](https://telegram.me/BotFather) to get bot username and API key
* Run project with two arguments: bot username as first argument and bot API key as second
```sh
$ java -jar ninegagatelegrambot.jar BOTUSERNAME BOTAPIKEY
```

Project has no logs except thrown exceptions.

Docker
--
You can use docker image on [DockerHub](https://hub.docker.com/r/paramoshkina/ninegagtelegrambot) for setting up your bot implementation.  
Docker image requires two environment variables:
 - `BOT_USERNAME` as your bot username
 - `BOT_API_TOKEN` as your bot API key

Run your docker container as daemon with
```sh
docker run -e BOT_USERNAME={USERNAME} -e BOT_API_TOKEN={API_TOKEN} --name ninegagbot -d paramoshkina/ninegagtelegrambot:latest
```

Features
--
* Reply with random image/video to any message
* Reply with next post to next message
* Reply with basic last post information to info message
* Reply with link to last post to link message
* Reply with mock messages to /start, /help, /stop commands
* Custom keyboard for easy use

