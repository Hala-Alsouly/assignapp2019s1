package com.example.go;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    /**
     * Write the onClick for the three buttons
     */
    public void onClick_VsCom(View v){
        Intent intent = new Intent(MainActivity.this, Game.class);
        startActivity(intent);
    }
    public void onClick_VsPlayer(View v){

    }
    // Allow the gamers to check the previous game records
    public void onClick_CheckRecords(View v){
        Intent intent = new Intent(MainActivity.this, CheckRecords.class);
        startActivity(intent);
    }
}
