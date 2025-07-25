package toplana.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "sr";
    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String RACUN_OSTALI_TROSKOVI_BEZ_STANA = "090100009";

    private Constants() {
    }
}
