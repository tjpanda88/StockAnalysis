package com.tjpanda88.stock.analysis.batch.decider;

import java.util.List;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.item.ExecutionContext;


public class ListSizeCheckDecider implements JobExecutionDecider {

	private String partitionListKey;

	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution,
			StepExecution stepExecution) {
		ExecutionContext jobExecutionContext = stepExecution.getJobExecution()
				.getExecutionContext();

		List<Object> partitionList = (List<Object>) jobExecutionContext
				.get(partitionListKey);

		if (partitionList == null || partitionList.size() == 0) {
			return new FlowExecutionStatus("COMPLETED");
		}

		return new FlowExecutionStatus("CONTINUE");
	}

	public String getPartitionListKey() {
		return partitionListKey;
	}

	public void setPartitionListKey(String partitionListKey) {
		this.partitionListKey = partitionListKey;
	}

}
