package com.sdx.platform.util.db.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.sdx.platform.util.db.repositories.MachineMetricFailureRepo;
import com.sdx.platform.util.db.EventDbPush;
import com.sdx.platform.util.db.entities.MachineMetrics;
import com.sdx.platform.util.db.entities.MachineMetricsFailures;
import com.sdx.platform.util.db.services.MachineMetricFailureService;

@Service
@Component
public class MachinemetricFailuresServiceImpl implements MachineMetricFailureService {

	@Autowired(required = true)
	public MachineMetricFailureRepo MachineMetricFailureRepo;
	@Autowired
	MachineMetricFailureService MachineFailureService;


	@Override
	public Iterable<MachineMetricsFailures> findAll() {
		return MachineMetricFailureRepo.findAll();

	}

	@Override
	public void delete(int id) {
		MachineMetricFailureRepo.deleteById(id);

	}

	@Override
	public MachineMetricsFailures save(MachineMetricsFailures MachineMetricsFailures) {
	//	System.out.println("machinemetric failue " + MachineMetricsFailures.getPayload());
		return MachineMetricFailureRepo.save(MachineMetricsFailures);
	}

	@Override
	public MachineMetricsFailures findById(int id) {
		return MachineMetricFailureRepo.findById(id).orElse(null);
	}
	


	@Override
	public void deleteall() {	
		System.out.println("I AM IN DELETE ALL METHOD*******");
	MachineMetricFailureRepo.deleteAll();
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public Iterable<MachineMetricsFailures> findAllDec() {
		// TODO Auto-generated method stub
		return MachineMetricFailureRepo.findAllDesc();

	}

}
