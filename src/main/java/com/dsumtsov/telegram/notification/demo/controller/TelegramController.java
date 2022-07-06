package com.dsumtsov.telegram.notification.demo.controller;

import com.dsumtsov.telegram.notification.demo.domain.Document;
import com.dsumtsov.telegram.notification.demo.service.ReportService;
import com.dsumtsov.telegram.notification.demo.service.TelegramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/telegram")
@Tag(name = "telegram notification", description = "Telegram Notification API")
public class TelegramController {

    private final TelegramService telegramService;
    private final ReportService reportService;

    @PostMapping("/send")
    @Operation(summary = "Send sample notification to a Telegram group")
    public void sendNotification() {
        log.info("Request: send notification to a Telegram group");
        Document document = reportService.getReport();
        telegramService.sendMessageWithDocument(document);
    }
}
