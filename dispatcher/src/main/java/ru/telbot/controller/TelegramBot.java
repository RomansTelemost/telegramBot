package ru.telbot.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Component
//@PropertySource(value = "classpath:/dispatcher.application.properties")
public class TelegramBot extends TelegramLongPollingBot {

    private static final Logger log = Logger.getLogger(TelegramBot.class);
//    @Value("${bot.name}")
    private String botName = "kodi_meverik_bot";
//    @Value("${bot.token}")
    private String botToken = "6668761210:AAG2cF5-Yv96k_5ryq1AmUKlUOcbsp62xsM";
    @Autowired
    private UpdateController updateController;

//    public TelegramBot(UpdateController updateController) {
//        this.updateController = updateController;
//    }

    @PostConstruct
    public void init() {
        updateController.registerBot(this);
    }

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
