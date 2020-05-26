package com.example.weather;

import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class intent extends AppCompatActivity {

    String total;
    Bundle bd;
    TextView tv4;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent);

        bd=this.getIntent().getExtras();

        total=bd.getString("total");
        tv4=findViewById(R.id.tv4);
        tv4.setText(total);


    }
}
