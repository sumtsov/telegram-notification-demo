# Telegram Notification Demo
Sample project that demonstrates how to send notifications to a Telegram group using Java and Telegram API

## How to run 
1. To run application with your own bot adjust application.yml file:
```
app:
  telegram:
    chat-id: [your chat id]
    bot:
      token: [your bot token]
      url: https://api.telegram.org/bot
```
2. Build the project: `mvn clean install`
3. Run the .jar: `java -jar telegram-notification-demo-${version}.jar`

## Usage
Send notification:
```
curl -X 'POST' \
  'http://localhost:8080/telegram/send' \
  -H 'accept: */*' \
  -d ''
```

## API Reference
- swagger-ui: `http://localhost:8080/swagger-ui/index.html`