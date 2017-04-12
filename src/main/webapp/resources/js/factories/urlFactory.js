'use strict';

var urlFactory = function () {
	var constats = {
		getDocUrl: 'getDocuments',
		uploadFileUrl: 'parseFile',
		defaultDocsNum: 1000
	};
	var docDefUrl = constats.getDocUrl + '?length=';
	var _uploadFile = function (fileType) {
		return constats.uploadFileUrl + '?fileType=' + fileType;
	}
	var _getUploadedFiles = function () {
		return docDefUrl + "&collectionName=uploadedFile&start=0&length=";
	};
	var _getDocs = function (collectionName, filters, sortConfig, isCsv) {
		var url = docDefUrl + "&collectionName=" + collectionName;
		if (isCsv) {
			url = constats.getDocUrl + "?getCsv=true&collectionName=" + collectionName;
		}
		if (filters) {
			var filterstr = JSON.stringify(filters);
			url += ("&filters=" + filterstr);
		}
		if (sortConfig) {
			url += ("&sortField=" + sortConfig.sortField + "&sortType=" + sortConfig.sortType);
		}
		return url
	};
	return {
		uploadFile: function (fileType) {
			return encodeURI(_uploadFile(fileType));
		},
		getUploadedFiles: function () {
			return encodeURI(_getUploadedFiles());
		},
		getDocs: function (collectionName,filters,sortConfig) {
			return  encodeURI(_getDocs(collectionName, filters, sortConfig, false));
		},
		getCsv: function (collectionName, filters) {
			return encodeURI(_getDocs(collectionName, filters, null, true));
		}
	}
}($);