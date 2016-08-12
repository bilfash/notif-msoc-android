package kpits.notif_msoc;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RegistrationIntentService extends IntentService {

    // abbreviated tag name
    private static final String TAG = "RegIntentService";
    private static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    private final OkHttpClient client = new OkHttpClient();

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Make a call to Instance API
        FirebaseInstanceId instanceID = FirebaseInstanceId.getInstance();
//        String senderId = getResources().getString(R.string.gcm_defaultSenderId);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        try {
            // request token that will be used by the server to send push notifications
            String fToken = instanceID.getToken();
            Log.d(TAG, "FCM Registration Token: " + fToken);

            // save token
            sharedPreferences.edit().putString("fToken", fToken).apply();
            // pass along this data
            sendRegistrationToServer(fToken);
        } catch (IOException e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, false).apply();
        }
    }

    private void sendRegistrationToServer(String fToken) throws IOException {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        // send network request
        if (sharedPreferences.contains("sToken")) {
            String sToken = sharedPreferences.getString("sToken", null);
            String idUser = sharedPreferences.getString("idUser", null);

            RequestBody formBody = new FormBody.Builder()
                    .add("token", sToken)
                    .add("token_device", fToken)
                    .add("id_user", idUser)
                    .build();
            Request request = new Request.Builder()
                    .url(Constants.SEND_TOKEN_URL)
                    .post(formBody)
                    .build();

            // Execute the request and retrieve the response.
            // TODO: 8/1/2016 add conn error handler
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            String json = body.string();

            Log.d(TAG, "respon send" + String.valueOf(response));
            Log.d(TAG, "isi send" + json);

            if (response.isSuccessful()) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("fToken", fToken);
                editor.apply();
            }
        }

        // if registration sent was successful, store a boolean that indicates whether the generated token has been sent to server
        sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, true).apply();
    }
}
