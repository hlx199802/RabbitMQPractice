package com.lurker.support.impl;

import com.lurker.support.face.FunctionService;

public class FunctionServiceImpl implements FunctionService {
    public String sayHello(String word) {
        return "Hello, " + word + "!";
    }
}
