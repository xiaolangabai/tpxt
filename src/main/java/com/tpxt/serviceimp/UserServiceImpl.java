package com.tpxt.serviceimp;

import com.tpxt.dao.UserDao;
import com.tpxt.entity.User;
import com.tpxt.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhoush
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public User getUser(String username) {
        return userDao.getUser(username);
    }
}
