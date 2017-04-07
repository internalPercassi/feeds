/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.percassi.perparser.facade.model;

import it.percassi.perparser.exception.NotValidFilterException;
import it.percassi.perparser.facade.model.UploadedFileModel;
import it.percassi.perparser.service.parsers.model.FacebookFeed;
import it.percassi.perparser.service.parsers.model.GLmodel;
import it.percassi.perparser.service.parsers.model.OSmodel;
import it.percassi.perparser.service.parsers.model.WAFModel;

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

	
	public static enum FileType {
		FACEBOOK("FacebookProduct", FacebookFeed.class),
		GL("GL", GLmodel.class),
		OS("OS", OSmodel.class),
		WAF("WAF", WAFModel.class),
		UPLOADED_FILE("uploadedFile", UploadedFileModel.class);

		private String code;
		private Class modelClass;

		private FileType(String code, Class modelClass) {
			this.code = code;
			this.modelClass = modelClass;
		}

		public String getCode() {
			return this.code;
		}

		public Class getModelClass() {
			return this.modelClass;
		}

		public static FileType getByCode(String code) {
			for (FileType e : values()) {
				if (e.code.equals(code)) {
					return e;
				}
			}
			return null;
		}
	}

	public static enum MongoFilterOperator {
		EQUALS("$eq"),
		NOT_EQUALS("$ne"),
		GREATER("$gt"),
		LESSER("$lt");

		private String mongoCode;

		private MongoFilterOperator(String mongoCode) {
			this.mongoCode = mongoCode;
		}

		public String getMongoCode() {
			return mongoCode;
		}

		public static MongoFilterOperator fromString(String mongoCode) throws NotValidFilterException {
			for (MongoFilterOperator b : MongoFilterOperator.values()) {
				if (b.getMongoCode().equalsIgnoreCase(mongoCode)) {
					return b;
				}
			}
			throw new NotValidFilterException(mongoCode);
		}
	}
}
