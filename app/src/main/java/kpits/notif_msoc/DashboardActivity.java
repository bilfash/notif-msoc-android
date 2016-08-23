package kpits.notif_msoc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DashboardActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private loadPage mPageTask = null;

    private static final String WIFI = "Wi-Fi";
    private static final String ANY = "Any";

    private String sToken;
    private String idUser;

    private final OkHttpClient client = new OkHttpClient();

    // Whether there is a Wi-Fi connection.
    private static boolean wifiConnected = false;
    // Whether there is a mobile connection.
    private static boolean mobileConnected = false;
    // Whether the display should be refreshed.
    private static boolean refreshDisplay = true;

    // The user's current network preference setting.
    private static String sPref = null;

    // The BroadcastReceiver that tracks network connectivity changes.
    private NetworkReceiver receiver = new NetworkReceiver();

    private static final String TAG = "DashboardActivity";

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);

//        if (pref.contains("sToken")) {
//            Toast t1 = Toast.makeText(getApplicationContext(), pref.getString("sToken", null), Toast.LENGTH_SHORT);
//            t1.show();
//        }
//
//        if (pref.contains("idUser")) {
//            Toast t1 = Toast.makeText(getApplicationContext(), pref.getString("idUser", null), Toast.LENGTH_SHORT);
//            t1.show();
//        }

        sToken = pref.getString("sToken", null);
        idUser = pref.getString("idUser", null);

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }

        // Register BroadcastReceiver to track connection changes.
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkReceiver();
        this.registerReceiver(receiver, filter);

        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);

        getLayoutInflater().inflate(R.layout.activity_dashboard, frameLayout);
        setTitle("Dashboard");

        // Retrieve the SwipeRefreshLayout and ListView instances
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    mPageTask = new loadPage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mPageTask.execute((Void) null);
            }
        });

        WebView myWebView = (WebView) findViewById(R.id.webview);
        // Force links and redirects to open in the WebView instead of in a browser
        myWebView.setWebViewClient(new WebViewClient());
        // Enable Javascript
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // to check current activity in the navigation drawer
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Gets the user's network preference settings
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Retrieves a string value for the preferences. The second parameter
        // is the default value to use if a preference value is not found.
        sPref = sharedPrefs.getString("listPref", "Wi-Fi");

        updateConnectedFlags();

        // Only loads the page if refreshDisplay is true. Otherwise, keeps previous
        // display. For example, if the user has set "Wi-Fi only" in prefs and the
        // device loses its Wi-Fi connection midway through the user using the app,
        // you don't want to refresh the display--this would force the display of
        // an error page instead of stackoverflow.com content.
        if (refreshDisplay) {
            try {
                mPageTask = new loadPage();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mPageTask.execute((Void) null);
        }
    }

    public class loadPage extends AsyncTask<Void, Void, Boolean> {
        private String json;

        loadPage() {
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                loadload();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mPageTask = null;

            if (!success) {
                showErrorPage();
            }
            else {
                loadDash();
                mSwipeRefreshLayout.setRefreshing(false);
            }

        }

        private void loadload() throws Exception {

            RequestBody formBody = new FormBody.Builder()
                    .add("token", sToken)
                    .add("id_user", idUser)
                    .build();
            Request request = new Request.Builder()
                    .url(Constants.URLdash)
                    .post(formBody)
                    .build();

            // Execute the request and retrieve the response.
            // TODO: 8/1/2016 add conn error handler
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            json = body.string();

            Log.d(TAG, "respon send " + String.valueOf(response));
//            Log.d(TAG, "isi send " + json);

            if (!response.isSuccessful()) {
                showErrorPage();
            }
//            if (((sPref.equals(ANY)) && (wifiConnected || mobileConnected))
//                    || ((sPref.equals(WIFI)) && (wifiConnected))) {
//
//            } else {
//                showErrorPage();
//            }
        }

        // Displays an error if the app is unable to load content.
        private void showErrorPage() {
            // The specified network connection is not available. Displays error message.
            WebView myWebView = (WebView) findViewById(R.id.webview);
            myWebView.loadData(getResources().getString(R.string.connection_error),
                    "text/html", null);
        }

        private void loadDash() {
            WebView myWebView = (WebView) findViewById(R.id.webview);
            myWebView.loadData(json,
                    "text/html", null);
        }
    }

    // Uses AsyncTask subclass to download the XML feed from stackoverflow.com.
    // This avoids UI lock up. To prevent network operations from
    // causing a delay that results in a poor user experience, always perform
    // network operations on a separate thread from the UI.

    // Checks the network connection and sets the wifiConnected and mobileConnected
    // variables accordingly.
    private void updateConnectedFlags() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        } else {
            wifiConnected = false;
            mobileConnected = false;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            Intent mainActivity = new Intent(Intent.ACTION_MAIN);
            mainActivity.addCategory(Intent.CATEGORY_HOME);
            mainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainActivity);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        /*getMenuInflater().inflate(R.menu.menu_dashboard, menu);*/

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsActivity = new Intent(getBaseContext(), SettingsActivity.class);
            startActivity(settingsActivity);
            return true;
        }
//        else if (id == R.id.subs) {
//            // [START subscribe_topics]
//            FirebaseMessaging.getInstance().subscribeToTopic("news");
//            Log.d(TAG, "Subscribed to news topic");
//            // [END subscribe_topics]
//            return true;
//        }
//        else if (id == R.id.token) {
//            Log.d(TAG, "InstanceID token: " + FirebaseInstanceId.getInstance().getToken());
//            return true;
//        }
        else if (id == R.id.refresh) {

            if (!mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(true);
            }

            try {
                mPageTask = new loadPage();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mPageTask.execute((Void) null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     *
     * This BroadcastReceiver intercepts the android.net.ConnectivityManager.CONNECTIVITY_ACTION,
     * which indicates a connection change. It checks whether the type is TYPE_WIFI.
     * If it is, it checks whether Wi-Fi is connected and sets the wifiConnected flag in the
     * main activity accordingly.
     *
     */
    public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connMgr =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();


            Toast.makeText(context, R.string.wifi_connected, Toast.LENGTH_SHORT).show();
            // Checks the user prefs and the network connection. Based on the result, decides
            // whether
            // to refresh the display or keep the current display.
            // If the userpref is Wi-Fi only, checks to see if the device has a Wi-Fi connection.
            if (WIFI.equals(sPref) && networkInfo != null
                    && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // If device has its Wi-Fi connection, sets refreshDisplay
                // to true. This causes the display to be refreshed when the user
                // returns to the app.
                refreshDisplay = true;
                Toast.makeText(context, R.string.wifi_connected, Toast.LENGTH_SHORT).show();

                // If the setting is ANY network and there is a network connection
                // (which by process of elimination would be mobile), sets refreshDisplay to true.
            } else if (ANY.equals(sPref) && networkInfo != null) {
                refreshDisplay = true;

                // Otherwise, the app can't download content--either because there is no network
                // connection (mobile or Wi-Fi), or because the pref setting is WIFI, and there
                // is no Wi-Fi connection.
                // Sets refreshDisplay to false.
            } else {
                refreshDisplay = false;
                Toast.makeText(context, R.string.lost_connection, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
