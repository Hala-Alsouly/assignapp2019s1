package com.example.go;

import android.content.Context;
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

public class Game extends AppCompatActivity  {
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
                turnOf_text.setText("");
            }
        });
        loadResources();
        BoardGame();
        initialize_board();
        start_game();

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
                        if (valueCell[xmove][ymove]==0){

                        }
                        if (player_turn==1){
                            //isClicked=true;
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
        //Toast.makeText(context, "Your Turn!", Toast.LENGTH_SHORT).show();
        turnOf_text.setText("Your Turn!");
        //isClicked=false;

    }
    private void Ai_turn(){
        //Toast.makeText(context, "Computer Turn!", Toast.LENGTH_SHORT).show();
        //turnOf_text.setText("Computer Turn!");
        if (first_move){
            xmove=new Random().nextInt(boardSize);
            ymove=new Random().nextInt(boardSize);
            //first_move=false;
            move();
        }else{
            //minimax
        }

    }
    private void move(){

        if (valueCell[xmove][ymove]==0) {
            ivCell[xmove][ymove].setImageDrawable(drawCell[player_turn]);
            valueCell[xmove][ymove] = player_turn;
            if (isBoardFull()) {
                Toast.makeText(context, "Draw!", Toast.LENGTH_SHORT).show();
                return;
            } else {
                if (isWinning()) {
                    if (winner == 1) {
                        Toast.makeText(context, "You Win!", Toast.LENGTH_SHORT).show();
                        turnOf_text.setText("You Win!");
                    } else {
                        Toast.makeText(context, "You Lose!", Toast.LENGTH_SHORT).show();
                        turnOf_text.setText("You Lose!");
                    }
                    return;
                }
            }
            if (player_turn == 1) {
                player_turn = 2;
                Ai_turn();
            } else if (player_turn == 2) {
                player_turn = 1;
                player_turn();
            }
        }else
            {Toast.makeText(context, "Choose another position!", Toast.LENGTH_SHORT).show();
        //return;
            }


    }

    private boolean isBoardFull() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (valueCell[i][j]==0)
                    return false;
            }
        }
        return true;
    }

    private boolean isWinning() {
        if(winner!=0)
            return true;
        VectorEnd(xmove,0,0,1,xmove,ymove);
        VectorEnd(0,ymove,1,0,xmove,ymove);
        if(xmove+ymove>=boardSize-1)
            VectorEnd(boardSize-1,xmove+ymove-boardSize+1,-1,1,xmove,ymove);
        else VectorEnd(xmove+ymove,0,-1,1,xmove,ymove);

        if(xmove<=ymove)
            VectorEnd(xmove-ymove+boardSize-1,boardSize-1,-1,-1,xmove,ymove);
        else VectorEnd(boardSize-1,boardSize-1-(xmove-ymove),-1,-1,xmove,ymove);

        if(winner!=0)
            return true;
        else return false;


        //return false;
    }

    private void VectorEnd(int xx, int yy, int vx, int vy, int rx, int ry) {
        //check the row based on vector(vx,vy)
        if(winner!=0)
            return;
        int i,j;
        int xbelow=rx-4*vx;
        int ybelow=ry-4*vy;
        int xabove=rx+4*vx;
        int yabove=ry+4*vy;
        String st="";
        i=xx;
        j=yy;
        while (!inside(i,xbelow,xabove) || !inside(j,ybelow,yabove)){
            i+=vx;
            j+=vy;
        }
        while (true){
            System.out.println(st);
            st += String.valueOf(valueCell[i][j]);
            if (st.length()==5){
                EvalEnd(st);
                st=st.substring(1,5);

            }
            i+=vx;
            j+=vy;
            if(!inBoard(i,j)|| !inside(i,xbelow,xabove) || !inside(j,ybelow,yabove)||winner!=0){
                break;
            }
        }

    }

    private boolean inBoard(int i, int j) {
        if (i<0 || i>=boardSize || j<0 ||j>=boardSize)
            return false;
        return true;
    }

    private void EvalEnd(String st) {
        switch (st){
            case "11111":winner=1; break;
            case "22222":winner=2;break;
            default:break;
        }
    }

    private boolean inside(int i, int xbelow, int xabove) {

        return(i-xbelow)*(i-xabove)<=0;
    }

    private void start_game() {
        player_turn= new Random().nextInt(2)+1;
        if (player_turn==1){
            //first_move=false;
            player_turn();
        }

        else Ai_turn();

    }

}
