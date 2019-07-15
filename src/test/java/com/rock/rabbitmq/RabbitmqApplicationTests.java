package com.rock.rabbitmq;

import com.rock.rabbitmq.entity.User;
import com.rock.rabbitmq.rabbtitmq.RabbitmqListener;
import com.rock.rabbitmq.rabbtitmq.Sender;
import com.rock.rabbitmq.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqApplicationTests {

    @Autowired
    Sender sender;

    @Autowired
    UserService userService;
    @Test
    public void contextLoads() {
        sender.send();
//        RabbitmqListener listener = new RabbitmqListener();
//        listener.process("hello");
        System.out.println("bb");
    }

    @Test
    public void test1(){
        User user=new User();
        user.setAge(10);
        user.setName("李蛋");
        int i = userService.addUser(user);
        System.out.println("i"+i);
    }
}
