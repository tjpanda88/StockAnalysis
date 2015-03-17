package com.tjpanda88.stock.analysis.batch.item;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.tjpanda88.stock.analysis.batch.item.file.JsonMaper;

public class HttpItemReader<T> extends
		AbstractItemCountingItemStreamItemReader<T> implements InitializingBean {

	String url = "http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page=1&num=3000&sort=symbol&asc=1&node=sz_a&symbol=&_s_r_a=init";

	private List<T> list = new ArrayList<T>();
	
	private JsonMaper<T> lineMapper;
	
	/**
	 * Setter for line mapper. This property is required to be set.
	 * @param lineMapper maps line to item
	 */
	public void setLineMapper(JsonMaper<T> lineMapper) {
		this.lineMapper = lineMapper;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(lineMapper, "LineMapper is required");
	}

	public HttpItemReader() {
	}

	public HttpItemReader(String url) {
		this.url = url;
	}

	@Override
	protected T doRead() throws Exception {
		if (!list.isEmpty()) {
			return list.remove(0);
		}
		return null;
	}

	@Override
	protected void doOpen() throws Exception {
		this.setName(url);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(url);

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity)
								: null;
					} else {
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}

			};
			String responseBody = httpclient.execute(httpget, responseHandler);

			JSONArray customersArray = new JSONArray().fromObject(responseBody);

			
			for(int i=0;i<customersArray.size();i++){
				JSONObject jsonObject = customersArray.getJSONObject(i);
				list.add(lineMapper.mapLine(jsonObject, i));
			}

		} catch (Exception e) {
			e.printStackTrace();
			list = null;
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
				list = null;
			}
		}

	}

	@Override
	protected void doClose() throws Exception {
		// TODO Auto-generated method stub

	}

}
