package com.example.froggame;

public class PlayerScore {
    private String playerName;
    private int bestScore;

    public PlayerScore(){}
    public PlayerScore(String playerName, int bestScore) {
        this.playerName = playerName;
        this.bestScore = bestScore;
    }

    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }

    public String getPlayerName(){
        return playerName;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    public int getBestScore() {
        return bestScore;
    }
}
