package com.tjpanda88.stock.analysis.batch.tasklet;

import java.util.Map;

import org.springframework.batch.core.scope.context.ChunkContext;

public class StockDetailFileDownloadTasklet extends FileDownloadTasklet{

	public String getUrl(ChunkContext chunkContext) {
		
		Map<String,Object> item = (Map<String,Object>)chunkContext.getStepContext().getStepExecutionContext().get("stock.partition.item");
		String stockCode = (String)item.get("STOCK_CODE");
		String stockType = (String)item.get("STOCK_TYPE");
		return super.getUrl(chunkContext).concat(stockCode).concat(".").concat(stockType);
	}
}
