package com.ronnie.topscore.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Builder
@AllArgsConstructor
@Entity
public class Score {
    private @Id @GeneratedValue Long id;

    private Double score;

    @JsonProperty("dateTime")
    @JsonFormat(pattern="dd MMM yyyy HH:mm:ss")
    private Date dateTime;

    private String player;

    public Score() {}

    public Score(Double score, Date dateTime, String player) {
        this.score = score;
        this.player = player;
        this.dateTime = dateTime;
    }

    public Long getId() { return this.id; }

    public Double getScore() { return this.score; }

    public Date getDateTime() { return this.dateTime; }

    public String getPlayer() { return this.player; }

    public void setId(Long id) { this.id = id; }

    public void setScore(Double score) {
        this.score = score;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
