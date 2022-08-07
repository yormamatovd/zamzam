package product.session;


import lombok.Getter;
import product.enums.UserType;


public class Session {
    private static Long infoId;
    private static UserType userType;

    public static UserType getUserType() {
        return userType;
    }

    public static Long getInfoId() {
        return infoId;
    }

    public static void setInfoId(Long infoId) {
        Session.infoId = infoId;
    }

    public static void setUserType(UserType userType) {
        Session.userType = userType;
    }
}
