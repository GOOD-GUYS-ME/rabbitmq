package com.rock.rabbitmq.service;




import com.rock.rabbitmq.entity.User;

import java.util.List;

public interface UserService {
    int addUser(User user);
    int updateUser(User user);
    int deleteUser(User user);
    User findUser(User user);
    List<User> findAllUser(User user);
}
