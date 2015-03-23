package com.tjpanda88.stock.analysis.batch.item.file;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import com.tjpanda88.stock.analysis.batch.model.StockList;

public class StockListFieldSetMapper implements JsonMaper<StockList> {
	
	public StockList mapLine(JSONObject jsonObject, int lineNumber) {
		StockList stockListItem = new StockList();
		
		stockListItem.setCode(jsonObject.getString("code"));
		stockListItem.setName(jsonObject.getString("name"));
		stockListItem.setType(StringUtils.left(jsonObject.getString("symbol"), 2));
		
		return stockListItem;
	}

}
