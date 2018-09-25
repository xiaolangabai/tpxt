package com.tpxt.serviceimp;

import com.tpxt.dao.ContentDao;
import com.tpxt.entity.Content;
import com.tpxt.service.ContentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author zhoush
 */
@Service("contentService")
public class ContentServiceImpl implements ContentService {

    @Resource
    private ContentDao contentDao;

    @Override
    public List<Content> listByCondition(Map<String, Object> params) {
        return contentDao.listByCondition(params);
    }

    @Override
    public Content getContentById(int condition) {
        return contentDao.getContentById(condition);
    }

    @Override
    public List<Content> getContentByName(String condition) {
        return contentDao.getContentByName(condition);
    }

    @Override
    public boolean updateContent(Content content) {
        return contentDao.updateContent(content) > 0;
    }

    @Override
    public List<Content> listAll() {
        return contentDao.listAll();
    }

    @Override
    public List<Content> rank() {
        return contentDao.rank();
    }

    @Override
    public boolean updateChecked(Content content) {
        return contentDao.updateChecked(content) > 0;
    }

    @Override
    public List<Content> getContentAll(Map<String, Object> params){
        return contentDao.getContentAll(params);
    }

    @Override
    public List<Content> listByStatus(Map<String, Object> params) {
        return contentDao.listByStatus(params);
    }

    @Override
    public boolean delContent(Map<String, Object> params) {
        return contentDao.delContent(params) > 0;
    }


}
