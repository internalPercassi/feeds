'use strict';
var dataService = function () {
	var dataArr = [];
	var colArr = [];
	return {
		getRowsForDatatables: function (jsonData) {
			/*il formato che abbiamo in ingresso è questo
			 * {data:[{key1:value1,key2:value2,key2:value2},...],recordsTotal:x}
			 * in uscita questo */
			dataArr = [];
			$.map(jsonData.data, function (el) {
				var objArr = [];
				$.each(el, function (key, value) {
					objArr.push(value);
				});
				dataArr.push(objArr);
			});
			return  dataArr;
		},
		getColumnsForDatatables: function (jsonData) {
			/*il formato che abbiamo in ingresso è questo
			 * {data:[{key1:value1,key2:value2,key2:value2},...],recordsTotal:x}
			 * in uscita questo */
			colArr = [];
			var el = jsonData.data[0];
			$.each(el, function (key, value) {
				colArr.push({title: key});
			});
			return  colArr;
		},
		getData: function () {
			return dataArr;
		},
		getColumns: function () {
			return colArr;
		}
	}
}($);

var urlService = function () {
	var constats = {
		getDocUrl: 'getDocuments',
		uploadFileUrl: 'parseFile'
	}
	var _uploadFile = function (fileType) {
		return constats.uploadFileUrl + '?fileType=' + fileType;
	}
	var _getUploadedFiles = function () {
		return constats.getDocUrl + "?collectionName=uploadedFile&start=0&length=1000&exclude=md5";
	};
	var _getDocs = function (collectionName) {
		return constats.getDocUrl + "?collectionName=" + collectionName + "&start=0&length=1000&exclude=md5";
	};
	var _getDocsFilter = function (collectionName, filters, sortConfig) {
		var filterstr = JSON.stringify(filters);
		return _getDocs(collectionName) + "&sortField=" + sortConfig.sortField + "&sortType=" + sortConfig.sortType + "&filters=" + filterstr;
	};
	return {
		uploadFile: function (fileType) {
			return encodeURI(_uploadFile(fileType));
		},
		getUploadedFiles: function () {
			return encodeURI(_getUploadedFiles());
		},
		getDocs: function (collectionName) {
			return  encodeURI(_getDocs(collectionName));
		},
		getDocsFilter: function (collectionName, filters, sortConfig) {
			return encodeURI(_getDocsFilter(collectionName, filters, sortConfig));
		}
	}
}($);