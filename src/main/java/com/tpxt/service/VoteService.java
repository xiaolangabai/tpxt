package com.tpxt.service;

import com.tpxt.entity.Content;

import java.util.Map;

public interface VoteService {

    public boolean addVote(Content content);

    public boolean voteRecord(Map<String, Object> params);

    public boolean addRecord(Map<String, Object> params);

}
