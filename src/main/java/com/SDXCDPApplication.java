package com;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.codehaus.groovy.control.CompilationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;


import com.sdx.platform.EventHandling.PerformanceEventGen;
import com.sdx.platform.config.Memory;

import com.sdx.platform.config.ShiftSchedularModule;
import com.sdx.platform.quartz.DefaultActions;
import com.sdx.platform.quartz.service.impl.ModulesServices;
import com.sdx.platform.util.db.repositories.MachineMetricFailureRepo;
import com.sdx.platform.util.db.services.MachineMetricFailureService;
import com.sdx.platform.util.db.services.impl.MachinemetricFailuresServiceImpl;

import groovy.lang.GroovyShell;
@SpringBootApplication
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class SDXCDPApplication {
	
	
    public static void main(String[] args) throws CompilationFailedException, ParseException, InterruptedException, IOException {
    	System.setProperty("server.servlet.context-path", "/rk");
    	Memory.init();
    	DefaultActions.init();
    	SpringApplication.run(SDXCDPApplication.class, args);	
    	Memory.clearAndAddProperty("totalProductionPieces", "0");
		Memory.clearAndAddProperty("totalGoodPieces", "0");
		Memory.clearAndAddProperty("shiftStatus", "stop");
	
		
      //  ApplicationContext applicationContext = SpringApplication.run(SDXCDPApplication.class, args) ;
		
        //MachinemetricFailuresServiceImpl service = applicationContext.getBean(MachinemetricFailuresServiceImpl.class);
	        //service.deleteall();
		
		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
			
			try {	
				com.sdx.platform.config.VFDConnection.main(args);
				
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} 
			}
			});
			
			t2.start();
		
			
	
			
	
    	
    	

    }
}
