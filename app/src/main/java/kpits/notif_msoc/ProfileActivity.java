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

    private loadPage mPageTask = null;
    private final OkHttpClient client = new OkHttpClient();

    private String sToken = "";
    private String idUser = "";

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

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        imageView = (ImageView)findViewById(R.id.imageView2);
//        Button pickImage = (Button) findViewById(R.id.btn_pick);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        pickImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//                photoPickerIntent.setType("image/*");
//                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
//            }
//        });

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
        try {
            mPageTask = new loadPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mPageTask.execute((Void) null);
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

    public class loadPage extends AsyncTask<Void, Void, Boolean> {
        private final String URLdash = "http://notif-msoc.esy.es/api/v1/get_user";

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
        }

        private void loadload() throws Exception {
            RequestBody formBody = new FormBody.Builder()
                    .add("token", sToken)
                    .add("id_user", idUser)
                    .build();
            Request request = new Request.Builder()
                    .url(URLdash)
                    .post(formBody)
                    .build();

            // Execute the request and retrieve the response.
            // TODO: 8/1/2016 add conn error handler
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            String json = body.string();

            Log.d(TAG, "respon send " + String.valueOf(response));
            Log.d(TAG, "isi send " + json);

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

            Toast.makeText(ProfileActivity.this, json, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
        }

        // Displays an error if the app is unable to load content.
        private void showErrorPage() {
            Toast.makeText(ProfileActivity.this, "Maaf, mohon coba lagi.", Toast.LENGTH_SHORT).show();
        }
    }
}
