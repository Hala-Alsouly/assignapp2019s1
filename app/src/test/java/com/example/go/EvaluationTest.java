package com.example.go;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * test whether the `Evaluation` class can figure out the best next step in the current situation
 * and correctly handle the state of each position on the board
 */

public class EvaluationTest {

    private String[][] playerPositions;
    private String[][] aiPositions;
    // The size of the board
    private static final int totalSize = 12*12;

    @Before
    public void setUp() throws Exception {
        // Real chess scene
        // Chess coordinates of player
        playerPositions = new String[][]{
                {"02","06"},{"03","05"},{"04","05"},{"05","05"},{"06","04"},{"02","08"},{"06","06"}
        };
        // Chess coordinates of ai
        aiPositions = new String[][]{
                {"02","05"},{"03","07"},{"04","06"},{"04","04"},{"05","04"},{"06","05"}
        };
    }

    @Test
    public void addPlayerPosition() {
        Evaluation evaluation = new Evaluation();
        String p1 = playerPositions[0][0]+playerPositions[0][1];
        String p2 = playerPositions[1][0]+playerPositions[1][1];
        evaluation.addPlayerPosition(p1);
        evaluation.addPlayerPosition(p2);
        assertEquals(evaluation.getPlayerPositions().size(),2);
        assertTrue(evaluation.getPlayerPositions().contains(p1));
        assertTrue(evaluation.getPlayerPositions().contains(p2));

    }

    @Test
    public void addAiPosition() {
        Evaluation evaluation = new Evaluation();
        String p1 = playerPositions[0][0]+playerPositions[0][1];
        String p2 = playerPositions[1][0]+playerPositions[1][1];
        evaluation.addAiPosition(p1);
        evaluation.addAiPosition(p2);
        assertEquals(evaluation.getAiPositions().size(),2);
        assertTrue(evaluation.getAiPositions().contains(p1));
        assertTrue(evaluation.getAiPositions().contains(p2));
    }

    @Test
    public void addAvailablePosition() {
        Evaluation evaluation = new Evaluation();
        String p1 = playerPositions[0][0]+playerPositions[0][1];
        String p2 = playerPositions[1][0]+playerPositions[1][1];
        String p3 = playerPositions[2][0]+playerPositions[2][1];
        evaluation.addPlayerPosition(p1);
        evaluation.addPlayerPosition(p2);
        evaluation.addAvailablePosition(p1);
        assertEquals(evaluation.getPlayerPositions().size(),1);
        assertEquals(evaluation.getAvailablePositions().size(), totalSize -1);

        evaluation.addAiPosition(p3);
        evaluation.addAvailablePosition(p3);
        assertEquals(evaluation.getAiPositions().size(),0);
        assertEquals(evaluation.getAvailablePositions().size(), totalSize -1);

    }


    @Test
    public void choosePositionForAi() {
        Evaluation evaluation = new Evaluation();
        for (String[] playerPosition : playerPositions) {
            evaluation.addPlayerPosition(playerPosition[0] + playerPosition[1]);
        }
        for (String[] aiPosition : aiPositions) {
            evaluation.addAiPosition(aiPosition[0] + aiPosition[1]);
        }
        assertEquals(evaluation.choosePositionForAi(),"0403");
    }

    @Test
    public void positionToString() {
        Evaluation evaluation = new Evaluation();
        assertEquals(evaluation.positionToString(0,2),"0002");
        assertEquals(evaluation.positionToString(10,2),"1002");
        assertEquals(evaluation.positionToString(10,12),"1012");

    }


    @Test
    public void positionTest(){
        Evaluation ev = new Evaluation();
        Set<String> availablePositions = new HashSet<>();
        Set<String> playerPositions = new HashSet<>();
        Set<String> aiPositions = new HashSet<>();

        ev.addAiPosition("0102");
        aiPositions.add("0102");
        ev.addPlayerPosition("1102");
        playerPositions.add("1102");


        assertEquals(aiPositions, ev.getAiPositions());
        assertEquals(playerPositions, ev.getPlayerPositions());
        assertFalse(ev.getAvailablePositions().contains("0102"));
        assertFalse(ev.getAvailablePositions().contains("1102"));
    }
}