package com.shun.log;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

public class DiyLog {


    public void before() {
        System.out.println("==========Before==========");
    }


    public void after() {
        System.out.println("==========After==========");
    }


}
