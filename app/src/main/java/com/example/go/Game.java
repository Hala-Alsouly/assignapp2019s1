package com.example.go;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Game extends AppCompatActivity implements View.OnClickListener {
    final static int boardSize=12;
    private Context context;
    //Array of cell
    private ImageView[][] ivCell= new ImageView[boardSize][boardSize] ;
    private Drawable[] drawCell=new Drawable[4] ;
    private int[][] valueCell=new int[boardSize][boardSize] ;
    private int winner;
    private boolean first_move;
    private int xmove, ymove;
    private int player_turn;
    private boolean isClicked;
    private TextView turnOf_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main);
        context=this;
        Button newGameButten= findViewById(R.id.newGameButten);
        turnOf_text=findViewById(R.id.turnOf_text);
        newGameButten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialize_board();
                start_game();
            }
        });
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
                final int x=i;
                final int y=j;
                ivCell[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (player_turn==1|| !isClicked){
                            isClicked=true;
                            xmove=x;
                            ymove=y;
                            move();
                        }
                    }
                });
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
    private void initialize_board(){
        first_move=true;
        winner=0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                ivCell[i][j].setImageDrawable(drawCell[0]);
                valueCell[i][j]=0;
            }
        }
    }
    private void player_turn(){
        Toast.makeText(context, "Your Turn!", Toast.LENGTH_SHORT).show();
        turnOf_text.setText("Your Turn!");
        isClicked=false;

    }
    private void Ai_turn(){
        Toast.makeText(context, "Computer Turn!", Toast.LENGTH_SHORT).show();
        turnOf_text.setText("Computer Turn!");
        if (first_move){
            xmove=new Random().nextInt(boardSize);
            ymove=new Random().nextInt(boardSize);
            move();
        }else{
            //minimax
        }

    }
    private void move(){
        ivCell[xmove][ymove].setImageDrawable(drawCell[player_turn]);
        player_turn=(player_turn % 2)+1;

    }
    private void start_game() {
        player_turn= new Random().nextInt(2)+1;
        if (player_turn==1)
            player_turn();
        else Ai_turn();

    }

    @Override
    public void onClick(View v) {

    }
}
