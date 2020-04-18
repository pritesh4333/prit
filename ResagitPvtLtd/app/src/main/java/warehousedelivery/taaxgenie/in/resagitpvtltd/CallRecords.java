package warehousedelivery.taaxgenie.in.resagitpvtltd;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;


import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CallRecords extends AppCompatActivity implements SearchView.OnQueryTextListener {

    RecyclerView calllogs;
    callrecord organisationAdapter;
    TextView lastseen, location, gps,betry;
    String LASTSEEN;
    String Locations;
    String Gpss;
    String betrry;
    Button apps;
    public static ArrayList<GetSet> organisationlist ;
    private ArrayList<GetSet> tempOrganisationList ;
    StringBuilder appss= new StringBuilder();
    SwipeRefreshLayout pullToRefresh;
    int totalapps=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callrecords);


        calllogs = findViewById(R.id.calls);
        lastseen = (TextView) findViewById(R.id.lastseen);
        location = (TextView) findViewById(R.id.location);
        gps = (TextView) findViewById(R.id.gps);
        betry=(TextView)findViewById(R.id.btry);
        apps=(Button) findViewById(R.id.apps);
          pullToRefresh  = (SwipeRefreshLayout) findViewById(R.id.pullToRefresh);
        //setting an setOnRefreshListener on the SwipeDownLayout
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            int Refreshcounter = 1; //Counting how many times user have refreshed the layout

            @Override
            public void onRefresh() {
                //Here you can update your data from internet or from local SQLite data
                GetRecords();


            }
        });
        apps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CallRecords.this,AppsActivity.class);
                i.putExtra("TotalApps",totalapps);
                i.putExtra("apps",appss.toString());
                startActivity(i);
            }
        });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        //  mLayoutManager.setReverseLayout(true);
        calllogs.setLayoutManager(mLayoutManager);
        calllogs.setNestedScrollingEnabled(false);
        calllogs.setHasFixedSize(true);

        GetRecords();


    }

    private void GetRecords() {
        pullToRefresh.setRefreshing(true);
        try {
            String url = "https://priteshparmarnew1.000webhostapp.com/readalllatlog.php";
            Log.e("url", "" + url);


            StringRequest strReq = new StringRequest(Request.Method.GET,
                    url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        organisationlist = new ArrayList<>();
                        Log.e("CallLog res", response.toString());
                        JSONObject jsonObject = new JSONObject(response);
                        try{
                        JSONArray jsonarry = jsonObject.getJSONArray("CALL LOG");



                        for (int i = 0; i < jsonarry.length(); i++) {
                            JSONObject c = jsonarry.getJSONObject(i);
                            String Name = c.getString("Number Name");
                            String Number = c.getString("Phone Number");
                            String Type = "";
                            try {
                                Type = c.getString("Call Type");
                            } catch (Exception e) {
                                Log.e("api error", e.getMessage());
                            }
                            String Date = c.getString("Call Date");
                            String duration = c.getString("Call duration in sec");


                            GetSet org = new GetSet(Name, Number, Type, Date, duration);
                            organisationlist.add(org);
                        }
                    } catch (Exception e) {
                        Log.e("api error", e.getMessage());
                    }
                        LASTSEEN="Null";
                        try {
                        LASTSEEN = jsonObject.getString("LAST SEEN");



                        } catch (Exception e) {
                            Log.e("api error", e.getMessage());
                        }
                        Locations="null";
                        try {
                            Locations = jsonObject.getString("Location");
                        }catch(Exception e){
                            Log.e("Location error", e.getMessage());
                        }
                        Gpss = "NULL";
                        try {

                            Gpss = jsonObject.getString("GPS");

                        } catch (Exception e) {
                            Log.e("api error", e.getMessage());
                        }
                        betrry="Null";
                        try {
                        betrry=jsonObject.getString("Battery Level");
                        } catch (Exception e) {
                            Log.e("api error", e.getMessage());
                        }
                        lastseen.setText("Last Seen:-"+LASTSEEN);
                        location.setText("GPS:-"+Locations);
                        if (Gpss.equalsIgnoreCase("GPS OFF")){

                            gps.setText("GPS:-"+Gpss);
                            gps.setTextColor(getResources().getColor(R.color.colorAccent));
                        }else{
                            gps.setText("GPS:-"+Gpss);
                            gps.setTextColor(getResources().getColor(R.color.grey_400));
                        }

                        betry.setText("Battrey Level:-"+betrry);
                        apps.setVisibility(View.GONE);
                        try{
                            appss= new StringBuilder();
                        JSONArray APPS = jsonObject.getJSONArray("APPS");
                            totalapps=APPS.length();
                        for (int i = 0; i < APPS.length(); i++) {
                            JSONObject c = APPS.getJSONObject(i);
                            appss.append( c.getString("Apps")+ "\n"+"------------------------------------"+"\n" );
                            apps.setVisibility(View.VISIBLE);
                        }

                        } catch (JSONException e) {
                            Log.e("api error", e.getMessage());
                        }

                        organisationAdapter = new callrecord(CallRecords.this, organisationlist);
                        calllogs.setAdapter(organisationAdapter);
                        pullToRefresh.setRefreshing(false);
                        //organisationAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        Log.e("api error", e.getMessage());
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("CallLog URL", error.getMessage());


                }
            });

// Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq);
        } catch (Exception e) {
            Log.e("eror", e.getMessage());
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        newText = newText.toLowerCase();
        filterCompanies(newText);
        return false;
    }

    private void filterCompanies(String query) {

        if (!query.equalsIgnoreCase("")) {
            tempOrganisationList = new ArrayList<>();
            for (int i = 0; i < organisationlist.size(); i++) {

                if (organisationlist.get(i).getName().toLowerCase().contains(query.toLowerCase()) ||
                        organisationlist.get(i).getDate().toLowerCase().contains(query.toLowerCase()) ||
                        organisationlist.get(i).getType().toLowerCase().contains(query.toLowerCase()) ||
                        organisationlist.get(i).getNumber().toLowerCase().contains(query.toLowerCase())) {
                    tempOrganisationList.add(organisationlist.get(i));
                }
            }
        } else {
            tempOrganisationList = new ArrayList<>();
            for (int i = 0; i < organisationlist.size(); i++) {
                tempOrganisationList.add(organisationlist.get(i));
            }
        }

        organisationAdapter = new callrecord(CallRecords.this, tempOrganisationList);
        calllogs.setAdapter(organisationAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_only, menu);

        // Associate searchable configuration with the SearchView
        final MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        EditText searchEditText = searchView.findViewById(R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setTextSize(16f);
        searchEditText.setHint("Search");
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));
        searchView.setOnQueryTextListener(this);

        return true;
    }

}
