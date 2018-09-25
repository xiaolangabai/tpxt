package com.tpxt.serviceimp;

import com.tpxt.dao.VoteDao;
import com.tpxt.entity.Content;
import com.tpxt.service.VoteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoush
 */
@Service("voteService")
public class VoteServiceImpl implements VoteService {

    @Resource
    private VoteDao voteDao;

    @Override
    public boolean addVote(Content content) {
        return voteDao.addVote(content) > 0;
    }

    @Override
    public boolean voteRecord(Map<String, Object> params) {
        return voteDao.voteRecord(params) > 0 ;
    }

    @Override
    public boolean addRecord(Map<String, Object> params) {
        return voteDao.addRecord(params) > 0;
    }

}
