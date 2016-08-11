package kpits.notif_msoc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ChangePasswordActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private loadPage mPageTask = null;

    private final OkHttpClient client = new OkHttpClient();

    private String sToken = "";
    private String idUser = "";
    private String User = "";
    private boolean errSama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_change_password, frameLayout);
        setTitle("Change Password");

        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        sToken = pref.getString("sToken", null);
        idUser = pref.getString("idUser", null);
        User = pref.getString("mUser", null);

        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    mPageTask = new loadPage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mPageTask.execute((Void) null);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // to check current activity in the navigation drawer
        navigationView.getMenu().getItem(1).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            startActivity(new Intent(ChangePasswordActivity.this, ProfileActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.change_password, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public class loadPage extends AsyncTask<Void, Void, Boolean> {
        loadPage() {
            tv7.setError(null);
            tv9.setError(null);
            errSama = false;
        }
        private final String URLdash = "http://notif-msoc.esy.es/api/v1/changepass_user";

        TextView tv7 = (TextView) findViewById(R.id.textView7);
        TextView tv8 = (TextView) findViewById(R.id.textView8);
        TextView tv9 = (TextView) findViewById(R.id.textView9);
        String passLama = tv7.getText().toString();
        String passBaru = tv8.getText().toString();
        String retypePass = tv9.getText().toString();

        String json;

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                if(!passBaru.contentEquals(retypePass)) {
                    errSama = true;
                    return false;
                }
                else {
                    loadload();
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mPageTask = null;

            if (success) {
                report_sent();
            }
            else {
                if(errSama) ErrorPasswordSama();
                else showError();
            }
        }

        private void loadload() throws Exception {
            RequestBody formBody = new FormBody.Builder()
                    .add("token", sToken)
                    .add("id_user", idUser)
                    .add("password", passBaru)
                    .add("passwordlama", passLama)
                    .add("username", User)
                    .build();
            Request request = new Request.Builder()
                    .url(URLdash)
                    .post(formBody)
                    .build();

            // Execute the request and retrieve the response.
            // TODO: 8/1/2016 add conn error handler
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            json = body.string();
        }

        // Displays an error if the app is unable to load content.
        private void showError() {
            Toast.makeText(ChangePasswordActivity.this, "Maaf, mohon coba lagi.", Toast.LENGTH_SHORT).show();
        }

        private void report_sent() {
            Toast.makeText(ChangePasswordActivity.this, "Password berhasil diubah", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        private void ErrorPasswordSama() {
            tv9.setError("Password yang Anda masukkan tidak sama");
        }
    }
}
