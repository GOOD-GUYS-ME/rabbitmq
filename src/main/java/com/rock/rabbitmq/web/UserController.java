package com.rock.rabbitmq.web;

import com.rock.rabbitmq.entity.User;
import com.rock.rabbitmq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/test")
    public User test(User user){
        user.setAge(10);
        user.setName("李蛋");
        userService.addUser(user);
        User u = userService.findUser(user);
        System.out.println("user:"+u);
        System.out.println("userService:"+userService+"controller");
        return user;
    }
}
