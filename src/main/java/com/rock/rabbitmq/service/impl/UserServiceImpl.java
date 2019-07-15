package com.rock.rabbitmq.service.impl;

import com.rock.rabbitmq.dao.UserDao;
import com.rock.rabbitmq.entity.User;
import com.rock.rabbitmq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

//import org.apache.dubbo.config.annotation.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public int addUser(User user) {
        User save = (User)userDao.save(user);
        if (StringUtils.isEmpty(save)) {
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    public int updateUser(User user) {
        User save = (User)userDao.save(user);
        if (StringUtils.isEmpty(save)) {
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    public int deleteUser(User user) {
        try {
            userDao.delete(user);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public User findUser(User user) {
        Optional byId = userDao.findById(user.getUserId());
        User u = (User)byId.get();
        if (StringUtils.isEmpty(user)) {
            return null;
        }else {
            return u;
        }
    }

    @Override
    public List<User> findAllUser(User user) {
        List<User> list = new ArrayList<>();
        Iterable all = userDao.findAll();
        for (Iterator it=all.iterator();it.hasNext();) {
            User next = (User)it.next();
            list.add(next);
        }
        return list;
    }
}
