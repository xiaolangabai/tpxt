package com.tpxt.dao;

import java.util.Map;

public interface OtherInfoDao {

    public int enrollNum();

    int enrollNumCondition(Map<String, Object> params);

    public int sumTicket();

    public long endTime();

    public int updateViewCount(Long count);

    public long viewCount();
}
