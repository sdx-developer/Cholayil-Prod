package com.sdx.platform.util.db.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.sdx.platform.util.db.entities.MachineMetrics;
import com.sdx.platform.util.db.entities.MachineMetricsFailures;

@Component
@Repository
public interface MachineMetricFailureRepo extends CrudRepository<MachineMetricsFailures, Integer>{
	
	@Query(value = "SELECT transid,payload,payloadtype,trandtm,streamtype,failurereason,retrycount,statusflag FROM machinemetricsfailures ORDER BY transid ASC",nativeQuery = true)
	Iterable<MachineMetricsFailures> findAllDesc();
	
	
	

}
