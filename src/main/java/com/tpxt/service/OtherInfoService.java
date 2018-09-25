package com.tpxt.service;

import java.util.Map;

public interface OtherInfoService {

    public int enrollNum();

    int enrollNumCondition(Map<String, Object> params);

    public int sumTicket();

    public Long getEndTime();

    public boolean updateViewCount();

    public long viewCount();
}
