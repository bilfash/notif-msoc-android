package kpits.notif_msoc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class PertanyaanActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pertanyaan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*PopulatePerangkatJaringan();
        addItemsOnSpinner3();
        addItemsOnSpinner4();
        addItemsOnSpinner5();
        addListenerOnButton();*/
        addListenerOnSpinnerItemSelection();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pertanyaan, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            // Handle the camera action
        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_notification) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private Spinner spinner1, spinner2, spinner3, spinner4, spinner5, spinner6, spinner7;
    private Button btnSubmit;

    // add items into spinner dynamically
    public void PopulatePerangkatJaringan() {
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        list.add("Router");
        list.add("Modem");
        list.add("Antena");
        list.add("Kabel");
        list.add("UPS");
        list.add("Lainnya");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner2.setAdapter(dataAdapter);
    }

    public void PopulateLink() {
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        list.add("Transmission Backbone");
        list.add("FO Lastmile");
        list.add("HUB Vsat");
        list.add("Backhaul");
        list.add("Wareline");
        list.add("Lainnya");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner2.setAdapter(dataAdapter);
    }

    public void PopulatePLN() {
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        list.add("Listrik Padam");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner2.setAdapter(dataAdapter);
    }

    public void PopulateSIAK() {
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        list.add("SIAK Konsolidasi");
        list.add("Aplikasi SIAK");
        list.add("Server SIAK");
        list.add("Lainnya");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner2.setAdapter(dataAdapter);
    }

    public void PopulateKantor() {
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        list.add("Listrik");
        list.add("Relokasi");
        list.add("Renovasi");
        list.add("Kerusakan Bangunan");
        list.add("Lainnya");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner2.setAdapter(dataAdapter);
    }

    public void PopulatePerangkatEKTP() {
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        list.add("Server");
        list.add("Switch");
        list.add("UPS E-KTP");
        list.add("Finger Print");
        list.add("Kamera");
        list.add("Printer");
        list.add("Aplikasi");
        list.add("Lainnya");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner2.setAdapter(dataAdapter);
    }

    public void PopulatePilih() {
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        spinner2.setVisibility(View.INVISIBLE);
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner2.setAdapter(dataAdapter);
    }

    public void PopulateLainnya() {
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        spinner2.setVisibility(View.INVISIBLE);
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner2.setAdapter(dataAdapter);
    }

    public void PopulateLainnya2() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    public void PopulateRouter() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        list.add("Kabupaten");
        list.add("Non Kabupaten");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    public void PopulateModem() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        list.add("Vsat");
        list.add("ADSL / IndiHome");
        list.add("L2S");
        list.add("G Phone");
        list.add("Lainnya");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    public void PopulateAntena() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        list.add("Vsat");
        list.add("Lainnya");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    public void PopulateKabel() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        list.add("UTP");
        list.add("FO");
        list.add("Lainnya");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    public void PopulateUPS() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    public void PopulateTransBackbone() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    public void PopulateFOLastmile() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    public void PopulateHUB() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    public void PopulateBackhaul() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    public void PopulateWareline() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    public void PopulateListrikPadam() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    public void PopulateKonsolidasi() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    public void PopulateAplikasiSIAK() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    public void PopulateServerSIAK() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    public void PopulateListrik() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    public void PopulateRelokasi() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    public void PopulateRenovasi() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    public void PopulateKerusakan() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    public void PopulateServer() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    public void PopulateSwitch() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    public void PopulateUPSKTP() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    public void PopulateFingerPrint() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    public void PopulateKamera() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    public void PopulatePrinter() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    public void PopulateAplikasi() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    public void PopulateHari() {
        spinner4 = (Spinner) findViewById(R.id.spinner4);
        List<String> list = new ArrayList<String>();
        list.add("Pilih Hari");
        list.add("-");
        list.add("1 Hari");
        list.add("2 Hari");
        list.add("3 Hari");
        list.add("4 Hari");
        list.add("5 Hari");
        list.add("6 Hari");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner4.setAdapter(dataAdapter);
    }

    public void PopulateMinggu() {
        spinner5 = (Spinner) findViewById(R.id.spinner5);
        List<String> list = new ArrayList<String>();
        list.add("Pilih Minggu");
        list.add("-");
        list.add("1 Minggu");
        list.add("2 Minggu");
        list.add("3 Minggu");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner5.setAdapter(dataAdapter);
    }

    public void PopulateBulan() {
        spinner6 = (Spinner) findViewById(R.id.spinner6);
        List<String> list = new ArrayList<String>();
        list.add("Pilih Bulan");
        list.add("-");
        list.add("1 Bulan");
        list.add("2 Bulan");
        list.add("3 Bulan");
        list.add("4 Bulan");
        list.add("5 Bulan");
        list.add("6 Bulan");
        list.add("7 Bulan");
        list.add("8 Bulan");
        list.add("9 Bulan");
        list.add("10 Bulan");
        list.add("11 Bulan");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner6.setAdapter(dataAdapter);
    }

    public void PopulateTahun() {
        spinner7 = (Spinner) findViewById(R.id.spinner7);
        List<String> list = new ArrayList<String>();
        list.add("Pilih Tahun");
        list.add("-");
        list.add("1 Tahun");
        list.add("2 Tahun");
        list.add("3 Tahun");
        list.add("4 Tahun");
        list.add("5 Tahun");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner7.setAdapter(dataAdapter);
    }

