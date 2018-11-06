package com.lurker.support.main;

import com.lurker.support.configuration.ConfigurationClass;
import com.lurker.support.face.UseFunctionInterface;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String [] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ConfigurationClass.class);

        UseFunctionInterface useFunctionService = context.getBean(UseFunctionInterface.class);

        System.out.println(useFunctionService.sayHello("Jack"));

        context.close();

    }

}
