'use strict';

var tableFactory = function () {
	var myTable;
	var collectionName;

	var sortFieldSel = '#sortField';

	var serverFiltersSeparatorSel = '#serverFiltersSeparator';
	var serverFiltersFieldsSel = '#serverFiltersFields';
	var serverFiltersSearchOperatorSel = '#serverFiltersSearchOperator';
	var serverFilterSearchValSel = '#serverFilterSearchVal';

	var _addFilter = function () {
		var field = $(serverFiltersFieldsSel).val();
		var searchOperator = $(serverFiltersSearchOperatorSel).val();
		var searchVal = $(serverFilterSearchValSel).val();
		filterFactory.addFilter(field, searchOperator, searchVal);

	};

	var _resetFilter = function () {
		filterFactory.reset();

	};

	var _buildFiltersSelect = function () {
		$(sortFieldSel).empty();
		$(serverFiltersFieldsSel).empty();
		var rowHash = dataService.getColumns();
		for (var key in rowHash) {
			$(sortFieldSel).append($('<option>', {
				value: rowHash[key].title,
				text: rowHash[key].title
			}));
			$(serverFiltersFieldsSel).append($('<option>', {
				value: rowHash[key].title,
				text: rowHash[key].title
			}));
		}
	};

	var _showFilters = function () {
/*		$(serverFiltersSel).show();
		$(serverFiltersSeparatorSel).show();*/
	}

	var _hideFilters = function () {
/*		$(serverFiltersSel).hide();
		$(serverFiltersSeparatorSel).hide();*/
	}

	var _downloadCsv = function () {
		var url = urlFactory.getCsv(collectionName, filterFactory.getFilters());
		var form$ = $('<form/>').attr("method", "post");
		form$.attr('action', url);
		$(document.body).append(form$);
		form$.submit();
		form$.remove();
	};	

	return {
		uploadFile: function () {
			_uploadFile();
		},
		downloadCsv: function () {
			_downloadCsv();
		}
	}
}($);

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
					if (value && typeof value === 'object'){
						var keys = Object.keys(value);
						value = value[keys[0]];
					}
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
