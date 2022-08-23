package order.enums;

public enum OrderStatus {
    WAIT_SELLER_ACCEPT,
    WAIT_DELIVERY,
    DELIVERED,
    RECEIVED,
    REJECTED,
    CANCELED;

    public static boolean exist(String s) {
        for (OrderStatus value : values()) if (value.name().equals(s)) return true;
        return false;
    }
}
