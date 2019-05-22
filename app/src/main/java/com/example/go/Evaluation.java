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
    private Set<String> availablePositions = new HashSet<>();
    private Set<String> playerPositions = new HashSet<>();
    private Set<String> aiPositions = new HashSet<>();
    private enum Direction{Horizontal, Vertical, Diagonal_LeftUp, Diagonal_RightUp}

    String pattern_50000 = "00000"; // 01
    String pattern_4320 = "+0000+"; //02
    String pattern_720_01 = "+000++"; // 03
    String pattern_720_02 = "++000+"; //04
    String pattern_720_03 = "+00+0+"; //05
    String pattern_720_04 = "+0+00+"; //06
    String pattern_720_05 = "0000+"; //07
    String pattern_720_06 = "+0000"; //08
    String pattern_720_07 = "00+00"; //09
    String pattern_720_08 = "0+000"; //10
    String pattern_720_09 = "000+0"; //11
    String pattern_120_01 = "++00++"; //12
    String pattern_120_02 = "++0+0+"; //13
    String pattern_120_03 = "+0+0++";  //14
    String pattern_20_01 = "+++0++"; // 15
    String pattern_20_02 = "++0+++";// 16





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
        return 0;
    }



    // Constructor, it should contain all positions at the beginning
    public Evaluation(){
        String x = "", y = "";
        for (int i = 0; i < 12; i++){
            if (i < 10)
                x = "0" + i;
            else
                x = String.valueOf(i);
            for (int j =0; j < 12; j++){
                if (j < 10)
                    y = "0" + j;
                else
                    y = String.valueOf(j);

                availablePositions.add(x + y);
            }

        }

    }
    // adders and getters
    public void addPlayerPosition(String position){
        playerPositions.add(position);
        availablePositions.remove(position);
    }
    public void addAiPosition(String position){
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



    public int scoreCalculator(String position, Set me, Set other){
        int x = Integer.parseInt(position.substring(0, 2));
        int y = Integer.parseInt(position.substring(2, 4));
        String vertical = "";
        for (int i = 4; i > 0; i--){
            if (x - i >= 0){
                String p = positionToString(x - i, y);
                if (availablePositions.contains(p))
                    vertical += "+";
                else if (me.contains(p))
                    vertical += "0";
                else if (other.contains(p))
                    vertical += "A";
            }
        }
        vertical += "0";
        for (int i = 1; i <= 4; i++){
            if (x + i < 12){
                String p = positionToString(x + i, y);
                if (availablePositions.contains(p))
                    vertical += "+";
                else if (me.contains(p))
                    vertical += "0";
                else if (other.contains(p))
                    vertical += "A";
            }
        }
        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        String horizontal = "";
        for (int i = 4; i > 0; i--){
            if (y - i >= 0){
                String p = positionToString(x, y  - i);
                if (availablePositions.contains(p))
                    horizontal += "+";
                else if (me.contains(p))
                    horizontal += "0";
                else if (other.contains(p))
                    horizontal += "A";
            }
        }
        horizontal += "0";
        for (int i = 1; i <= 4; i++){
            if (y + i < 12){
                String p = positionToString(x, y  + i);
                if (availablePositions.contains(p))
                    horizontal += "+";
                else if (me.contains(p))
                    horizontal += "0";
                else if (other.contains(p))
                    horizontal += "A";
            }
        }


        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        //diagonal left_up -> right_down
        String diagonal_left_up = "";
        for (int i = 4; i > 0; i--){
            if (x - i >= 0 && y - i >= 0){
                String p = positionToString(x - i, y  - i);
                if (availablePositions.contains(p))
                    diagonal_left_up += "+";
                else if (me.contains(p))
                    diagonal_left_up += "0";
                else if (other.contains(p))
                    diagonal_left_up += "A";
            }
        }
        diagonal_left_up += "0";
        for (int i = 1; i <= 4; i++){
            if (x + i < 12 && y + i < 12){
                String p = positionToString(x + i, y  + i);
                if (availablePositions.contains(p))
                    diagonal_left_up += "+";
                else if (me.contains(p))
                    diagonal_left_up += "0";
                else if (other.contains(p))
                    diagonal_left_up += "A";
            }
        }

        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        //diagonal left_up -> right_down
        String diagonal_right_up = "";
        for (int i = 4; i > 0; i--){
            if (x - i >= 0 && y + i < 12){
                String p = positionToString(x - i, y  + i);
                if (availablePositions.contains(p))
                    diagonal_right_up += "+";
                else if (me.contains(p))
                    diagonal_right_up += "0";
                else if (other.contains(p))
                    diagonal_right_up += "A";
            }
        }
        diagonal_right_up += "0";
        for (int i = 1; i <= 4; i++){
            if (x + i < 12 && y - i >= 0){
                String p = positionToString(x + i, y  - i);
                if (availablePositions.contains(p))
                    diagonal_right_up += "+";
                else if (me.contains(p))
                    diagonal_right_up += "0";
                else if (other.contains(p))
                    diagonal_right_up += "A";
            }
        }
        String[] lines = {horizontal, vertical, diagonal_left_up, diagonal_right_up};
        int score = 0;
        for (String line : lines){
            if (line.contains(pattern_50000)){
                score += 50000;
                continue;
            }
            else if (line.contains(pattern_4320)){
                score += 4320;
                continue;
            }
            else if (line.contains(pattern_720_01)){
                score += 720;
                continue;
            }
            else if (line.contains(pattern_720_02)){
                score += 720;
                continue;
            }
            else if (line.contains(pattern_720_03)){
                score += 720;
                continue;
            }
            else if (line.contains(pattern_720_04)){
                score += 720;
                continue;
            }
            else if (line.contains(pattern_720_05)){
                score += 720;
                continue;
            }
            else if (line.contains(pattern_720_06)){
                score += 720;
                continue;
            }
            else if (line.contains(pattern_720_07)){
                score += 720;
                continue;
            }
            else if (line.contains(pattern_720_08)){
                score += 720;
                continue;
            }
            else if (line.contains(pattern_720_09)){
                score += 720;
                continue;
            }
            else if (line.contains(pattern_120_01)){
                score += 120;
                continue;
            }
            else if (line.contains(pattern_120_02)){
                score += 120;
                continue;
            }
            else if (line.contains(pattern_120_03)){
                score += 120;
                continue;
            }
            else if (line.contains(pattern_20_01)){
                score += 20;
                continue;
            }
            else if (line.contains(pattern_20_02)){
                score += 20;
                continue;
            }
        }
        return score;
    }



    public String choosePositionForAi(){
        int maxScore = Integer.MIN_VALUE;
        int currentScore = 0;
        String max_position = "";
        for (String position : availablePositions){
            currentScore = scoreCalculator(position, aiPositions, playerPositions);
            System.out.println("position: "+ position + "  score: " + currentScore);
            if (currentScore > maxScore){
                max_position = position;
                maxScore = currentScore;
            }
        }

        return max_position;
    }



    public String positionToString(int x, int y){
        String xx = "", yy = "";
        if (x < 10)
            xx = "0" + x;
        else
            xx = String.valueOf(x);
        if (y < 10)
            yy = "0" + y;
        else
            yy = String.valueOf(y);
        return xx + yy;
    }



//    public int getScoreForAi(String position){
//        boolean[][] isFrees = new boolean[4][2];
//        int[] counts = new int[4];
//
//        int x = Integer.parseInt(position.substring(0, 2));
//        int y = Integer.parseInt(position.substring(2, 4));
//        System.out.println(x + ", " + y);
//        int total_score = 0;
//
//        // check vertical line
//        // check up
//        boolean isUpFree = false;
//        boolean isDownFree = false;
//        int num_vertical = 1;
//        String pos = "";
//        for (int i = 1; i <= 4; i++){
//            if (x - i >= 0){
//                pos = positionToString(x - i, y);
//                if (aiPositions.contains(pos)){
//                    num_vertical++;
//                }else{
//                    if (availablePositions.contains(pos)){
//                        isUpFree = true;
//                    }
//                    break;
//                }
//            }else
//                break;
//        }
//        // check down
//        for (int i = 1; i <= 4; i++){
//            if (x + i < 12){
//                pos = positionToString(x + i, y);
//                if (aiPositions.contains(pos)){
//                    num_vertical++;
//                }else{
//                    if (availablePositions.contains(pos)){
//                        isDownFree = true;
//                    }
//                    break;
//                }
//            }else
//                break;
//        }
//        isFrees[0][0] = isUpFree;
//        isFrees[0][1] = isDownFree;
//        counts[0] = num_vertical;
//
//        // check horizontal line
//        boolean isLeftFree = false;
//        boolean isRightFree = false;
//        int num_horizontal = 1;
//
//        // check left
//        for (int i = 1; i <= 4; i++){
//            // decrease y, means move left
//            if (y - i >= 0){
//                pos = positionToString(x, y - i);
//                if (aiPositions.contains(pos)){
//                    num_horizontal++;
//                }else{
//                    if (availablePositions.contains(pos)){
//                        isLeftFree = true;
//                    }
//                    break;
//                }
//            }else
//                break;
//        }
//        // check right
//        for (int i = 1; i <= 4; i++){
//            // increase y, means move to the right
//            if (y + i < 12){
//                pos = positionToString(x, y + i);
//                if (aiPositions.contains(pos)){
//                    num_horizontal++;
//                }else{
//                    if (availablePositions.contains(pos)){
//                        isRightFree = true;
//                    }
//                    break;
//                }
//            }else
//                break;
//        }
//        isFrees[1][0] = isLeftFree;
//        isFrees[1][1] = isRightFree;
//        counts[1] = num_horizontal;
//
//        //diagonal left_up -> right_down
//        boolean isLeftUpFree = false;
//        boolean isRightDownFree = false;
//        int num_diagonal_left_up = 1;
//        // check left up
//        for (int i = 1; i <= 4; i++){
//            if (x - i >= 0 && y - i >= 0){
//                pos = positionToString(x - i, y - i);
//                if (aiPositions.contains(pos)){
//                    num_diagonal_left_up++;
//                }else{
//                    if (availablePositions.contains(pos)){
//                        isLeftUpFree = true;
//                    }
//                    break;
//                }
//            }else
//                break;
//        }
//        // check right down
//        for (int i = 1; i <= 4; i++){
//            if (x + i < 12 && y + i < 12){
//                pos = positionToString(x + i, y + i);
//                if (aiPositions.contains(pos)){
//                    num_diagonal_left_up++;
//                }else{
//                    if (availablePositions.contains(pos)){
//                        isRightDownFree = true;
//                    }
//                    break;
//                }
//            }else
//                break;
//        }
//        isFrees[2][0] = isLeftUpFree;
//        isFrees[2][1] = isRightDownFree;
//        counts[2] = num_diagonal_left_up;
//
//        //diagonal right_up -> left_down
//        boolean isRightUpFree = false;
//        boolean isLeftDownFree = false;
//        int num_diagonal_right_up = 1;
//        // check right up
//        for (int i = 1; i <= 4; i++){
//            if (x - i >= 0 && y + i < 12){
//                pos = positionToString(x - i, y + i);
//                if (aiPositions.contains(pos)){
//                    num_diagonal_right_up++;
//                }else{
//                    if (availablePositions.contains(pos)){
//                        isRightUpFree = true;
//                    }
//                    break;
//                }
//            }else
//                break;
//        }
//        // check left down
//        for (int i = 1; i <= 4; i++){
//            if (x + i < 12 && y - i >= 0){
//                pos = positionToString(x + i, y - i);
//                if (aiPositions.contains(pos)){
//                    num_diagonal_right_up++;
//                }else{
//                    if (availablePositions.contains(pos)){
//                        isLeftDownFree = true;
//                    }
//                    break;
//                }
//            }else
//                break;
//        }
//        isFrees[3][0] = isRightUpFree;
//        isFrees[3][1] = isLeftDownFree;
//        counts[3] = num_diagonal_right_up;
//
//        // give the score if place the piece in this way
//        return scoreHelper(isFrees, counts);
//    }
}
