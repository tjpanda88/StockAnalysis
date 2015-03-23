package com.tjpanda88.stock.analysis.batch.tasklet;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.tjpanda88.stock.analysis.batch.util.HttpUtils;

public class FileDownloadTasklet implements Tasklet {

	private String url;
	
	private String baseDirectory = null;
	
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		String baseDirectory = (String)chunkContext.getStepContext().getJobExecutionContext().get("job.temp.directory");
		String responseBody = HttpUtils.getResponseByUtil(url);
		
		String downloadFile = FilenameUtils.concat(baseDirectory, RandomStringUtils.randomAlphanumeric(64));
		FileUtils.writeStringToFile(new File(downloadFile), responseBody);
		return RepeatStatus.FINISHED;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBaseDirectory() {
		return baseDirectory;
	}

	public void setBaseDirectory(String baseDirectory) {
		this.baseDirectory = baseDirectory;
	}

}
