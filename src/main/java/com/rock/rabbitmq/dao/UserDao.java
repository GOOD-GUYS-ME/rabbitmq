package com.rock.rabbitmq.dao;

import com.rock.rabbitmq.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

public interface UserDao extends CrudRepository<User,Integer> {
}
