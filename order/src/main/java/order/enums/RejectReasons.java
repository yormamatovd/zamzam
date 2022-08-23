package order.enums;

public enum RejectReasons {
    NOT_IN_STORAGE;

    public static boolean exist(String s) {
        for (RejectReasons value : RejectReasons.values()) if (value.name().equals(s)) return true;
        return false;
    }
}
