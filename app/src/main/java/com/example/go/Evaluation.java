package com.example.go;

import java.util.HashSet;
import java.util.Set;

public class Evaluation {
    private Set<int[]> availablePositions;
    private Set<int[]> playerPositions = new HashSet<>();
    private Set<int[]> aiPositions = new HashSet<>();

    final int ALL5 = 1000000;
    final int Free4 = 100000;
    final int TwoSleep4 = 100000;
    final int Sleep4_Free3 = 100000;
    final int TwoSleep3 = 10000;
    final int Sleep3_Free3 = 10000;
    final int Sleep4 = 5000;
    final int Free3 = 5000;
    final int TwoFree2 = 1000;
    final int Sleep3 = 1000;
    final int Free2 = 100;
    final int Free1 = 10;

    // Constructor, it should contain all positions at the beginning
    public Evaluation(Set positions){
        availablePositions = positions;
    }

    public void setPlayPosition(int[] position){
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


    public int evaluateAi(){
        int maxScore = Integer.MIN_VALUE;
        int currentScore = 0;
        for (int[] position : availablePositions){



        }
        return maxScore;
    }

    public int getScore(int[] position){

        int x = position[0];
        int y = position[1];
        int total_score = 0;

        // check horizontal line
        // check left
        boolean isLeftFree = false;
        boolean isRightFree = false;
        int num_horizontal = 1;
        for (int i = 1; i <= 4; i++){
            int[] left = {x - i, y};
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
            int[] left = {x + i, y};
            if (aiPositions.contains(left)){
                num_horizontal++;
            }else{
                if (availablePositions.contains(left)){
                    isRightFree = true;
                }
                break;
            }
        }
        // check vertical line
        boolean isUpFree = false;
        boolean isDownFree = false;
        int num_vertical = 1;
        for (int i = 1; i <= 4; i++){
            int[] up = {x, y + i};
            if (aiPositions.contains(up)){
                num_horizontal++;
            }else{
                if (availablePositions.contains(up)){
                    isUpFree = true;
                }
                break;
            }
        }
        // check right
        for (int i = 1; i <= 4; i++){
            int[] left = {x, y};
            if (aiPositions.contains(left)){
                num_horizontal++;
            }else{
                if (availablePositions.contains(left)){
                    isRightFree = true;
                }
                break;
            }
        }

        //diagonal left_top -> right_bottom

        //diagonal right_top -> left_bottom
        return 0;
    }


}
