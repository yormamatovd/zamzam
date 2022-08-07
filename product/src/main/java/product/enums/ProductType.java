package product.enums;

public enum ProductType {
    WATER;

    public static boolean existValue(String s) {
        for (ProductType value : values()) if (value.name().equals(s)) return true;
        return false;
    }
}
