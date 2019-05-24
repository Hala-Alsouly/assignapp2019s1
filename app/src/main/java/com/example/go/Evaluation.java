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
    String pattern_20_02 = "++0+++";// 1

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
    public void addAvailablePosition(String position){
        if (aiPositions.contains(position)){
            aiPositions.remove(position);
            availablePositions.add(position);
        }
        else if (playerPositions.contains(position)){
            playerPositions.remove(position);
            availablePositions.add(position);
        }
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

    /**
     * The heuristic function
     * @param position the position that will place a piece in next step
     * @param me Ai or player
     * @param other player or Ai
     * @return the max move from Ai
     */

    public int scoreCalculator(String position, Set me, Set other){
        // Check the 4 lines in four directions
        // According to the status, generate the string which represent the pattern
        int x = Integer.parseInt(position.substring(0, 2));
        int y = Integer.parseInt(position.substring(2, 4));
        String vertical = "";
        String vertical_other = "";
        for (int i = 4; i > 0; i--){
            if (x - i >= 0){
                String p = positionToString(x - i, y);
                if (availablePositions.contains(p)){
                    vertical += "+";
                    vertical_other += "+";
                }

                else if (me.contains(p)){
                    vertical += "0";
                    vertical_other += "A";
                }

                else if (other.contains(p)){
                    vertical += "A";
                    vertical_other += "0";
                }

            }
        }
        vertical += "0";
        vertical_other += "0";
        for (int i = 1; i <= 4; i++){
            if (x + i < 12){
                String p = positionToString(x + i, y);
                if (availablePositions.contains(p)){
                    vertical += "+";
                    vertical_other += "+";
                }
                else if (me.contains(p)){
                    vertical += "0";
                    vertical_other += "A";
                }

                else if (other.contains(p)){
                    vertical += "A";
                    vertical_other += "0";
                }
            }
        }
        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        String horizontal = "";
        String horizontal_other = "";
        for (int i = 4; i > 0; i--){
            if (y - i >= 0){
                String p = positionToString(x, y  - i);
                if (availablePositions.contains(p)){
                    horizontal += "+";
                    horizontal_other += "+";
                }

                else if (me.contains(p)){
                    horizontal += "0";
                    horizontal_other += "A";
                }

                else if (other.contains(p)){
                    horizontal += "A";
                    horizontal_other += "0";
                }
            }
        }
        horizontal += "0";
        horizontal_other += "0";
        for (int i = 1; i <= 4; i++){
            if (y + i < 12){
                String p = positionToString(x, y  + i);
                if (availablePositions.contains(p)){
                    horizontal += "+";
                    horizontal_other += "+";
                }
                else if (me.contains(p)){
                    horizontal += "0";
                    horizontal_other += "A";
                }
                else if (other.contains(p)){
                    horizontal += "A";
                    horizontal_other += "0";
                }
            }
        }


        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        //diagonal left_up -> right_down
        String diagonal_left_up = "";
        String diagonal_left_up_other = "";
        for (int i = 4; i > 0; i--){
            if (x - i >= 0 && y - i >= 0){
                String p = positionToString(x - i, y  - i);
                if (availablePositions.contains(p)){
                    diagonal_left_up += "+";
                    diagonal_left_up_other += "+";
                }
                else if (me.contains(p)){
                    diagonal_left_up += "0";
                    diagonal_left_up_other += "A";
                }
                else if (other.contains(p)){
                    diagonal_left_up += "A";
                    diagonal_left_up_other += "0";
                }
            }
        }
        diagonal_left_up += "0";
        diagonal_left_up_other += "0";
        for (int i = 1; i <= 4; i++){
            if (x + i < 12 && y + i < 12){
                String p = positionToString(x + i, y  + i);
                if (availablePositions.contains(p)){
                    diagonal_left_up += "+";
                    diagonal_left_up_other += "+";
                }
                else if (me.contains(p)){
                    diagonal_left_up += "0";
                    diagonal_left_up_other += "A";
                }
                else if (other.contains(p)){
                    diagonal_left_up += "A";
                    diagonal_left_up_other += "0";
                }
            }
        }

        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        //diagonal left_up -> right_down
        String diagonal_right_up = "";
        String diagonal_right_up_other = "";
        for (int i = 4; i > 0; i--){
            if (x - i >= 0 && y + i < 12){
                String p = positionToString(x - i, y  + i);
                if (availablePositions.contains(p)){
                    diagonal_right_up += "+";
                    diagonal_right_up_other += "+";
                }
                else if (me.contains(p)){
                    diagonal_right_up += "0";
                    diagonal_right_up_other += "A";
                }
                else if (other.contains(p)){
                    diagonal_right_up += "A";
                    diagonal_right_up_other += "0";
                }

            }
        }
        diagonal_right_up += "0";
        diagonal_right_up_other += "0";
        for (int i = 1; i <= 4; i++){
            if (x + i < 12 && y - i >= 0){
                String p = positionToString(x + i, y  - i);
                if (availablePositions.contains(p)){
                    diagonal_right_up += "+";
                    diagonal_right_up_other += "+";
                }
                else if (me.contains(p)){
                    diagonal_right_up += "0";
                    diagonal_right_up_other += "A";
                }
                else if (other.contains(p)){
                    diagonal_right_up += "A";
                    diagonal_right_up_other += "0";
                }
            }
        }
        String[] lines = {horizontal, vertical, diagonal_left_up, diagonal_right_up};
        String[] lines_other = {horizontal_other, vertical_other, diagonal_left_up_other, diagonal_right_up_other};
        int score = 0;
        /**
         * Calculate how much benefit will gain if Ai place its place to attack for winning
         * The winning pattern will be the largest number
         */
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
        /**
         * Calculate how much benefit will gain if Ai place its place to defend player from winning
         * The winning pattern of player will be the second largest number
         */
        for (String line : lines_other){
            if (line.contains(pattern_50000)){
                score += 10000;
                continue;
            }
            else if (line.contains(pattern_4320)){
                score += 1320;
                continue;
            }
            else if (line.contains(pattern_720_01)){
                score += 320;
                continue;
            }
            else if (line.contains(pattern_720_02)){
                score += 320;
                continue;
            }
            else if (line.contains(pattern_720_03)){
                score += 320;
                continue;
            }
            else if (line.contains(pattern_720_04)){
                score += 320;
                continue;
            }
            else if (line.contains(pattern_720_05)){
                score += 320;
                continue;
            }
            else if (line.contains(pattern_720_06)){
                score += 320;
                continue;
            }
            else if (line.contains(pattern_720_07)){
                score += 320;
                continue;
            }
            else if (line.contains(pattern_720_08)){
                score += 320;
                continue;
            }
            else if (line.contains(pattern_720_09)){
                score += 320;
                continue;
            }
            else if (line.contains(pattern_120_01)){
                score += 320;
                continue;
            }
            else if (line.contains(pattern_120_02)){
                score += 320;
                continue;
            }
            else if (line.contains(pattern_120_03)){
                score += 320;
                continue;
            }
            else if (line.contains(pattern_20_01)){
                score += 2;
                continue;
            }
            else if (line.contains(pattern_20_02)){
                score += 2;
                continue;
            }
        }
        return score;
    }


    /**
     * check every available position, choose the one with the best score
     * @return
     */
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


    /**
     * A tool to transfer the integer to the String representation of the position
     * @return The position String
     */
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

}
