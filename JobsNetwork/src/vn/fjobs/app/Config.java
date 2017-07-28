package vn.fjobs.app;

enum SERVER {
    PRODUCT,       // Product server. The running server on air
}

public class Config{

    public static final String APPLICATION = "vn_fjobs";

    /* -----Application configuration----- */
    private static SERVER mServer = SERVER.PRODUCT;

    /**
     * Connect time out to server in millisecond.
     */
    public static final int TIMEOUT_CONNECT = 60 * 1000;

    /**
     * Read stream on server timeout in millisecond.
     */
    public static final int TIMEOUT_READ = 5 * 60 * 1000;

    /**
     * Debug mode for (true: show log, throw exception, show toast)
     */
    public static final boolean DEBUG = false;
    // Main server
    public static String SERVER_URL;

    static {
        if (mServer == SERVER.PRODUCT) {
            // Product server
            SERVER_URL = "http://192.168.1.69:1313/FJobsServer/fjobs/mainservice/";
        }
    }
}
