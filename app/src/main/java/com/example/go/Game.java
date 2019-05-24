package com.example.go;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends AppCompatActivity  {
    final static int boardSize=12;
    private Context context;
    //Array of cell to draw the board
    private ImageView[][] ivCell= new ImageView[boardSize][boardSize] ;
    //Array to place the drawable (background board, placments)
    private Drawable[] drawCell=new Drawable[4] ;
    private Drawable bombIcon;
    //Two rectangles to show the player turn
    private Drawable border;
    private Drawable borderless;

    //Save the values for all players
    private int[][] valueCell=new int[boardSize][boardSize] ;
    private int winner;
    private boolean first_move;
    private int xmove, ymove;
    //indicate the current turn, 1 indicates the first player or the human player,
    // 2 for the computer or the second player
    private int player_turn;
    private TextView tvPlayerOne;
    private TextView tvPlayerTwo;
    //the button to decide whether the bomb is activated
    private Button btnBomb;
    //List to save the retrieved data from the file
    private List<String> record = new ArrayList<>();
    private FileWR fileR=new FileWR();

    private Evaluation evaluation = new Evaluation();
    //True if bomb is activated
    private boolean isBomb;
    //control the two Modes
    private boolean isAIMode = true;
    public static final String KEY_MODE_AI = "MODE_AI";
    private MediaPlayer mp;
    //counter for number of bomb used
    private int playerOneBomb;
    private int playerTowBomb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main);
        //sound from https://taira-komori.jpn.org/arms01tw.html
        mp=MediaPlayer.create(this, R.raw.bomb);
        //the value of isAIMode is assigned according to the information in intent(the selection of the player)
        if (getIntent() != null){
            isAIMode = getIntent().getBooleanExtra(KEY_MODE_AI,true);
        }
        context=this;
        bombIcon = context.getResources().getDrawable(R.drawable.bomb);
        border=context.getResources().getDrawable(R.drawable.border);
        border=context.getResources().getDrawable(R.drawable.borderless);
        //read the file record to modify it later
        record=fileR.loadBes("record.txt",context);
        if (record.isEmpty()){
            record.add("0");
            record.add("0");
        }
        Button newGameButten= findViewById(R.id.newGameButten);
        Button backMenuButten=findViewById(R.id.menuButten);
        //two textviews to tell the turn of the player
        tvPlayerOne =findViewById(R.id.playerOne_text);
        tvPlayerTwo =findViewById(R.id.playerTwo_text);
        //if is AI mode, the second textview is not required
        if (isAIMode){
            tvPlayerTwo.setVisibility(View.GONE);
        }
        newGameButten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start a new game
                initialize_board();
                evaluation = new Evaluation();
                start_game();
