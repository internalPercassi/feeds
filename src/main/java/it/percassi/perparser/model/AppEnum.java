/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.percassi.perparser.model;

/**
 *
 * @author Daniele Sperto
 */
public class AppEnum {

	public static enum FacebookAvailability {
		INSTOCK("in stock"),
		OUTOFSTOCK("out of stock");

		private String fbCode;

		private FacebookAvailability(String fbCode) {
			this.fbCode = fbCode;
		}

		public String getFbCode() {
			return fbCode;
		}

		public static FacebookAvailability fromString(String fbCode) {
			for (FacebookAvailability b : FacebookAvailability.values()) {
				if (b.name().equalsIgnoreCase(fbCode)) {
					return b;
				}
			}
			return OUTOFSTOCK;
		}
	}	
	
	public static enum FileType{
		FACEBOOK("FacebookProduct"),
		GL("GL"),
		OS("OS"),
		WAF("WAF");

		private String code;

		private FileType(String code) {
			this.code = code;
		}

		public String getCode() {
			return this.code;
		}
	}	
}
