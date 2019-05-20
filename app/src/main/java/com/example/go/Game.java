package com.example.go;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Game extends AppCompatActivity {
    final static int boardSize=12;
    private Context context;
    //Array of cell
    private ImageView[][] ivCell= new ImageView[boardSize][boardSize] ;
    private Drawable[] drawCell=new Drawable[4] ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main);
        context=this;
        loadResources();
        BoardGame();

    }
    private void loadResources(){
        drawCell[0]=null;
        drawCell[1]=context.getResources().getDrawable(R.drawable.black);
        drawCell[2]=context.getResources().getDrawable(R.drawable.white);
        drawCell[3]=context.getResources().getDrawable(R.drawable.cell);
    }
    private void BoardGame(){
        int cellSize=Math.round(ScreenWidth()/boardSize);
        LinearLayout.LayoutParams lnrow=new LinearLayout.LayoutParams(cellSize*boardSize, cellSize);
        LinearLayout.LayoutParams lnCell=new LinearLayout.LayoutParams(cellSize, cellSize);
        //LinearLayout.LayoutParams lnCell=new LinearLayout.LayoutParams(cellSize,cellSize);
        LinearLayout lBoeardGame=(LinearLayout)findViewById(R.id.boardGame);

        for (int i=0; i<boardSize;i++){
            LinearLayout linRow=new LinearLayout(context);
            //drow row
            for (int j=0; j<boardSize;j++){
                ivCell[i][j]=new ImageView(context);
                //drow cell
                ivCell[i][j].setBackground(drawCell[3]);
                linRow.addView(ivCell[i][j],lnCell);
            }
            lBoeardGame.addView(linRow,lnrow);
        }
    }
    private float ScreenWidth(){
        Resources resources= context.getResources();
        DisplayMetrics dm=resources.getDisplayMetrics();
        return dm.widthPixels;

    }
}
