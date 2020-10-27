package com.sdx.platform.config;

import java.io.IOException;
import java.text.ParseException;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;

import com.sdx.platform.EventHandling.EventsFunction;
import com.sdx.platform.EventHandling.ShiftEventGen;

public class ShiftHandler extends TimerTask {

	@Override
	public void run() {
		
		
			//ShiftEventGen.buildShiftStatusPayload();
		
		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
			
			try {
		
				ShiftEventGen.buildShiftStatusPayloadModule();
				
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} 
			}
			});
			//t.sleep(2);
			t2.start();
		
		
		
	}

}
