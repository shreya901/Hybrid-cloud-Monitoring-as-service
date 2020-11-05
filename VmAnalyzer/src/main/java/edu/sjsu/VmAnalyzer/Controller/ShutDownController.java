package edu.sjsu.VmAnalyzer.Controller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;

public class ShutDownController implements ApplicationContextAware {
    
    private static  ApplicationContext context;
    
    public  static void shutdownContext() {
        ((ConfigurableApplicationContext) context).close();
    }


 
    @Override
    public  void setApplicationContext(ApplicationContext ctx) throws BeansException {
        ShutDownController.context = ctx;
        
    }
}