//                tvPlayerOne.setText("");
            }
        });
        backMenuButten.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  Intent intent = new Intent(Game.this,MainActivity.class);
                                                  startActivity(intent);
                                              }
                                          }
        );
        btnBomb = findViewById(R.id.btn_bomb);
        //set up the onClickListener for the bomb button, if it is pressed, the bomb is activated
        btnBomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player_turn==1){
                    if (playerOneBomb<3){
                        isBomb = true;
                        btnBomb.setBackgroundResource(R.drawable.shape_radius_bg_red);
                        playerOneBomb++;
                    }else Toast.makeText(context, "You are out of bombs!", Toast.LENGTH_SHORT).show();

                }else{
                    if (playerTowBomb<3) {
                        isBomb = true;
                        btnBomb.setBackgroundResource(R.drawable.shape_radius_bg_red);
                        playerTowBomb++;
                    }else Toast.makeText(context, "You are out of bombs!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        loadResources();
        BoardGame();
        //Start a new game
        initialize_board();
        start_game();

    }

    //Set board contents
    private void loadResources(){
        drawCell[0]=context.getResources().getDrawable(R.drawable.cell);//background
        drawCell[1]=context.getResources().getDrawable(R.drawable.black);
        drawCell[2]=context.getResources().getDrawable(R.drawable.white);
    }

    //prepare the game board
    private void BoardGame(){
        //Get the cell size depends on screen size
        int cellSize=Math.round(ScreenWidth()/boardSize);
        //Setting the size of the linearLayout
        LinearLayout.LayoutParams lnrow=new LinearLayout.LayoutParams(cellSize*boardSize, cellSize);
        //the size of the cell setting to be a size of linear layout
        LinearLayout.LayoutParams lnCell=new LinearLayout.LayoutParams(cellSize, cellSize);
        LinearLayout lBoeardGame=(LinearLayout)findViewById(R.id.boardGame);

        for (int i=0; i<boardSize;i++){
            LinearLayout linRow=new LinearLayout(context);
            for (int j=0; j<boardSize;j++){
                ivCell[i][j]=new ImageView(context);
                ivCell[i][j].setBackground(drawCell[0]);
                final int x=i;
                final int y=j;
                //On click listener for each cell on the board
                ivCell[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //if (player_turn==1){
                            xmove=x;
                            ymove=y;
                            String curPosition = evaluation.positionToString(xmove,ymove);

                            if (evaluation.getAvailablePositions().contains(curPosition)){

                                String x = "", y = "";
                                evaluation.addPlayerPosition(curPosition);
                                Log.i("counts", "player size: " + evaluation.getPlayerPositions().size());
                                Log.i("counts", "all size: " + evaluation.getAvailablePositions().size());
                            }

                            move();
                        }
                    //}
                });
                //add each cell to the view
                linRow.addView(ivCell[i][j],lnCell);
            }
            //add each row to the view
            lBoeardGame.addView(linRow,lnrow);
        }
    }

    //get screen width
    private float ScreenWidth(){
        Resources resources= context.getResources();
        DisplayMetrics dm=resources.getDisplayMetrics();
        return dm.widthPixels;

    }

    //initialize the game board and reset every thing
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

    //manage the turn of players and the corresponding textviews
    private void player_turn(){
        if (isAIMode) tvPlayerOne.setText("Your Turn!");
        else{
            if (player_turn == 1){
                tvPlayerOne.setText("Player One Turn!");
                tvPlayerTwo.setText("");
                tvPlayerOne.setBackground(border);
                tvPlayerTwo.setBackground(borderless);
            } else {
                tvPlayerOne.setText("");
                tvPlayerTwo.setText("Player Two Turn!");
                tvPlayerTwo.setBackground(border);
                tvPlayerOne.setBackground(borderless);
            }
        }
    }

    //AI player
    private void Ai_turn(){
        //Toast.makeText(context, "Computer Turn!", Toast.LENGTH_SHORT).show();
        //comment_text.setText("Computer Turn!");
        if (first_move){
            xmove= boardSize / 2 + new Random().nextInt(3);
            ymove= boardSize / 2 + new Random().nextInt(3);
            first_move=false;

            evaluation.addAiPosition(evaluation.positionToString(xmove, ymove));
            Log.i("ai", "size  " + evaluation.getAiPositions().size() + "");

            move();
        }else{
            String move = evaluation.choosePositionForAi();
            xmove = Integer.valueOf(move.substring(0 ,2));
            ymove = Integer.valueOf(move.substring(2 ,4));
            evaluation.addAiPosition(move);
            Log.i("ai", "size  " + evaluation.getAiPositions().size() + "");
            move();
        }
    }

    //Set movement
    private void move(){

        //for available positions
        if (valueCell[xmove][ymove]==0 && winner==0) {
            //consider the bomb case
            if (isBomb){
                isBomb = false;

                ivCell[xmove][ymove].setImageDrawable(bombIcon);
                ScaleAnimation scaleAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(this, R.anim.bomb);
                ivCell[xmove][ymove].startAnimation(scaleAnimation);
                ivCell[0][0].postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Start the sound
                        mp.start();
                        Toast.makeText(context, "Bomb!", Toast.LENGTH_SHORT).show();
                        for (int i = xmove-1; i < xmove + 2; i++) {
                            for (int j = ymove -1; j < ymove + 2; j++) {
                                ivCell[i][j].setImageDrawable(drawCell[0]);
                                valueCell[i][j] = 0;
                                evaluation.addAvailablePosition(evaluation.positionToString(i,j));
                            }
                        }
                        btnBomb.setBackgroundResource(R.drawable.shape_radius_bg_gray);
                        //set the delay
                        ivCell[0][0].postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                checkAndTurn();
                            }
                        },1100);
                    }
                },500);
            }else {
                ivCell[xmove][ymove].setImageDrawable(drawCell[player_turn]);
                valueCell[xmove][ymove] = player_turn;
                checkAndTurn();
            }
        }else if(winner!=0)
            return;
        else
        {Toast.makeText(context, "Choose another position!", Toast.LENGTH_SHORT).show();
            //return;
        }


    }
    //check the state of the game, change the turn and manage the toast message
    private void checkAndTurn() {
        if (isBoardFull()) {
            Toast.makeText(context, "Draw!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (isWinning()) {
                if (winner == 1) {
                    String hint = "You Win!";
                    record.set(0,Integer.toString(Integer.valueOf(record.get(0))+1));
                    fileR.savebespoke(record,context);
                    if (!isAIMode){
                        hint = "Player One Win!";
                        tvPlayerTwo.setText("Player Two Lost!");
                    }
                    Toast.makeText(context, hint, Toast.LENGTH_SHORT).show();
                    tvPlayerOne.setText(hint);
                } else {
                    String hint = "Player One Lost!";
                    record.set(1,Integer.toString(Integer.valueOf(record.get(1))+1));
                    fileR.savebespoke(record,context);
                    if (isAIMode){
                        hint = "You Lost!";
                        Toast.makeText(context, hint, Toast.LENGTH_SHORT).show();
                    }else {
                        tvPlayerTwo.setText("Player Two Win!");
                        Toast.makeText(context, "Player Two Win!", Toast.LENGTH_SHORT).show();
                    }
                    tvPlayerOne.setText(hint);
                }
                return;
            }
        }
        if (player_turn == 1) {
            player_turn = 2;
            if (isAIMode) Ai_turn();
            else player_turn();
        } else  {
            player_turn = 1;
            player_turn();
        }
    }

    //return true if there is no empty space on the board
    private boolean isBoardFull() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (valueCell[i][j]==0)
                    return false;
            }
        }
        return true;
    }
    //check the winning state and return true when someone win
    //it will check all vectors
    private boolean isWinning() {

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

    }
    //check the provided vector to see if it is a winning state
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
            System.out.println("i "+i+" j "+j);
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

    //to make sure we check only inside the board
    private boolean inBoard(int i, int j) {
        if (i<0 || i>=boardSize || j<0 ||j>=boardSize)
            return false;
        return true;
    }
    //winning state
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
    //randomly choose the first player if AI mode, for two-player mode,
    // the first player is assigned black stone
    private void start_game() {
        playerOneBomb=0;
        playerTowBomb=0;
        if (isAIMode){
            player_turn= new Random().nextInt(2)+1;
            if (player_turn==1){
                first_move=false;
                player_turn();
            } else Ai_turn();
        }else {
            player_turn = 1;
            player_turn();
        }

    }

}
