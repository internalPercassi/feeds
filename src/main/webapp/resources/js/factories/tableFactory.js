'use strict';

var tableFactory = function () {

	var dataArr = [];
	var colArr = [];
	
	var _getRowsForDatatables = function (jsonData) {
		/*il formato che abbiamo in ingresso è questo
		 * {data:[{key1:value1,key2:value2,key2:value2},...],recordsTotal:x}
		 * in uscita questo */
		dataArr = [];
		$.map(jsonData.data, function (el) {
			var objArr = [];
			$.each(el, function (key, value) {
				if (value && typeof value === 'object'){
					var keys = Object.keys(value);
					value = value[keys[0]];
				}
				objArr.push(value);
			});
			dataArr.push(objArr);
		});
		return  dataArr;
	};
	
	var _getColumnsForDatatables = function (jsonData) {
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
	}
	var _downloadCsv = function (collectionName) {
		var url = urlFactory.getCsv(collectionName, filterFactory.getFilters());
		var form$ = $('<form/>').attr("method", "post");
		form$.attr('action', url);
		$(document.body).append(form$);
		form$.submit();
		form$.remove();
	};	

	return {
		downloadCsv: function (collectionName) {
			return _downloadCsv(collectionName);
		},
		getColumnsForDatatables: function (jsonData) {
			return _getColumnsForDatatables(jsonData);
		},
		getRowsForDatatables: function (jsonData) {
			return _getRowsForDatatables(jsonData);
		}
	}
}();