package warehousedelivery.taaxgenie.in.resagitpvtltd;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback{

    SupportMapFragment mapFragment;
    String lat="";
    String log="";
    TimerTask hourlyTask;
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Declare the timer
        Timer t = new Timer();
//Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {

                                  @Override
                                  public void run() {
                                      //Called each time when 1000 milliseconds (1 second) (the period parameter)
                                      try{
                                          String url = "https://priteshparmarnew1.000webhostapp.com/readlocation.php";
                                          Log.e("url",""+url);



                                          StringRequest strReq = new StringRequest(Request.Method.GET,
                                                  url, new Response.Listener<String>() {

                                              @Override
                                              public void onResponse(String response) {

                                                  Log.e("LOCATION URL", response.toString());
                                                  String spli[]=response.split("q=");
                                                  String split1=spli[2].substring(0,spli[2].length()-4);
                                                  String latlog[]=split1.split(",");
                                                  lat=latlog[0];
                                                  log=latlog[1];
                                                  Log.e("latlogs data","latlog-"+"lat-"+lat+" log-"+log);
                                                  setLatLog();

                                              }
                                          }, new Response.ErrorListener() {

                                              @Override
                                              public void onErrorResponse(VolleyError error) {
                                                  Log.e("LOCATION URL", error.getMessage());


                                              }
                                          });

// Adding request to request queue
                                          AppController.getInstance().addToRequestQueue(strReq);
                                      }catch (Exception e){
                                          Log.e("eror",e.getMessage());
                                      }
                                 }

                              },
//Set how long before to start calling the TimerTask (in milliseconds)
                2000,
//Set the amount of time between each execution (in milliseconds)
                2*60*1000);


    }



    private void setLatLog() {
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(lat),Double.parseDouble( log)))
                        .title("Komal")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(lat),Double.parseDouble( log)), 15));

            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

    }



}