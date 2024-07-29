package ru.telbot.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.telbot.utils.MessageUtils;

@Component
public class UpdateController {

    private static final Logger log = Logger.getLogger(UpdateController.class);

    private TelegramBot telegramBot;
    @Autowired
    private MessageUtils messageUtils;

    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Unable update bot");
            return;
        }

        if (update.getMessage() != null) {
            distributeMessagesByType(update);
        } else {
            log.error("Unsupported operation exception type");
        }
    }

    private void distributeMessagesByType(Update update) {
        Message message = update.getMessage();
        if (message.getText() != null) {
            processTextMessage(update);
        } else if (message.getPhoto() != null) {
            processPhotoMessage(update);
        } else if (message.getDocument() != null) {
            processDocumentMessage(update);
        } else {
            setUnsupportedMessageTypeView(update);
        }
    }

    private void processDocumentMessage(Update update) {
    }

    private void processPhotoMessage(Update update) {
    }

    private void processTextMessage(Update update) {
    }

    private void setUnsupportedMessageTypeView(Update update) {
        SendMessage sendMessage = messageUtils
                .generateSendMessageWithText(update, "Unsupported telegram type");
        setView(sendMessage);
    }

    private void setView(SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }
}
