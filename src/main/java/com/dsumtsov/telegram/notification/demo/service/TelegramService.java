package com.dsumtsov.telegram.notification.demo.service;

import com.dsumtsov.telegram.notification.demo.domain.Document;

public interface TelegramService {

    void sendMessageWithDocument(Document document);
}
