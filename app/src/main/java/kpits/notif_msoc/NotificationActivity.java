package kpits.notif_msoc;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class NotificationActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<String> array_list;
    private ArrayAdapter adapter;
    private DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_notification, frameLayout);
        setTitle("Notification");

        mydb = new DBHelper(this);

        // Retrieve the SwipeRefreshLayout and ListView instances
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        ListView listView = (ListView) findViewById(R.id.mobile_list);

//        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        array_list = mydb.getAllNotifs();
        Collections.reverse(array_list);
        adapter = new ArrayAdapter<>(this, R.layout.activity_listview, array_list);

        listView.setAdapter(adapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initiateRefresh();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // to check current activity in the navigation drawer
        navigationView.getMenu().getItem(3).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            startActivity(new Intent(NotificationActivity.this, DashboardActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notification, menu);
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
        else if (id == R.id.refresh) {
            // We make sure that the SwipeRefreshLayout is displaying it's refreshing indicator
            if (!mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(true);
            }

            // Start our refresh background task
            initiateRefresh();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToPertanyaan(View view) {
        Intent intent = new Intent(this, PertanyaanActivity.class);
        startActivity(intent);
    }

    private void initiateRefresh() {        /**
         * Execute the background task, which uses {@link android.os.AsyncTask} to load the data.
         */
        new refreshNotif().execute();
    }

    private void onRefreshComplete() {
        // Remove all items from the ListAdapter, and then replace them with the new items
        adapter.clear();
        for (String setring : array_list) {
            adapter.add(setring);
        }

        // Stop the refreshing indicator
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void refreshnot() {
        array_list = mydb.getAllNotifs();
        Collections.reverse(array_list);
    }

    private class refreshNotif extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                refreshnot();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            // Tell the Fragment that the refresh has completed
            onRefreshComplete();
        }
    }
}
