package com.sdx.platform.util.db.services;

import org.jvnet.hk2.annotations.Service;
import org.springframework.stereotype.Component;

import com.sdx.platform.util.db.entities.MachineMetrics;
import com.sdx.platform.util.db.entities.MachineMetricsFailures;
@Service
@Component
public interface MachineMetricFailureService {

	public Iterable<MachineMetricsFailures> findAll();

	public MachineMetricsFailures save(MachineMetricsFailures MachineMetricsFailures);

	public void delete(int id);

	public MachineMetricsFailures findById(int id);

	
	void deleteall();

	Iterable<MachineMetricsFailures> findAllDec();

	// public Product update(ObjectId _id,Product product);

}
