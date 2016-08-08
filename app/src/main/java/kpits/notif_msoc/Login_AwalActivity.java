package kpits.notif_msoc;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * A login screen that offers login via email/password.
 */
public class Login_AwalActivity extends AppCompatActivity {

    SharedPreferences pref;
    private static final String LOGIN_URL = "http://notif-msoc.esy.es/api/v1/login";
    private static final String SEND_TOKEN_URL = "http://notif-msoc.esy.es/api/v1/send_token";

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
/*    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world", "test:test"
    };*/
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private static final String TAG = "LoginAwalActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__awal);

        pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        // Set up the login form.
        mUsernameView = (AutoCompleteTextView) findViewById(R.id.username);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }



    /**
     * Callback received when a permissions request has been completed.
     */



    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } /*else if (!isEmailValid(username)) {
            mUsernameView.setError(getString(R.string.error_invalid_email));
            focusView = mUsernameView;
            cancel = true;
        }*/

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(username, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 3;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }





    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mPassword;
        private final OkHttpClient client = new OkHttpClient();

        public boolean send_token() throws Exception {
            String fToken = FirebaseInstanceId.getInstance().getToken();
            Log.d(TAG, "InstanceID token: " + fToken);
            if (pref.contains("sToken")) {
                String sToken = pref.getString("sToken", null);
                String idUser = pref.getString("idUser", null);

                RequestBody formBody = new FormBody.Builder()
                        .add("token", sToken)
                        .add("token_device", fToken)
                        .add("id_user", idUser)
                        .build();
                Request request = new Request.Builder()
                        .url(SEND_TOKEN_URL)
                        .post(formBody)
                        .build();

                // Execute the request and retrieve the response.
                // TODO: 8/1/2016 add conn error handler
                Response response = client.newCall(request).execute();
                ResponseBody body = response.body();
                String json = body.string();

                Log.d(TAG, "respon send " + String.valueOf(response));
                Log.d(TAG, "isi send " + json);

                if (response.isSuccessful()) {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("fToken", fToken);
                    editor.apply();
                    return true;
                }
                return false;
            }
            return false;
        }

        public boolean login() throws Exception {
            RequestBody formBody = new FormBody.Builder()
                    .add("username", mUsername)
                    .add("password", mPassword)
                    .build();
            Request request = new Request.Builder()
                    .url(LOGIN_URL)
                    .post(formBody)
                    .build();

            // Execute the request and retrieve the response.
            // TODO: 8/1/2016 add conn error handler
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            String json = body.string();

            Log.d(TAG, "respon login " + String.valueOf(response));
            Log.d(TAG, "isi login " + json);

            if (!response.isSuccessful()) return false;

            HashMap<String, String> map = new HashMap<String, String>();
            JSONObject jObject = new JSONObject(json);
            Iterator<?> keys = jObject.keys();

            try {
                while( keys.hasNext() ){
                    String key = (String)keys.next();
                    String value = jObject.getString(key);
                    map.put(key, value);
                }
            }
            catch (JSONException e)
            {

            }

            if(map.get("result") == null) {
                return false;
            }

            SharedPreferences.Editor editor = pref.edit();
            Log.d(TAG, "login token: " + map.get("result") + map.get("id_user"));
            editor.putString("sToken", map.get("result"));
            editor.putString("idUser", map.get("id_user"));
            editor.putString("mUser", mUsername);
            editor.commit();
            send_token();
            return true;
        }

        UserLoginTask(String username, String password) {
            mUsername = username;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            /*HashMap<String, String> data = new HashMap<String,String>();
            data.put("username", mUsername);
            data.put("password", mPassword);*/

            try {
                if(this.login()) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //if sukses true else false

/*            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }*/

            /*for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mUsername)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }*/

            // TODO: register the new account here.
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
                Intent intent = new Intent(Login_AwalActivity.this, DashboardActivity.class);
                Login_AwalActivity.this.startActivity(intent);

            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

