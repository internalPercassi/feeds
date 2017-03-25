'use strict';
var dataService = function () {
	return {
		getRowsForDatatables: function (jsonData) {
			/*il formato che abbiamo in ingresso è questo
			 * {data:[{key1:value1,key2:value2,key2:value2},...],recordsTotal:x}
			 * in uscita questo */
			var arr = [];
			$.map(jsonData.data, function (el) {
				var objArr = [];
				$.each(el, function (key, value) {
					objArr.push(value);
				});
				arr.push(objArr);
			});
			return  arr;
		},
		getColumnsForDatatables: function (jsonData) {
			/*il formato che abbiamo in ingresso è questo
			 * {data:[{key1:value1,key2:value2,key2:value2},...],recordsTotal:x}
			 * in uscita questo */
			var arr = [];
			var el = jsonData.data[0];
			$.each(el, function (key, value) {
				arr.push({title: key});
			});
			return  arr;
		}
	}
}($);
var urlService = function () {
	var constats = {
		getDocUrl: 'getDocuments',
		uploadFileUrl: 'parseFile'
	}
	return {
		getUploadedFiles: function () {
			return encodeURI(constats.getDocUrl + "?collectionName=uploadedFile&start=0&length=1000&exclude=md5");
		},
		getDocs: function (collectionName) {
			return encodeURI(constats.getDocUrl + "?collectionName="+collectionName+"&start=0&length=1000&exclude=md5");
		}
	}
}($);