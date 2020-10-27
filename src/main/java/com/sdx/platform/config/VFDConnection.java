package com.sdx.platform.config;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

import com.ghgande.j2mod.modbus.*;
import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.net.*;
import com.ghgande.j2mod.modbus.procimg.Register;
import com.ghgande.j2mod.modbus.util.*;
import com.mchange.v2.sql.filter.SynchronizedFilterDataSource;
import com.sdx.platform.EventHandling.AvailabilityEventGen;

import com.sdx.platform.config.Memory;
import groovy.lang.GroovyShell;
import com.sdx.platform.quartz.service.impl.ModulesServices;
import com.sdx.platform.util.db.services.impl.MachinemetricFailuresServiceImpl;

public class VFDConnection {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		/* The important instances of the classes mentioned before */

		// a = new DeleteFailureData();

		Runnable runner = new Runnable() {
			@Override
			public void run() {
				Thread t2 = new Thread(new Runnable() {

					@Override
					public void run() {

						try {
							// ut.println(":::::::::::::::::::::::::::::::::::::::inside for loop");
							ModulesServices a = new ModulesServices();
							a.updateRunModule("Stamping IoT",
									"{\"modName\":\"Stamping IoT\",\"modActive\":true,\"modPath\":\"/scripts/SMCam.groovy\",\"modCron\":\"Stamping Machine\",\"modDesc\":\"Stamping Module\",\"stationCode\":\"TAD/EQP/084\",\"stationName\":\"STAMPING M/C 1\"}");

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				// t.sleep(2);
				t2.start();

				Thread t3 = new Thread(new Runnable() {

					@Override
					public void run() {

						try {
							// \":\"Stamping
							// IoT\",\"modActive\":true,\"modPath\":\"/scripts/SMCam.groovy\",\"modCron\":\"Stamping
							// Machine\",\"modDesc\":\"Stamping
							// Module\",\"stationCode\":\"TAD/EQP/084\",\"stationName\":\"STAMPING M/C
							// 1\"}");
							ModulesServices a = new ModulesServices();

							a.updateRunModule("Flow Wrap",
									"{\"modNameFlow\":\"Flow Wrap\",\"modActive1\":true,\"modPath1\":\"/scripts/FWCam.groovy\",\"modCron1\":\"Flowwarap Machine\",\"modDesc1\":\"Flowwrap Module\",\"stationCode1\":\"TAD/EQP/084\",\"stationName1\":\"STAMPING M/C 1\"}");

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				// t.sleep(2);
				t3.start();

				//
				ModbusSerialMaster serialMaster = null; // the connection
				// new GroovyShell().parse( new File("C:\\IOT\\BaseDir\\scripts\\SMCam.groovy")
				// ).invokeMethod( "run", null ) ;

				TimeUnit time = TimeUnit.SECONDS;
				/* Open the connection */
				SerialParameters parameters = new SerialParameters();
				serialMaster = new ModbusSerialMaster(parameters);

				/* Variables for storying the parameters */
				String portname = "COM3"; // the name of the serial port to be used /// oUr Port Name might be Different
				int unitID = 1; // the unit identifier we will be talking to /// Our Device ID
				int startingRegister = 12740; // the reference, where to start reading from //Registry // reg2 machine
												// // reg 1 machine status
				int registerCount = 5; // the count of the input registers to read
				Register[] slaveResponse = new Register[registerCount];
				String machinevalue = null;
				/* Setup the serial parameters */

				parameters.setPortName(portname);
				parameters.setBaudRate(19200); // Our Baud Rate Here ...Our Baud Rate is 19200
				parameters.setDatabits(8); // Even WE also Have same DataBits
				parameters.setParity(AbstractSerialConnection.EVEN_PARITY);
				parameters.setStopbits(AbstractSerialConnection.ONE_STOP_BIT);
				parameters.setEncoding(Modbus.SERIAL_ENCODING_RTU); // We also Use SAme RTU communication.
				parameters.setEcho(false);
				String match = "0";
				String currentResult = null;
				String lastPushedResult = "start";
				int intialRun = 0;
				try {
					serialMaster.connect();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				while (true) {
					intialRun++;
					try {
						time.sleep(2);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {

					} catch (Exception exception) {
						exception.printStackTrace();
					}
					/* Read the first four holding registers */
					try {
						slaveResponse = serialMaster.readMultipleRegisters(unitID, startingRegister, registerCount);
						for (int i = 0; i < slaveResponse.length; i++) {
							// System.out.println("reg" + i + " = " + slaveResponse[i]);
							// System.out.println("reg 2 " + " = " + slaveResponse[2]);
							machinevalue = slaveResponse[2].toString();
							if (i == 2) {

								// System.out.println("I am here *******" + machinevalue);

								if (machinevalue.matches(match)) {
									currentResult = "start";
									System.out.println("start");
									if (!(currentResult == lastPushedResult)) {
										lastPushedResult = "start";
										// System.out.println("sending the start to avail");
										// Memory.clearAndAddProperty("stampingIOTStatus", "started");
										// Memory.clearAndAddProperty("flowwrapStatus", "started");

										try {
											AvailabilityEventGen.buildAvailabilityPayloadModule("start");
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

									}

								}

								else {
									currentResult = "stop";
									// System.out.println("stop");

									if (!(currentResult == lastPushedResult)) {

										if (intialRun == 1) {

											lastPushedResult = "start";

											try {
												AvailabilityEventGen.buildAvailabilityPayloadModule("stop");
											} catch (Exception e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}

											// AvailabilityEventGen.buildAvailabilityPayloadModule("stop");
										} else {
											lastPushedResult = "stop";

											// Memory.clearAndAddProperty("stampingIOTStatus", "started");
											// Memory.clearAndAddProperty("flowwrapStatus", "started");

											try {
												AvailabilityEventGen.buildAvailabilityPayloadModule("stop");
											} catch (Exception e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}

											// AvailabilityEventGen.buildAvailabilityPayloadModule("stop");
										}
									}
									// System.out.println("stop");
									// AvailabilityEventGen.buildAvailabilityPayloadModule("stop");

								}
							}
						}
						// System.out.println(machinevalue);

					} catch (ModbusException | JSONException e) {
						e.printStackTrace();
					}
//serialMaster.disconnect();

					// System.out.println("executing connecct state ***********************");
					try {
						// serialMaster.disconnect();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		};
		new Thread(runner).start();
	} // main
}
//}
