package com.tjpanda88.stock.analysis.batch.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STOCK_LIST")
public class StockList implements Serializable {

	private static final long serialVersionUID = -8528854691896111209L;

	@Id
	@Column(nullable = false, name = "STOCK_CODE", length = 6)
	private String code;
	
	@Column(nullable = false, name = "STOCK_TYPE", length = 2)
	private String type;

	@Column(nullable = false, name = "STOCK_NAME", length = 30)
	private String name;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