//    public void addItemsOnSpinner5() {
//        spinner5 = (Spinner) findViewById(R.id.spinner5);
//        List<String> list = new ArrayList<String>();
//        list.add("");
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, list);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        dataAdapter.notifyDataSetChanged();
//        spinner5.setAdapter(dataAdapter);
//    }

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinner4 = (Spinner) findViewById(R.id.spinner4);
        spinner5 = (Spinner) findViewById(R.id.spinner5);
        spinner6 = (Spinner) findViewById(R.id.spinner6);
        spinner7 = (Spinner) findViewById(R.id.spinner7);

        PopulateHari();
        PopulateMinggu();
        PopulateBulan();
        PopulateTahun();

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                       long arg3) {
                // TODO Auto-generated method stub
                String sp1= String.valueOf(spinner1.getSelectedItem());
                spinner2.setVisibility(View.VISIBLE);
                spinner3.setVisibility(View.VISIBLE);
                Toast.makeText(PertanyaanActivity.this, sp1, Toast.LENGTH_SHORT).show();
                if(sp1.contentEquals("Pilih")) {
                    PopulatePilih();
                }
                else if(sp1.contentEquals("Perangkat Jaringan")) {
                    PopulatePerangkatJaringan();
                }
                else if(sp1.contentEquals("Link")) {
                    PopulateLink();
                }
                else if(sp1.contentEquals("PLN")) {
                    PopulatePLN();
                }
                else if(sp1.contentEquals("SIAK")) {
                    PopulateSIAK();
                }
                else if(sp1.contentEquals("Kantor")) {
                    PopulateKantor();
                }
                else if(sp1.contentEquals("Perangkat E-KTP")) {
                    PopulatePerangkatEKTP();
                }
                else if(sp1.contentEquals("Lainnya")) {
                    PopulateLainnya();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                       long arg3) {
                // TODO Auto-generated method stub
                String sp2= String.valueOf(spinner2.getSelectedItem());
                spinner3.setVisibility(View.VISIBLE);
                Toast.makeText(PertanyaanActivity.this, sp2, Toast.LENGTH_SHORT).show();

                if(sp2.contentEquals("Router")) {
                    PopulateRouter();
                }
                else if(sp2.contentEquals("Modem")) {
                    PopulateModem();
                }
                else if(sp2.contentEquals("Antena")) {
                    PopulateAntena();
                }
                else if(sp2.contentEquals("Kabel")) {
                    PopulateKabel();
                }
                else if(sp2.contentEquals("UPS")) {
                    PopulateUPS();
                }
                else if(sp2.contentEquals("Transmission Backbone")) {
                    PopulateTransBackbone();
                }
                else if(sp2.contentEquals("FO Lastmile")) {
                    PopulateFOLastmile();
                }
                else if(sp2.contentEquals("HUB Vsat")) {
                    PopulateHUB();
                }
                else if(sp2.contentEquals("Backhaul")) {
                    PopulateBackhaul();
                }
                else if(sp2.contentEquals("Wareline")) {
                    PopulateWareline();
                }
                else if(sp2.contentEquals("Listrik Padam")) {
                    PopulateListrikPadam();
                }
                else if(sp2.contentEquals("SIAK Konsolidasi")) {
                    PopulateKonsolidasi();
                }
                else if(sp2.contentEquals("Aplikasi SIAK")) {
                    PopulateAplikasiSIAK();
                }
                else if(sp2.contentEquals("Server SIAK")) {
                    PopulateServerSIAK();
                }
                else if(sp2.contentEquals("Listrik")) {
                    PopulateListrik();
                }
                else if(sp2.contentEquals("Relokasi")) {
                    PopulateRelokasi();
                }
                else if(sp2.contentEquals("Renovasi")) {
                    PopulateRenovasi();
                }
                else if(sp2.contentEquals("Kerusakan Bangunan")) {
                    PopulateKerusakan();
                }
                else if(sp2.contentEquals("Server")) {
                    PopulateServer();
                }
                else if(sp2.contentEquals("Switch")) {
                    PopulateSwitch();
                }
                else if(sp2.contentEquals("UPS E-KTP")) {
                    PopulateUPSKTP();
                }
                else if(sp2.contentEquals("Finger Print")) {
                    PopulateFingerPrint();
                }
                else if(sp2.contentEquals("Kamera")) {
                    PopulateKamera();
                }
                else if(sp2.contentEquals("Printer")) {
                    PopulatePrinter();
                }
                else if(sp2.contentEquals("Aplikasi")) {
                    PopulateAplikasi();
                }
                else if(sp2.contentEquals("Lainnya")) {
                    PopulateLainnya2();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                       long arg3) {
                // TODO Auto-generated method stub
                String sp3= String.valueOf(spinner3.getSelectedItem());
                spinner3.setVisibility(View.VISIBLE);
                Toast.makeText(PertanyaanActivity.this, sp3, Toast.LENGTH_SHORT).show();

                if(sp3.contentEquals("Kabupaten")) {

                }
                else if(sp3.contentEquals("Non Kabupaten")) {

                }
                else if(sp3.contentEquals("Vsat")) {

                }
                else if(sp3.contentEquals("ADSL / IndiHome")) {

                }
                else if(sp3.contentEquals("L2S")) {

                }
                else if(sp3.contentEquals("G Phone")) {

                }
                else if(sp3.contentEquals("UTP")) {

                }
                else if(sp3.contentEquals("FO")) {

                }
                else if(sp3.contentEquals("Lainnya")) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                       long arg3) {
                // TODO Auto-generated method stub
                String sp4= String.valueOf(spinner4.getSelectedItem());
                Toast.makeText(PertanyaanActivity.this, sp4, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                       long arg3) {
                // TODO Auto-generated method stub
                String sp5= String.valueOf(spinner5.getSelectedItem());
                Toast.makeText(PertanyaanActivity.this, sp5, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                       long arg3) {
                // TODO Auto-generated method stub
                String sp6= String.valueOf(spinner6.getSelectedItem());
                Toast.makeText(PertanyaanActivity.this, sp6, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        spinner7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                       long arg3) {
                // TODO Auto-generated method stub
                String sp7= String.valueOf(spinner7.getSelectedItem());
                Toast.makeText(PertanyaanActivity.this, sp7, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

    }


    // get the selected dropdown list value
    public void addListenerOnButton() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinner4 = (Spinner) findViewById(R.id.spinner4);
        spinner5 = (Spinner) findViewById(R.id.spinner5);
        spinner6 = (Spinner) findViewById(R.id.spinner6);
        spinner7 = (Spinner) findViewById(R.id.spinner7);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(PertanyaanActivity.this,
                        "OnClickListener : " +
                                "\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem()) +
                                "\nSpinner 2 : "+ String.valueOf(spinner2.getSelectedItem()) +
                                "\nSpinner 3 : "+ String.valueOf(spinner3.getSelectedItem()) +
                                "\nSpinner 4 : "+ String.valueOf(spinner4.getSelectedItem()) +
                                "\nSpinner 5 : "+ String.valueOf(spinner5.getSelectedItem()) +
                                "\nSpinner 6 : "+ String.valueOf(spinner6.getSelectedItem()) +
                                "\nSpinner 7 : "+ String.valueOf(spinner7.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }

        });
    }
}