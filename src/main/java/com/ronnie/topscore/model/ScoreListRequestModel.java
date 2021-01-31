package com.ronnie.topscore.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class ScoreListRequestModel {

    public List<String> playerList;

    @JsonFormat(pattern="dd MMM yyyy HH:mm:ss")
    public Date startDate;

    @JsonFormat(pattern="dd MMM yyyy HH:mm:ss")
    public Date endDate;

}
