package com.tjpanda88.stock.analysis.batch.listener;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class CreateDropJobFolderListener implements JobExecutionListener, InitializingBean{
	
	private static final Log logger = LogFactory.getLog(CreateDropJobFolderListener.class);
	
	private static String JOB_DIRECTORY_KEY = "job.temp.directory";

	private String baseDirectory;
	
	public void setBaseDirectory(String baseDirectory) {
		this.baseDirectory = baseDirectory;
	}
	

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(baseDirectory, "BasePath is required");
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		try {
			String directory = FilenameUtils.concat(baseDirectory, RandomStringUtils.randomAlphanumeric(64));
			
			FileUtils.forceMkdir(new File(directory));
			jobExecution.getExecutionContext().putString(JOB_DIRECTORY_KEY, directory);
		} catch (IOException e) {
			logger.error(null, e);
		}
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		try{
			String jobDirectory = jobExecution.getExecutionContext().getString(JOB_DIRECTORY_KEY);
			FileUtils.deleteDirectory(new File(jobDirectory));
		} catch (IOException e) {
			logger.error(null, e);
		}
	}
}
