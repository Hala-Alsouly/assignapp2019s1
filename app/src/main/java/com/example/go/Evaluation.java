package com.example.go;

import android.util.Log;

import java.util.HashSet;
import java.util.Set;

/***
 *    0 --------------------> x
 *      \
 *      \
 *      \
 *      \
 *      \ y
 *      
 *      pay attention, that's how coordinate sysyem set previously
 *      so all methods are written accordingly
 */



public class Evaluation {
    private Set<int[]> availablePositions = new HashSet<>();
    private Set<int[]> playerPositions = new HashSet<>();
    private Set<int[]> aiPositions = new HashSet<>();
    
    
    private int scoreHelper(boolean[][] isFrees, int[] counts){
        int num_All5 = 0;
        int num_Free4 = 0;
        int num_Sleep4 = 0;
        int num_Free3 = 0;
        int num_Sleep3 = 0;
        int num_Free2 = 0;
        int num_Sleep2 = 0;
        int num_Free1 = 0;
        int num_Sleep1 = 0;
        for (int i = 0; i < 4; i++){
            int count = counts[i];
            boolean isFree_0 = isFrees[i][0];
            boolean isFree_1 = isFrees[i][1];
            if (count == 5)
                num_All5++;
            else if (count == 4){
                if (isFree_0 && isFree_1)
                    num_Free4++;
                else
                    num_Sleep4++;
            }
            else if (count == 3){
                if (isFree_0 && isFree_1)
                    num_Free3++;
                else
                    num_Sleep3++;
            }
            else if (count == 2){
                if (isFree_0 && isFree_1)
                    num_Free2++;
                else
                    num_Sleep2++;
            }
            else if (count == 1){
                if (isFree_0 && isFree_1)
                    num_Free1++;
                else
                    num_Sleep1++;
            }
        }
        if (num_All5 != 0)
            return 10000;
        else if (num_Free4 >= 1 || num_Sleep4 >= 2 || (num_Sleep4 >= 1 && num_Free3 >= 1))
            return 1000;
        else if (num_Free3 >= 1 || num_Sleep3 >= 2 || (num_Sleep3 >= 1 && num_Free2 >= 1))
            return 100;
        else if (num_Free2 >= 1 || num_Sleep2 >= 2 || (num_Sleep1 >= 1 && num_Free1 >= 1))
            return 10;
        else if (num_Free1 >= 1)
            return 1;
        Log.i("check ai","1:" + num_Free1 +", " +"2:" + num_Free2 +", ");
        return 0;
    }

