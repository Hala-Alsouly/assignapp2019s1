package com.example.go;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class EvaluationTest {



    @Test
    public void positionToString() {
        Evaluation ev = new Evaluation();
        assertEquals("0102", ev.positionToString(1, 2));
        assertEquals("1102", ev.positionToString(11, 2));
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