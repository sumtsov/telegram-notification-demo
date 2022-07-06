package com.dsumtsov.telegram.notification.demo.domain;

import lombok.Value;

@Value
public class Document {
    String name;
    byte[] body;
}
