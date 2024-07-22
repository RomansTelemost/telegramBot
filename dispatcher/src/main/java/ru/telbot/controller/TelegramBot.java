package ru.telbot.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    String botName;

    @Value("${bot.token}")
    String botToken;

    private static final Logger log = Logger.getLogger(TelegramBot.class);

    public TelegramBot() {
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message origMessage = update.getMessage();
        log.debug(origMessage.getText());

        SendMessage response = new SendMessage();
        response.setChatId(origMessage.getChatId().toString());
        response.setText("Hello from bot");
        sendAnswerMessage(response);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    public void sendAnswerMessage(SendMessage sendMessage) {
        if (sendMessage != null) {
            try {
                execute(sendMessage);
            } catch (TelegramApiException ex) {
                log.error(ex.getMessage());
            }
        }
    }
}
