package com.ronnie.topscore.model;

import java.util.List;

public class PlayerHistoryResponseModel {

    private ScoreTimeModel topScore;

    private ScoreTimeModel lowScore;

    private Double averageScore;

    List<ScoreTimeModel> playerScoreList;

    public PlayerHistoryResponseModel(ScoreTimeModel topScore,
                                      ScoreTimeModel lowScore,
                                      Double averageScore,
                                      List<ScoreTimeModel> playerScoreList) {
        this.topScore = topScore;
        this.lowScore = lowScore;
        this.averageScore = averageScore;
        this.playerScoreList = playerScoreList;
    }

    public ScoreTimeModel getTopScore() {
        return topScore;
    }

    public ScoreTimeModel getLowScore() {
        return lowScore;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public List<ScoreTimeModel> getPlayerScoreList() {
        return playerScoreList;
    }

    public void setTopScore(ScoreTimeModel topScore) {
        this.topScore = topScore;
    }

    public void setLowScore(ScoreTimeModel lowScore) {
        this.lowScore = lowScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    public void setPlayerScoreList(List<ScoreTimeModel> playerScoreList) {
        this.playerScoreList = playerScoreList;
    }
}
