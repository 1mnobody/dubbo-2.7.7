package com.wuzhsh.dubbo.test2;

import org.apache.dubbo.common.URL;

public class Bird implements Animal {

    Animal friend;

    public void setFriend(Animal friend) {
        this.friend = friend;
    }

    @Override
    public void say(URL url) {
        System.out.println("Bird");
        friend.say(url);
    }
}
