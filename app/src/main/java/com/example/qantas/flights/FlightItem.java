package com.example.qantas.flights;

public class FlightItem {
    private int mIcon;
    private String mAirportName;
    private String mCountry;
    private String mApiVal;


    public FlightItem(int icon, String airportName, String country, String apiVal){
        mIcon = icon;
        mAirportName = airportName;
        mCountry = country;
        mApiVal = apiVal;
    }

    public int getIcon(){
        return mIcon;
    }

    public String getAirportName() {
        return mAirportName;
    }

    public String getCountry() {
        return mCountry;
    }

    public String getApiVal(){
        return mApiVal;
    }
}
