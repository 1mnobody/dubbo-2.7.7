package com.wuzhsh.dubbo.test3;

public class Bird implements Animal {

    Animal cat;

    public void setCat(Animal cat) {
        this.cat = cat;
    }

    @Override
    public void say() {
        System.out.println("Bird");
        cat.say();
    }
}
