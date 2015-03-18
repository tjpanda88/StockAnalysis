package com.tjpanda88.stock.analysis.batch.item;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public abstract class CreateListItemReader<S,T> implements ItemReader<T> , InitializingBean {
	
	private static final Log logger = LogFactory.getLog(CreateListItemReader.class);

	private List<T> outputList;
	
	private List<S> inputList;
	
	public void setInputList(List<S> inputList) {
		if (AopUtils.isAopProxy(inputList)) {
			this.inputList = inputList;
		}
		else {
			this.inputList = new ArrayList<S>(inputList);
		}
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(inputList, "LineMapper is required");
	}

    @Override
	public T read() {
    	if (outputList==null  || outputList.isEmpty()) {
    		if (inputList!=null  && !inputList.isEmpty()) {
    			try {
					outputList = createOutputList(inputList.remove(0));
				} catch (Exception e) {
					logger.error(null,e);
					outputList = null;
				}
    		}
		}

		if (outputList!=null  && !outputList.isEmpty()) {
			return outputList.remove(0);
		}
		return null;
	}
    
    protected abstract List<T> createOutputList(S inputItem) throws Exception;
}