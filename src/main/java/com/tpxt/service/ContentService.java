package com.tpxt.service;

import com.tpxt.entity.Content;

import java.util.List;
import java.util.Map;

public interface ContentService {

    public List<Content> listByCondition(Map<String, Object> params);

    Content getContentById(int condition);

    public List<Content> getContentByName(String condition);

    public boolean updateContent(Content content);

    public List<Content> listAll();

    public List<Content> rank();

    boolean updateChecked(Content content);

    List<Content> getContentAll(Map<String, Object> params);

    List<Content> listByStatus(Map<String, Object> params);

    boolean delContent(Map<String, Object> params);
}
