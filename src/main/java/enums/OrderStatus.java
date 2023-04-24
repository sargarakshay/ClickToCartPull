package enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderStatus {

    ORDERED(1, "Ordered"),
    CANCELLED(2, "Cancelled"),
    DELIVERED(3, "Delivered");

    private final Integer id;
    private final String value;
}