    public int getScoreForAi(int[] position){
        boolean[][] isFrees = new boolean[4][2];
        int[] counts = new int[4];
        
        int x = position[0];
        int y = position[1];
        int total_score = 0;

        // check vertical line
        // check up
        boolean isUpFree = false;
        boolean isDownFree = false;
        int num_vertical = 1;
        for (int i = 1; i <= 4; i++){
            int[] up = {x - i, y};
            if (aiPositions.contains(up)){
                num_vertical++;
            }else{
                if (availablePositions.contains(up)){
                    isUpFree = true;
                }
                break;
            }
        }
        // check down
        for (int i = 1; i <= 4; i++){
            int[] down = {x + i, y};
            if (aiPositions.contains(down)){
                num_vertical++;
            }else{
                if (availablePositions.contains(down)){
                    isDownFree = true;
                }
                break;
            }
        }
        isFrees[0][0] = isUpFree;
        isFrees[0][1] = isDownFree;
        counts[0] = num_vertical;

        // check horizontal line
        boolean isLeftFree = false;
        boolean isRightFree = false;
        int num_horizontal = 1;

        // check left
        for (int i = 1; i <= 4; i++){
            // decrease y, means move left
            int[] left = {x, y - i};
            if (aiPositions.contains(left)){
                num_horizontal++;
            }else{
                if (availablePositions.contains(left)){
                    isLeftFree = true;
                }
                break;
            }
        }
        // check right
        for (int i = 1; i <= 4; i++){
            // increase y, means move to the right
            int[] right = {x, y + i};
            if (aiPositions.contains(right)){
                num_horizontal++;
            }else{
                if (availablePositions.contains(right)){
                    isRightFree = true;
                }
                break;
            }
        }
        isFrees[1][0] = isLeftFree;
        isFrees[1][1] = isRightFree;
        counts[1] = num_horizontal;

        //diagonal left_up -> right_down
        boolean isLeftUpFree = false;
        boolean isRightDownFree = false;
        int num_diagonal_left_up = 1;
        // check left up
        for (int i = 1; i <= 4; i++){
            int[] left_up = {x - i, y - i};
            if (aiPositions.contains(left_up)){
                num_diagonal_left_up++;
            }else{
                if (availablePositions.contains(left_up)){
                    isLeftUpFree = true;
                }
                break;
            }
        }
        // check right down
        for (int i = 1; i <= 4; i++){
            int[] right_down = {x + i, y + i};
            if (aiPositions.contains(right_down)){
                num_diagonal_left_up++;
            }else{
                if (availablePositions.contains(right_down)){
                    isRightDownFree = true;
                }
                break;
            }
        }
        isFrees[2][0] = isLeftUpFree;
        isFrees[2][1] = isRightDownFree;
        counts[2] = num_diagonal_left_up;

        //diagonal right_up -> left_down
        boolean isRightUpFree = false;
        boolean isLeftDownFree = false;
        int num_diagonal_right_up = 1;
        // check right up
        for (int i = 1; i <= 4; i++){
            int[] right_up = {x - i, y + i};
            if (aiPositions.contains(right_up)){
                num_diagonal_right_up++;
            }else{
                if (availablePositions.contains(right_up)){
                    isRightUpFree = true;
                }
                break;
            }
        }
        // check right down
        for (int i = 1; i <= 4; i++){
            int[] left_down = {x + i, y - i};
            if (aiPositions.contains(left_down)){
                num_diagonal_right_up++;
            }else{
                if (availablePositions.contains(left_down)){
                    isLeftDownFree = true;
                }
                break;
            }
        }
        isFrees[3][0] = isRightUpFree;
        isFrees[3][1] = isLeftDownFree;
        counts[3] = num_diagonal_right_up;

        // give the score if place the piece in this way
        Log.i("counts", num_vertical + ", " + num_horizontal + ", " + num_diagonal_left_up + ", " + num_diagonal_right_up);
        return scoreHelper(isFrees, counts);
    }

    // Constructor, it should contain all positions at the beginning
    public Evaluation(){
        int[] position = new int[2];
        for (int i = 0; i < 12; i++){
            for (int j =0; j < 12; j++){
                position[0] = i;
                position[1] = j;
                availablePositions.add(position);
            }
        }
    }
    // setters and getters
    public void setPlayerPosition(int[] position){
        playerPositions.add(position);
        availablePositions.remove(position);
    }
    public void setAiPosition(int[] position){
        aiPositions.add(position);
        availablePositions.remove(position);
    }
    public Set getAvailablePositions(){
        return availablePositions;
    }
    public Set getPlayerPositions(){
        return playerPositions;
    }
    public Set getAiPositions(){
        return aiPositions;
    }


    public int[] choosePositiveForAi(){
        int maxScore = Integer.MIN_VALUE;
        int currentScore = 0;
        int[] max_position = {-1,-1};
      
        for (int[] position : availablePositions){
            currentScore = getScoreForAi(position);
            if (currentScore == 10000)
                return position;
            if (currentScore > maxScore){
                max_position = position;
                maxScore = currentScore;
            }
        }
        Log.i("check ai", "  max score: "+ maxScore + "  max position" + max_position[0] + ", " + max_position[1]);
        return max_position;
    }


    
    
 


}
