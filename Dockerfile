FROM java:openjdk-8-jdk-alpine
MAINTAINER Andrew Paramoshkin <paramoshkin.andrew@gmail.com>
ADD ./target/ninegagtelegrambot-*-jar-with-dependencies.jar /app.jar
WORKDIR /
ENV BOT_USERNAME=""
ENV BOT_API_TOKEN=""
CMD java -jar /app.jar ${BOT_USERNAME} ${BOT_API_TOKEN}