package com.tpxt.dao;

import com.tpxt.entity.Content;

import java.util.Map;

public interface VoteDao {

    public int addVote(Content content);

    public int voteRecord(Map<String, Object> params);

    public int addRecord(Map<String, Object> params);

}
