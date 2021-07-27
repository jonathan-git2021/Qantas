package com.example.qantas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.qantas.flights.FlightAdapter;
import com.example.qantas.flights.FlightDetails;
import com.example.qantas.flights.FlightItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FlightAdapter mAdapter; // serve as a bridge for data added in ArrayList and RecyclerView
    private RecyclerView.LayoutManager mLayoutManager; // responsible for aligning items on list

    //for getting api
    private RequestQueue mQueue;
    ProgressDialog progDialog;

    private List<String> myGlobalData = new ArrayList<String>();
    // creating array list that contains items to be added in ExampleItem
    ArrayList<FlightItem> flightList = new ArrayList<>();

    public static final String EXTRA_TEXT = "com.example.qantas.EXTRA_TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // api creating request
        mQueue = Volley.newRequestQueue(this);
        jsonParse();
    }

    public void jsonParse(){
        showLoader();
        Log.d("INSIDE JSON PARSE","HELLO" );
        String url  = "https://api.qantas.com/flight/refData/airport";

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progDialog.dismiss();
                        Log.d("SAMBO"," >>>>>> " +response.toString());
                        try {
                            for(int i = 0; i < response.length(); i++){
                                JSONObject flightDetails = response.getJSONObject(i);
                                Log.d("API VAL : "," "+ flightDetails  );

                                String airportName = flightDetails.getString("airportName");
                                String country = flightDetails.getString("country");
                                JSONObject countryObj = new JSONObject(country);
                                String countryName = countryObj.getString("countryName") == null ? "": countryObj.getString("countryName");

                                flightList.add(new FlightItem(R.drawable.ic_airplane,airportName,countryName,flightDetails.toString()));

                                mRecyclerView = findViewById(R.id.recyclerView);
                                mRecyclerView.setHasFixedSize(true);
                                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                mAdapter = new FlightAdapter(flightList);

                                mRecyclerView.setLayoutManager(mLayoutManager);
                                mRecyclerView.setAdapter(mAdapter);

                                mAdapter.setOnItemClickListener(new FlightAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int position) {
                                        String flightInfo = flightList.get(position).getApiVal();
                                        Log.d("TEST" ," == " + flightList.get(position).getApiVal());
                                        openFlightDetails(flightInfo);
                                    }
                                });
                            }
                        }catch (JSONException e){
                            progDialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(arrayRequest);
    }
    public void openFlightDetails(String flightInfo){
        Log.d("FI" ," == " + flightInfo );
        try {
            JSONObject flightInfoObj = new JSONObject(flightInfo);
            Intent intent1 = new Intent(this,FlightDetails.class);
            intent1.putExtra(EXTRA_TEXT,flightInfo);
            startActivity(intent1);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }

    public void showLoader(){
        progDialog = new ProgressDialog(this);
        progDialog.setMessage("Loading...");
        progDialog.show();
    }
}