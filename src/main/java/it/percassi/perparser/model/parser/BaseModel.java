package it.percassi.perparser.model.parser;

import java.io.Serializable;

/**
 *
 * @author Daniele Sperto
 */
public class BaseModel implements Serializable{
	
	private String md5;

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}
	
	
}
