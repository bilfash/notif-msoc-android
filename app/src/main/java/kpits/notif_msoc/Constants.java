package kpits.notif_msoc;

/**
 * Created by Danang on 8/12/2016.
 */
public class Constants {

    public Constants() {

    }

    public static final String URLserver = "http://notif-msoc.esy.es";
    public static final String URLdash = URLserver + "/api/v1/dashboardAPI";
    public static final String URLhist = URLserver + "/api/v1/historyAPI";
    public static final String URLgetus = URLserver + "/api/v1/get_user";
    public static final String URLupus = URLserver + "/api/v1/update_user";
    public static final String URLreps = URLserver + "/api/v1/send_report";

    public static final String LOGIN_URL = URLserver + "/api/v1/login";
    public static final String SEND_TOKEN_URL = URLserver + "/api/v1/send_token";
}
