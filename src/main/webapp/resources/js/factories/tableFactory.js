'use strict';

var tableFactory = function () {

	var dataArr = [];
	var colArr = [];
	
	var _getRowsForDatatables = function (jsonData) {
		/*il formato che abbiamo in ingresso è questo
		 * {data:[{key1:value1,key2:value2,key2:value2},...],recordsTotal:x,columnNames:['a','b,'c']}
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
		getRowsForDatatables: function (jsonData) {
			return _getRowsForDatatables(jsonData);
		}
	}
}();