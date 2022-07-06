package com.dsumtsov.telegram.notification.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class TelegramNotificationDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelegramNotificationDemoApplication.class, args);
	}

}
