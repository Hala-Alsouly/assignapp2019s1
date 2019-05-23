package com.example.go;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class FileWR extends AppCompatActivity {

    //Write the winning and losing record to a file
    public void savebespoke(List<String> s,Context context) {

        try {
            FileOutputStream oFile = context.openFileOutput("record.txt", Context.MODE_PRIVATE);
            oFile.write(s.get(0).getBytes());
            oFile.write("\n".getBytes());
            oFile.write(s.get(1).getBytes());
            oFile.write("\n".getBytes());
            oFile.close();

        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    //Load the records from a file
    public List<String> loadBes(String file,Context context) {
        List<String> record = new ArrayList<>();
        try {
            InputStreamReader fr = new InputStreamReader(context.openFileInput(file));
            Scanner input = new Scanner(fr);

            while (input.hasNext()) {
                String words  = input.next();
                record.add(words);}

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return record;
    }
}
