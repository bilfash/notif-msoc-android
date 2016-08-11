package kpits.notif_msoc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class ProfileActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private getUser mPageTask = null;
    private updateUser mUptTask = null;
    private final OkHttpClient client = new OkHttpClient();

    private String sToken = "";
    private String idUser = "";

    private String fullname;
    private String previledge;
    private String number;
    private String email;
    private String region;

    private static final String TAG = "ProfileActivity";
//    private final int SELECT_PHOTO = 1;
//    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile);

        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        getLayoutInflater().inflate(R.layout.activity_profile, frameLayout);

        sToken = pref.getString("sToken", null);
        idUser = pref.getString("idUser", null);

        /**
         * Setting title
         */
        setTitle("Profile");

        TextView textView = (TextView) findViewById(R.id.textView1);
        textView.setText(pref.getString("mUser", null));

//        imageView = (ImageView)findViewById(R.id.imageView2);
//        Button pickImage = (Button) findViewById(R.id.btn_pick);

        try {
            mPageTask = new getUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mPageTask.execute((Void) null);



        Button btnSubmit = (Button) findViewById(R.id.btnEditProfile);
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    mUptTask = new updateUser();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mUptTask.execute((Void) null);
            }

        });
    }

    public void goToChangePass(View view) {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // to check current activity in the navigation drawer
        navigationView.getMenu().getItem(1).setChecked(true);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
//        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
//
//        switch(requestCode) {
//            case SELECT_PHOTO:
//                if(resultCode == RESULT_OK){
//                    try {
//                        final Uri imageUri = imageReturnedIntent.getData();
//                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
//                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                        imageView.setImageBitmap(selectedImage);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//        }
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            startActivity(new Intent(ProfileActivity.this, DashboardActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
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

    public class getUser extends AsyncTask<Void, Void, Boolean> {
        private final String URLgetus = "http://notif-msoc.esy.es/api/v1/get_user";

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                if(loadload()){
                    return true;
                }
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
                isiProfil();
            }
        }

        private boolean loadload() throws Exception {
            RequestBody formBody = new FormBody.Builder()
                    .add("token", sToken)
                    .add("id_user", idUser)
                    .build();
            Request request = new Request.Builder()
                    .url(URLgetus)
                    .post(formBody)
                    .build();

            // Execute the request and retrieve the response.
            // TODO: 8/1/2016 add conn error handler
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            String json = body.string();

            Log.d(TAG, "respon send " + String.valueOf(response));
            Log.d(TAG, "isi send " + json);

            if (!response.isSuccessful()) {
                return false;
            }

            HashMap<String, String> map = new HashMap<>();
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
                e.printStackTrace();
            }

            fullname = map.get("fullname");
            previledge = map.get("previledge");
            number = map.get("number");
            email = map.get("email");
            region = map.get("name");

            return true;
        }

        // Displays an error if the app is unable to load content.
        private void showErrorPage() {
            Toast.makeText(ProfileActivity.this, "Maaf, jaringan anda bermasalah.", Toast.LENGTH_SHORT).show();
        }

        private void isiProfil() {
            TextView tv2 = (TextView) findViewById(R.id.textView2);
            TextView tv3 = (TextView) findViewById(R.id.textView3);
            TextView tv4 = (TextView) findViewById(R.id.textView4);
            TextView tv5 = (TextView) findViewById(R.id.textView5);
            TextView tv6 = (TextView) findViewById(R.id.textView6);

            tv2.setText(fullname);
            if (previledge.contentEquals("0")) {
                tv3.setText("Pusat");
            } else if (previledge.contentEquals("1")) {
                tv3.setText("Provinsi");
            } else if (previledge.contentEquals("2")) {
                tv3.setText("Kabupaten / Kota");
            }
            tv4.setText(region);
            tv5.setText(number);
            tv6.setText(email);
        }
    }

    public class updateUser extends AsyncTask<Void, Void, Boolean> {
        private final String URLupus = "http://notif-msoc.esy.es/api/v1/update_user";
        TextView tv2 = (TextView) findViewById(R.id.textView2);
        TextView tv4 = (TextView) findViewById(R.id.textView4);
        TextView tv5 = (TextView) findViewById(R.id.textView5);
        TextView tv6 = (TextView) findViewById(R.id.textView6);
        boolean sama = false;

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                if(!cekSamakah()) {
                    if(loadload()){
                        return true;
                    }
                } else {
                    infoSama();
                    sama = true;
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

            if (!success) {
                showErrorPage();
            }
            else {
                isiProfil();
            }
        }

        private boolean loadload() throws Exception {
            RequestBody formBody = new FormBody.Builder()
                    .add("token", sToken)
                    .add("id_user", idUser)
                    .build();
            Request request = new Request.Builder()
                    .url(URLupus)
                    .post(formBody)
                    .build();

            // Execute the request and retrieve the response.
            // TODO: 8/1/2016 add conn error handler
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            String json = body.string();

            Log.d(TAG, "respon send " + String.valueOf(response));
            Log.d(TAG, "isi send " + json);
            if (!response.isSuccessful()) {
                return false;
            } else {
                return true;
            }
        }

        // Displays an error if the app is unable to load content.
        private void showErrorPage() {
            Toast.makeText(ProfileActivity.this, "Maaf, jaringan anda bermasalah.", Toast.LENGTH_SHORT).show();
        }

        private void infoSama() {
            Toast.makeText(ProfileActivity.this, "Maaf, data yang anda masukkan sama.", Toast.LENGTH_SHORT).show();
        }

        private void isiProfil() {
            if(!sama) {
                fullname = tv2.getText().toString();
                number = tv4.getText().toString();
                email = tv5.getText().toString();
                region = tv6.getText().toString();
            }
        }

        private boolean cekSamakah() {
            int sama = 0;

            if(fullname.contentEquals(tv2.getText())) {
                sama++;
            }
            if(region.contentEquals(tv4.getText())) {
                sama++;
            }
            if(number.contentEquals(tv5.getText())) {
                sama++;
            }
            if(email.contentEquals(tv6.getText())) {
                sama++;
            }

            if(sama == 4) {
                return true;
            } else {
                return false;
            }
        }
    }
}
