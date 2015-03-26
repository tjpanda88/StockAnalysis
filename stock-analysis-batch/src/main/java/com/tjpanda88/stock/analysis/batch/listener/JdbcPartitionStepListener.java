package com.tjpanda88.stock.analysis.batch.listener;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcPartitionStepListener extends StepExecutionListenerSupport {

	private static final Log logger = LogFactory
			.getLog(JdbcPartitionStepListener.class);

	private JdbcOperations jdbcTemplate;

	private String sql;

	private String partitionListKey;

	private String partitionItemKey;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		ExecutionContext stepExecutionContext = stepExecution
				.getExecutionContext();
		ExecutionContext jobExecutionContext = stepExecution.getJobExecution()
				.getExecutionContext();

		List<Map<String, Object>> partitionList = (List<Map<String, Object>>) jobExecutionContext
				.get(partitionListKey);
		if (partitionList == null) {
			partitionList = jdbcTemplate.queryForList(sql);
			jobExecutionContext.put(partitionListKey, partitionList);
		}

		if (partitionList != null & partitionList.size() != 0) {
			Map<String, Object> partitionItem = partitionList.remove(0);
			stepExecutionContext.put(partitionItemKey, partitionItem);
		}
	}

	/**
	 * The data source for connecting to the database.
	 *
	 * @param dataSource
	 *            a {@link DataSource}
	 */
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getPartitionListKey() {
		return partitionListKey;
	}

	public void setPartitionListKey(String partitionListKey) {
		this.partitionListKey = partitionListKey;
	}

	public String getPartitionItemKey() {
		return partitionItemKey;
	}

	public void setPartitionItemKey(String partitionItemKey) {
		this.partitionItemKey = partitionItemKey;
	}

}