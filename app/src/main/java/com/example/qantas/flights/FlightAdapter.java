package com.example.qantas.flights;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.qantas.R;

import java.util.ArrayList;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.FlightViewHolder> {
    private ArrayList<FlightItem> mFlightList;
    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void  setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;

    }
    public static class FlightViewHolder extends RecyclerView.ViewHolder{
        public ImageView mIcon;
        public TextView mAirportName;
        public TextView mCountry;

        public FlightViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.imageView);
            mAirportName = itemView.findViewById(R.id.tv_airportName);
            mCountry = itemView.findViewById(R.id.tv_country);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   if(listener != null){
                       int position = getBindingAdapterPosition();
                       if(position != RecyclerView.NO_POSITION)
                       {
                           listener.onItemClick(position);
                       }
                   }
                }
            });
        }
    }

    public FlightAdapter(ArrayList<FlightItem> flightList){
        mFlightList = flightList;

    }
    @Override
    public FlightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_item,parent,false);
        FlightViewHolder fvh = new FlightViewHolder(v, mListener);

        return fvh;
    }

    @Override
    public void onBindViewHolder(FlightAdapter.FlightViewHolder holder, int position) {
        FlightItem currentFlight = mFlightList.get(position);
        holder.mIcon.setImageResource(currentFlight.getIcon());
        holder.mAirportName.setText(currentFlight.getAirportName());
        holder.mCountry.setText(currentFlight.getCountry());

    }

    @Override
    public int getItemCount() {
        return mFlightList.size();
    }
}
