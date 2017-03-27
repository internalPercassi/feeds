'use strict';
var dataService = function () {
	var dataArr = [];
	var colArr = [];
	var excludeAray = ['md5'];
	return {
		getRowsForDatatables: function (jsonData, excludeConfigPar) {
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
		getColumnsForDatatables: function (jsonData, excludeConfigPar) {
			/*il formato che abbiamo in ingresso è questo
			 * {data:[{key1:value1,key2:value2,key2:value2},...],recordsTotal:x}
			 * in uscita questo */
			var tmpArr = [];
			var el = jsonData.data[0];
			if (jsonData && jsonData.data && jsonData.data.length > 0) {
				$.each(el, function (key, value) {
					tmpArr.push({title: key});
				});
				colArr = tmpArr;
			}
			if (!colArr || colArr.length==0){
				return [{title:"no data found"}]; //todo: gestire un result set vuoto
			}
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
		uploadFileUrl: 'parseFile',
		defaultDocsNum: 1000
	};
	var docDefUrl = constats.getDocUrl + '?length=' + constats.defaultDocsNum;
	var _uploadFile = function (fileType) {
		return constats.uploadFileUrl + '?fileType=' + fileType;
	}
	var _getUploadedFiles = function () {
		return docDefUrl + "&collectionName=uploadedFile&start=0&length=1000";
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
		getDocs: function (collectionName,filters) {
			return  encodeURI(_getDocs(collectionName, filters, null, false));
		},
		getDocsFilter: function (collectionName, filters, sortConfig) {
			return encodeURI(_getDocs(collectionName, filters, sortConfig, false));
		},
		getCsv: function (collectionName, filters) {
			return encodeURI(_getDocs(collectionName, filters, null, true));
		}
	}
}($);



var filterService = function () {
	var filters = [];

	var _addFilter = function (field, searchOperator, searchVal) {
		if (!field || !searchOperator || !searchVal) {
			return;
		}
		
		var filter = {
			field: field,
			searchOperator: searchOperator,
			searchVal: searchVal
		};

		filters.forEach(function (value, i) {
			if (value.field == field) {
				return;
			}
		});

		filters.push(filter);
	}

	var _removeFilter = function (field) {
		var tmp = [];
		filters.forEach(function (value, i) {
			if (value.field == field) {
				tmp.push(value);
			}
		});
		filters = tmp;
	};

	var _reset = function () {
		filters = [];
	}
	var _getFilters = function () {
		var ret = [];
		filters.forEach(function (value, i) {
			ret.push(value);
		});
		return ret;
	};

	return {
		addFilter: function (field, searchOperator, searchVal) {
			return _addFilter(field, searchOperator, searchVal);
		},
		removeFilter: function () {
			return _removeFilter();
		},
		reset: function () {
			return _reset();
		},
		getFilters: function () {
			return _getFilters();
		}
	};
}()