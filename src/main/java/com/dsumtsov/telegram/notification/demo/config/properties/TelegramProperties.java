package com.dsumtsov.telegram.notification.demo.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Getter
@Setter
@ConfigurationProperties(
        value = "app.telegram",
        ignoreInvalidFields = true
)
public class TelegramProperties {

    private String chatId;
    @NestedConfigurationProperty
    private Bot bot = new Bot();

    @Getter
    @Setter
    public static class Bot {
        private String token;
        private String url;
    }
}
