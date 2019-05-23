package com.example.go;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class GameRecord  extends AppCompatActivity {

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_record);

        context=this;
        Button backMenuButten=findViewById(R.id.menuButten);
        TextView winsRecord=findViewById(R.id.text_winsRecodr);
        TextView lostsRecord=findViewById(R.id.text_lostRecord);
        backMenuButten.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  Intent intent = new Intent(GameRecord.this,MainActivity.class);
                                                  startActivity(intent);
                                              }
                                          }
        );
        FileWR fileR=new FileWR();
        List<String> record = new ArrayList<>();
        record=fileR.loadBes("record.txt",context);
        if (!record.isEmpty()){
            winsRecord.setText(record.get(0));
            lostsRecord.setText(record.get(1));
        }else {
            winsRecord.setText("0");
            lostsRecord.setText("0");
        }

    }



}
