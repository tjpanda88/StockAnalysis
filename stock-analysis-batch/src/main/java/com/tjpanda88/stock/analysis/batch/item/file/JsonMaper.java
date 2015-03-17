package com.tjpanda88.stock.analysis.batch.item.file;

import net.sf.json.JSONObject;

public interface JsonMaper<T> {

	/**
	 * Implementations must implement this method to map the provided line to 
	 * the parameter type T.  The line number represents the number of lines
	 * into a file the current line resides.
	 * 
	 * @param line to be mapped
	 * @param lineNumber of the current line
	 * @return mapped object of type T
	 * @throws Exception if error occurred while parsing.
	 */
	T mapLine(JSONObject line, int lineNumber) throws Exception;
}
