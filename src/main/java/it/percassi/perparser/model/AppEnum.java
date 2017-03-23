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

	public static enum ColumnNames {
		FACEBOOK("id","availability","condition","description","imageLink","link","title","price","brand","additionalImageLink","ageGroup","color","expirationDate","gender","itemGroupId","googleProductCategory","material","pattern","productType","salePrice","shipping","shippingWeight","customLabel0","customLabel1","customLabel2","customLabel3","customLabel4"),
		GL(),
		OS();

		private String[] names;

		private ColumnNames(String... names) {
			this.names = names;
		}

		public String[] getNames() {
			return this.names;
		}
	}
	
	
	public static enum FeedType{
		FACEBOOK("FacebookProduct"),
		GL("GL"),
		OS("OS");

		private String code;

		private FeedType(String code) {
			this.code = code;
		}

		public String getCode() {
			return this.code;
		}
	}
	
	
	public static enum FeedTitle{
		FACEBOOK("Facebook Product View"),
		GL("Stocks File View"),
		OS("Overselling File View");

		private String titleText;

		private FeedTitle(String titleText) {
			this.titleText = titleText;
		}

		public String getTitleText() {
			return this.titleText;
		}
	}
			
}
