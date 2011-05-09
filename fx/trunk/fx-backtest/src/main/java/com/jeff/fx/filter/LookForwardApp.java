package com.jeff.fx.filter;

import com.jeff.fx.backtest.AppCtx;
import com.jeff.fx.backtest.BackTestDataManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component("lookForwardApp")
public class LookForwardApp {
    
    private static Logger log = Logger.getLogger(LookForwardApp.class);

    @Autowired
    private LookForwardFrame frame;
    
    @Autowired
    private BackTestDataManager dataManager;
    
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("context-*.xml");
        LookForwardApp app = (LookForwardApp)ctx.getBean("lookForwardApp");
        AppCtx.initialise(ctx);
        app.run(); 
    }

    private void run() {
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("Slider.paintValue", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame.setVisible(true);
    }
}

