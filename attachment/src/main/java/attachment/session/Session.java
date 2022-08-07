package attachment.session;


public class Session {
    private static Long infoId;

    public static Long getInfoId() {
        return infoId;
    }

    public static void setInfoId(Long infoId) {
        Session.infoId = infoId;
    }
}
