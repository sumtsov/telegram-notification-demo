package com.dsumtsov.telegram.notification.demo.service.impl;

import com.dsumtsov.telegram.notification.demo.config.properties.TelegramProperties;
import com.dsumtsov.telegram.notification.demo.domain.Document;
import com.dsumtsov.telegram.notification.demo.service.TelegramService;
import com.dsumtsov.telegram.notification.demo.util.DateUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Slf4j
@Service
public class TelegramServiceImpl implements TelegramService {

    private final TelegramProperties properties;
    private final WebClient webClient;

    public TelegramServiceImpl(WebClient.Builder webClientBuilder,
                               TelegramProperties properties) {
        this.properties = properties;
        this.webClient =
                webClientBuilder.baseUrl(
                        properties.getBot().getUrl().concat(properties.getBot().getToken()))
                        .build();
    }

    @Override
    public void sendMessageWithDocument(Document document) {
        if (Objects.isNull(document)) {
            log.info("No document provided, message not sent");
            return;
        }

        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>(3);
        formData.set("chat_id", properties.getChatId());
        formData.set("document", getDocAsResource(document));
        formData.set("caption", String.format("%s report", DateUtils.getCurrentDateFormatted()));

        try {
            String response = webClient.post()
                    .uri("/sendDocument")
                    .body(BodyInserters.fromMultipartData(formData))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);

            if (StringUtils.isBlank(response)) {
                log.error("Failed to send message, received empty response");
            } else if (!jsonObject.get("ok").getAsBoolean()) {
                log.error("Failed to send message, received error in response: {}",
                        jsonObject.get("description").getAsString());
            } else {
                log.info("Message sent successfully");
            }
        } catch (Exception e) {
            log.error("Failed to send message: {}", e.getMessage());
        }
    }

    private ByteArrayResource getDocAsResource(Document doc) {
        return new ByteArrayResource(doc.getBody()) {
            @Override
            public String getFilename() {
                return doc.getName();
            }
        };
    }
}
