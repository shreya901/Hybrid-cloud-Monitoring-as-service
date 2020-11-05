package edu.sjsu.AnalyzerPerMin.Controller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;



public class ShutDownController implements ApplicationContextAware {
    
    public static ApplicationContext context;
    
    public  static void shutdownContext() {
        ((ConfigurableApplicationContext) context).close();
    }


 
    @Override
    public  void setApplicationContext(ApplicationContext ctx) throws BeansException {
        ShutDownController.context = ctx;
        
    }
}
