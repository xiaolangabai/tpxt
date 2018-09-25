package com.tpxt.serviceimp;

import com.tpxt.entity.User;
import com.tpxt.utils.TokenManagerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TokenCleanerSchedule {

    private Logger logger = LoggerFactory.getLogger(TokenCleanerSchedule.class);

    /**
     * 每30分钟清理一次tokne
     */
    @Scheduled(fixedRate = 30 * 60 * 1000)
    public void clean(){
        logger.info("定期清理token");
        Map<String, Object> tokenManager = TokenManagerUtil.getTokenManager();
        for(Map.Entry<String, Object> entry : tokenManager.entrySet()){
            User user = (User) entry.getValue();
            if(System.currentTimeMillis() - user.getExpireTime() > 30*60*1000L){
                tokenManager.remove(entry.getKey());
            }
        }
        logger.info("清理token结束");
    }
}
