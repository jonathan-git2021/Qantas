package com.example.qantas.flights;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.qantas.MainActivity;
import com.example.qantas.R;

public class FlightDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_details);

        Intent act2Intent = getIntent();
        String extraText = act2Intent.getStringExtra(MainActivity.EXTRA_TEXT);

        TextView detail1 = (TextView)findViewById(R.id.textViewAct2);
        detail1.setText(extraText);

        Button backBtn = (Button)findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}