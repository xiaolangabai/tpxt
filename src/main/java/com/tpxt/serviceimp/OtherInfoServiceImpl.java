package com.tpxt.serviceimp;

import com.tpxt.dao.OtherInfoDao;
import com.tpxt.service.OtherInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author zhoush
 */
@Service("otherInfoService")
public class OtherInfoServiceImpl implements OtherInfoService {

    @Resource
    private OtherInfoDao otherInfoDao;

    @Override
    public int enrollNum() {
        return otherInfoDao.enrollNum();
    }

    @Override
    public int enrollNumCondition(Map<String, Object> params) {
        return otherInfoDao.enrollNumCondition(params);
    }

    @Override
    public int sumTicket() {
        return otherInfoDao.sumTicket();
    }

    @Override
    public Long getEndTime(){
        long endTime = otherInfoDao.endTime();
        return endTime - System.currentTimeMillis()/1000;
    }

    @Override
    public boolean updateViewCount() {
        long count = otherInfoDao.viewCount();
        return otherInfoDao.updateViewCount(count + 1) > 0;
    }

    @Override
    public long viewCount() {
        return otherInfoDao.viewCount();
    }


}
