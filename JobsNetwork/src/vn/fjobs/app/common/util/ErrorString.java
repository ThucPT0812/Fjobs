package vn.fjobs.app.common.util;


import project.fjobs.R;
import vn.fjobs.app.common.connection.Response;

public class ErrorString {

    public static int getDescriptionOfErrorCode(int code) {
        int message = R.string.alert;
        switch (code) {
            case Response.SERVER_INCORRECT_PASSWORD:
                message = R.string.msg_common_password_is_incorrect;
                break;
            default:
                break;
        }
        return message;
    }
}
