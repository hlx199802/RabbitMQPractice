package com.lurker.support.impl;

import com.lurker.support.face.FunctionService;
import com.lurker.support.face.UseFunctionInterface;

public class UseFunctionService implements UseFunctionInterface {

    private FunctionService functionService;

    public void setFunctionService(FunctionService functionService) {
        this.functionService = functionService;
    }

    public String sayHello(String word) {
        return functionService.sayHello(word);
    }

}
