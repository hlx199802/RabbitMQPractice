package com.lurker.support.configuration;

import com.lurker.support.face.FunctionService;
import com.lurker.support.face.UseFunctionInterface;
import com.lurker.support.impl.FunctionServiceImpl;
import com.lurker.support.impl.UseFunctionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.lurker")
public class ConfigurationClass {

    @Bean
    public FunctionService functionService() {
        return new FunctionServiceImpl();
    }

    @Bean
    public UseFunctionInterface useFunctionInterface() {
        return new UseFunctionService() {{
            this.setFunctionService(functionService());
        }};
    }

}
