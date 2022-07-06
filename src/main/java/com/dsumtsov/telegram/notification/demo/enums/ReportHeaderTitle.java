package com.dsumtsov.telegram.notification.demo.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ReportHeaderTitle {
    ID(0, "Id"),
    SELLER_ID(1, "Seller Id"),
    SKU_ID(2, "Sku Id"),
    QUANTITY(3, "Quantity");

    public final int index;
    public final String title;
}
