package com.tjpanda88.stock.analysis.batch.item;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.util.Assert;

import com.tjpanda88.stock.analysis.batch.item.file.JsonMaper;
import com.tjpanda88.stock.analysis.batch.util.HttpUtils;

public class HttpItemReader<T> extends CreateListItemReader<String,T> {

	private JsonMaper<T> jsonMapper;
	
	/**
	 * Setter for line mapper. This property is required to be set.
	 * @param jsonMapper maps line to item
	 */
	public void setLineMapper(JsonMaper<T> jsonMapper) {
		this.jsonMapper = jsonMapper;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		Assert.notNull(jsonMapper, "LineMapper is required");
	}

	protected List<T> createOutputList(String inputItem) throws Exception {
		List<T> list = new ArrayList<T>();
		String responseBody = HttpUtils.getResponseByUtil(inputItem);
		JSONArray customersArray = JSONArray.fromObject(responseBody);

		for (int i = 0; i < customersArray.size(); i++) {
			JSONObject jsonObject = customersArray.getJSONObject(i);
			list.add(jsonMapper.mapLine(jsonObject, i));
		}

		return list;
	}

}
