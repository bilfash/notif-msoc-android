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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class PertanyaanActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private loadPage mPageTask = null;

    private final OkHttpClient client = new OkHttpClient();
    private String idAnswer;

    private String sToken = "";
    private String idUser = "";
    private String idResponse = "";
    private String idDetail = "";

    private int hour = 0;
    private int day = 0;
    private int week = 0;
    private int month = 0;
    private int year = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_pertanyaan, frameLayout);
        setTitle("Pertanyaan");

        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        sToken = pref.getString("sToken", null);
        idUser = pref.getString("idUser", null);

        /*PopulatePerangkatJaringan();
        addItemsOnSpinner3();
        addItemsOnSpinner4();
        addItemsOnSpinner5();*/
        addListenerOnButton();
        addListenerOnSpinnerItemSelection();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // to check current activity in the navigation drawer
        navigationView.getMenu().getItem(2).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            startActivity(new Intent(PertanyaanActivity.this, NotificationActivity.class));
            finish();
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
            Intent settingsActivity = new Intent(getBaseContext(), SettingsActivity.class);
            startActivity(settingsActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Spinner spinner1, spinner2, spinner3, spinner4, spinner5, spinner6, spinner7;

    // add items into spinner dynamically
    private void PopulatePerangkatJaringan() {
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<>();
        list.add("Router");
        list.add("Modem");
        list.add("Antena");
        list.add("Kabel");
        list.add("UPS");
        list.add("Lainnya");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner2.setAdapter(dataAdapter);
    }

    private void PopulateLink() {
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<>();
        list.add("Transmission Backbone");
        list.add("FO Lastmile");
        list.add("HUB Vsat");
        list.add("Backhaul");
        list.add("Wareline");
        list.add("Lainnya");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner2.setAdapter(dataAdapter);
    }

    private void PopulatePLN() {
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<>();
        list.add("Listrik Padam");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner2.setAdapter(dataAdapter);
    }

    private void PopulateSIAK() {
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<>();
        list.add("SIAK Konsolidasi");
        list.add("Aplikasi SIAK");
        list.add("Server SIAK");
        list.add("Lainnya");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner2.setAdapter(dataAdapter);
    }

    private void PopulateKantor() {
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<>();
        list.add("Listrik");
        list.add("Relokasi");
        list.add("Renovasi");
        list.add("Kerusakan Bangunan");
        list.add("Lainnya");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner2.setAdapter(dataAdapter);
    }

    private void PopulatePerangkatEKTP() {
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<>();
        list.add("Server");
        list.add("Switch");
        list.add("UPS E-KTP");
        list.add("Finger Print");
        list.add("Kamera");
        list.add("Printer");
        list.add("Aplikasi");
        list.add("Lainnya");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner2.setAdapter(dataAdapter);
    }

    private void PopulatePilih() {
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<>();
        spinner2.setVisibility(View.INVISIBLE);
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner2.setAdapter(dataAdapter);
    }

    private void PopulateLainnya() {
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<>();
        spinner2.setVisibility(View.INVISIBLE);
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner2.setAdapter(dataAdapter);
    }

    private void PopulateLainnya2() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    private void PopulateRouter() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<>();
        list.add("Kabupaten");
        list.add("Non Kabupaten");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    private void PopulateModem() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<>();
        list.add("Vsat");
        list.add("ADSL / IndiHome");
        list.add("L2S");
        list.add("G Phone");
        list.add("Lainnya");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    private void PopulateAntena() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<>();
        list.add("Vsat");
        list.add("Lainnya");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    private void PopulateKabel() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<>();
        list.add("UTP");
        list.add("FO");
        list.add("Lainnya");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    private void PopulateUPS() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    private void PopulateTransBackbone() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    private void PopulateFOLastmile() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    private void PopulateHUB() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    private void PopulateBackhaul() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    private void PopulateWareline() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    private void PopulateListrikPadam() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    private void PopulateKonsolidasi() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    private void PopulateAplikasiSIAK() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    private void PopulateServerSIAK() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    private void PopulateListrik() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    private void PopulateRelokasi() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    private void PopulateRenovasi() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    private void PopulateKerusakan() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    private void PopulateServer() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    private void PopulateSwitch() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    private void PopulateUPSKTP() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    private void PopulateFingerPrint() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    private void PopulateKamera() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    private void PopulatePrinter() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    private void PopulateAplikasi() {
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<>();
        spinner3.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter);
    }

    private void PopulateHari() {
        spinner4 = (Spinner) findViewById(R.id.spinner4);
        List<String> list = new ArrayList<>();
        list.add("Pilih Hari");
        list.add("-");
        list.add("1 Hari");
        list.add("2 Hari");
        list.add("3 Hari");
        list.add("4 Hari");
        list.add("5 Hari");
        list.add("6 Hari");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner4.setAdapter(dataAdapter);
    }

    private void PopulateMinggu() {
        spinner5 = (Spinner) findViewById(R.id.spinner5);
        List<String> list = new ArrayList<>();
        list.add("Pilih Minggu");
        list.add("-");
        list.add("1 Minggu");
        list.add("2 Minggu");
        list.add("3 Minggu");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner5.setAdapter(dataAdapter);
    }

    private void PopulateBulan() {
        spinner6 = (Spinner) findViewById(R.id.spinner6);
        List<String> list = new ArrayList<>();
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
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner6.setAdapter(dataAdapter);
    }

    private void PopulateTahun() {
        spinner7 = (Spinner) findViewById(R.id.spinner7);
        List<String> list = new ArrayList<>();
        list.add("Pilih Tahun");
        list.add("-");
        list.add("1 Tahun");
        list.add("2 Tahun");
        list.add("3 Tahun");
        list.add("4 Tahun");
        list.add("5 Tahun");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
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

    private void addListenerOnSpinnerItemSelection() {
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
                    idAnswer = "711";
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
                String sp1= String.valueOf(spinner1.getSelectedItem());
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
                    idAnswer = "211";
                }
                else if(sp2.contentEquals("FO Lastmile")) {
                    PopulateFOLastmile();
                    idAnswer = "221";
                }
                else if(sp2.contentEquals("HUB Vsat")) {
                    PopulateHUB();
                    idAnswer = "231";
                }
                else if(sp2.contentEquals("Backhaul")) {
                    PopulateBackhaul();
                    idAnswer = "241";
                }
                else if(sp2.contentEquals("Wareline")) {
                    PopulateWareline();
                    idAnswer = "251";
                }
                else if(sp2.contentEquals("Listrik Padam")) {
                    PopulateListrikPadam();
                    idAnswer = "311";
                }
                else if(sp2.contentEquals("SIAK Konsolidasi")) {
                    PopulateKonsolidasi();
                    idAnswer = "411";
                }
                else if(sp2.contentEquals("Aplikasi SIAK")) {
                    PopulateAplikasiSIAK();
                    idAnswer = "421";
                }
                else if(sp2.contentEquals("Server SIAK")) {
                    PopulateServerSIAK();
                    idAnswer = "431";
                }
                else if(sp2.contentEquals("Listrik")) {
                    PopulateListrik();
                    idAnswer = "511";
                }
                else if(sp2.contentEquals("Relokasi")) {
                    PopulateRelokasi();
                    idAnswer = "521";
                }
                else if(sp2.contentEquals("Renovasi")) {
                    PopulateRenovasi();
                    idAnswer = "531";
                }
                else if(sp2.contentEquals("Kerusakan Bangunan")) {
                    PopulateKerusakan();
                    idAnswer = "541";
                }
                else if(sp2.contentEquals("Server")) {
                    PopulateServer();
                    idAnswer = "611";
                }
                else if(sp2.contentEquals("Switch")) {
                    PopulateSwitch();
                    idAnswer = "621";
                }
                else if(sp2.contentEquals("UPS E-KTP")) {
                    PopulateUPSKTP();
                    idAnswer = "631";
                }
                else if(sp2.contentEquals("Finger Print")) {
                    PopulateFingerPrint();
                    idAnswer = "641";
                }
                else if(sp2.contentEquals("Kamera")) {
                    PopulateKamera();
                    idAnswer = "651";
                }
                else if(sp2.contentEquals("Printer")) {
                    PopulatePrinter();
                    idAnswer = "661";
                }
                else if(sp2.contentEquals("Aplikasi")) {
                    PopulateAplikasi();
                    idAnswer = "671";
                }
                else if(sp2.contentEquals("Lainnya")) {
                    PopulateLainnya2();
                    if(sp1.contentEquals("Perangkat Jaringan")) {
                        idAnswer = "161";
                    }
                    else if(sp1.contentEquals("Link")) {
                        idAnswer = "261";
                    }
                    else if(sp1.contentEquals("SIAK")) {
                        idAnswer = "441";
                    }
                    else if(sp1.contentEquals("Kantor")) {
                        idAnswer = "551";
                    }
                    else if(sp1.contentEquals("Perangkat E-KTP")) {
                        idAnswer = "681";
                    }
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
                String sp2= String.valueOf(spinner2.getSelectedItem());
                String sp3= String.valueOf(spinner3.getSelectedItem());
                spinner3.setVisibility(View.VISIBLE);
                Toast.makeText(PertanyaanActivity.this, sp3, Toast.LENGTH_SHORT).show();

                if(sp3.contentEquals("Kabupaten")) {
                    idAnswer = "111";
                }
                else if(sp3.contentEquals("Non Kabupaten")) {
                    idAnswer = "112";
                }
                else if(sp3.contentEquals("Vsat")) {
                    idAnswer = "121";
                }
                else if(sp3.contentEquals("ADSL / IndiHome")) {
                    idAnswer = "122";
                }
                else if(sp3.contentEquals("L2S")) {
                    idAnswer = "123";
                }
                else if(sp3.contentEquals("G Phone")) {
                    idAnswer = "124";
                }
                else if(sp3.contentEquals("UTP")) {
                    idAnswer = "141";
                }
                else if(sp3.contentEquals("FO")) {
                    idAnswer = "142";
                }
                else if(sp3.contentEquals("Lainnya")) {
                    if(sp2.contentEquals("Modem")) {
                        idAnswer = "125";
                    }
                    else if(sp2.contentEquals("Antena")) {
                        idAnswer = "132";
                    }
                    else if(sp2.contentEquals("Kabel")) {
                        idAnswer = "143";
                    }
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
                if(sp4.substring(0, 1).matches("[0-9]")) {
                    day = Integer.parseInt(sp4.substring(0, 1))*24;
                }
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
                if(sp5.substring(0, 1).matches("[0-9]")) {
                    week = Integer.parseInt(sp5.substring(0, 1))*24*7;
                }
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
                if(sp6.substring(0, 1).matches("[0-9]")) {
                    month = Integer.parseInt(sp6.substring(0, 1))*24*30;
                }
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
                if(sp7.substring(0, 1).matches("[0-9]")) {
                    year = Integer.parseInt(sp7.substring(0, 1))*24*365;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    public class loadPage extends AsyncTask<Void, Void, Boolean> {

        String json;
        loadPage() {
            printHours();
            idResponse = String.valueOf(hour);
        }

        public void printHours(){
            TextView textView5 = (TextView) findViewById(R.id.textView5);
            idDetail = textView5.getText().toString();
            hour = day + week + month + year;
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

            if (success) {
                report_sent();
            }
            else {
                showError();
            }
        }

        private void loadload() throws Exception {
            RequestBody formBody = new FormBody.Builder()
                    .add("token", sToken)
                    .add("id_user", idUser)
                    .add("id_answer", idAnswer)
                    .add("response", idResponse)
                    .add("detail", idDetail)
                    .build();
            Request request = new Request.Builder()
                    .url(Constants.URLreps)
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
            Toast.makeText(PertanyaanActivity.this, "Maaf, mohon coba lagi.", Toast.LENGTH_SHORT).show();
        }

        private void report_sent() {
            Toast.makeText(PertanyaanActivity.this, json, Toast.LENGTH_SHORT).show();
            Toast.makeText(PertanyaanActivity.this, idDetail, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
        }
    }

    // get the selected dropdown list value
    private void addListenerOnButton() {

//        spinner1 = (Spinner) findViewById(R.id.spinner1);
//        spinner2 = (Spinner) findViewById(R.id.spinner2);
//        spinner3 = (Spinner) findViewById(R.id.spinner3);
//        spinner4 = (Spinner) findViewById(R.id.spinner4);
//        spinner5 = (Spinner) findViewById(R.id.spinner5);
//        spinner6 = (Spinner) findViewById(R.id.spinner6);
//        spinner7 = (Spinner) findViewById(R.id.spinner7);
        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    mPageTask = new loadPage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mPageTask.execute((Void) null);

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