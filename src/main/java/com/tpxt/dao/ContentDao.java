package com.tpxt.dao;

import com.tpxt.entity.Content;

import java.util.List;
import java.util.Map;

public interface ContentDao {

    public List<Content> listByCondition(Map<String,Object> params);

    public Content getContentById(int condition);

    List<Content> getContentByName(String condition);

    public int updateContent(Content content);

    public List<Content> listAll();

    public List<Content> rank();

    int updateChecked(Content content);

    List<Content> getContentAll(Map<String, Object> params);

    List<Content> listByStatus(Map<String, Object> params);

    int delContent(Map<String, Object> params);
}
