package com.ronnie.topscore.model;

import java.util.Date;

public class ScoreTimeModel {
    private Double score;

    private Date time;

    public ScoreTimeModel(Double score, Date time) {
        this.score = score;
        this.time = time;
    }

    public Double getScore() {
        return score;
    }

    public Date getTime() {
        return time;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
