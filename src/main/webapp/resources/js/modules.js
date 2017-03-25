'use strict';

var jsonTable = function () {
	var _actualPageNum = 1;
	var pagingConfig = {
		total: 100,
		maxVisible: 10,
		page: _actualPageNum,
		leaps: true,
		firstLastUse: true,
		first: '←',
		last: '→',
		wrapClass: 'pagination',
		activeClass: 'active',
		disabledClass: 'disabled',
		nextClass: 'next',
		prevClass: 'prev',
		lastClass: 'last',
		firstClass: 'first'
	};

	var _buildHtmlTable = function (JSONData, recordCount, tableSelector, tablePagerSelector, pagingCallback, cellConfig) {
		$(tableSelector).empty();
		$(tablePagerSelector).empty();
		var totalPage = Math.floor(recordCount / JSONData.length);
		pagingConfig.total = totalPage;
		pagingConfig.page = _actualPageNum;
		var columns = _addAllColumnHeaders(JSONData, tableSelector, cellConfig);
		var tBody$ = $('<tbody/>');
		for (var i = 0; i < JSONData.length; i++) {
			var row$ = $('<tr/>');
			for (var colIndex = 0; colIndex < columns.length; colIndex++) {
				var cellValue = JSONData[i][columns[colIndex]];
				if (cellValue == null)
					cellValue = "";
				var cell$ = $('<td/>').html(cellValue);
				if (cellConfig) {
					for (var cccI = 0; cccI < cellConfig.length; cccI++) {
						if (colIndex + 1 == cellConfig[cccI].columnIndex) {
							if (cellConfig[cccI].callback) {
								cell$.click({rowData: JSONData[i], callback: cellConfig[cccI].callback}, function (event) {
									event.preventDefault();
									event.stopImmediatePropagation();
									event.data.callback(event.data.rowData);
								});
								cell$.hover(function () {
									$(this).css('cursor', 'pointer');
								});
							}
							if (cellConfig[cccI].show) {
								row$.append(cell$);
							}
						} else {
							row$.append(cell$);
						}
					}
				} else {
					row$.append(cell$);
				}
			}
			tBody$.append(row$);
		}
		$(tableSelector).append(tBody$);
		$(tableSelector).addClass("table table-bordered");
		var paginator = $(tablePagerSelector).bootpag(pagingConfig);
		paginator.off("page");
		paginator.on("page", function (event, num) {
			event.preventDefault();
			event.stopImmediatePropagation();
			_actualPageNum = num;
			var start = pagingConfig.maxVisible * (num - 1);
			var length = pagingConfig.maxVisible;
			console.log("Paging event! num=" + num + ', start=' + start + ", length=" + length);
			pagingCallback(start, length);
		});
		$('#tableActions').html("<input id='getCsv' class='btn btn-default form-control' type='button' value='Get CSV' />");
		var getCvsBtn = $('#getCsv');
		getCvsBtn.off("click");
		getCvsBtn.on("click", function (event, num) {
			event.preventDefault();
			event.stopImmediatePropagation();
			pagingCallback(0, recordCount, true);
		});
	};

	var _addAllColumnHeaders = function (JSONData, tableSelector, cellConfig) {
		var columnSet = [];
		var tHead$ = $('<thead/>');
		var headerTr$ = $('<tr/>');
		var row = JSONData[0];
		var colIndex = 1;
		for (var columnName in row) {
			for (var cccI = 0; cccI < cellConfig.length; cccI++) {
				columnSet[colIndex - 1] = columnName;
				if (colIndex == cellConfig[cccI].columnIndex) {
					if (cellConfig[cccI].show) {
						headerTr$.append($('<th/>').html(columnName));
					}
					break;
				} else {
					headerTr$.append($('<th/>').html(columnName));
					break;
				}
			}
			colIndex++;
		}
		tHead$.append(headerTr$)
		$(tableSelector).append(tHead$);

		return columnSet;
	};


	return {
		drawTable: function (JSONData, recordCount, tableSelector, tablePagerSelector, pagingCallback, cellConfig) {
			_buildHtmlTable(JSONData, recordCount, tableSelector, tablePagerSelector, pagingCallback, cellConfig);
		},
		resetPage: function () {
			_actualPageNum = 1;
		}
	}
}();

var searchFilter = function () {
	var that = this;
	var filters = [];
	var filtersActiveSelector = '#filtersActive';
	var searchFieldSelector = '#searchField';
	var searchOperatorSelector = '#searchOperator';
	var searchValSelector = '#searchVal';
	var addFilterBtnSelector = '#addFilterBtn';
	var searchBtnSelector = '#searchBtn';
	var removeFilterSelector = '#removeFilter';

	var _drawFiltersActive = function () {
		$(filtersActiveSelector).empty();
		for (var i = 0; i < filters.length; i++) {
			var span$ = $('<span/>').addClass('label label-default filter').html("  " + filters[i].field + " " + filters[i].searchOperator + " " + filters[i].searchVal + "  ");
			$(filtersActiveSelector).append(span$);
		}
	};

	var _buildFilterSelect = function (JSONData) {
		$(searchFieldSelector).empty();
		var rowHash = JSONData[0];
		for (var key in rowHash) {
			$(searchFieldSelector).append($('<option>', {
				value: key,
				text: key
			}));
		}
	};

	var _addFilterBtnCallback = function (event) {
		event.preventDefault();
		event.stopImmediatePropagation();
		var field = $(searchFieldSelector).val();
		var searchOperator = $(searchOperatorSelector).val();
		var searchVal = $(searchValSelector).val();

		var filter = {
			field: field,
			searchOperator: searchOperator,
			searchVal: searchVal
		};
		filters.push(filter);
		_drawFiltersActive();
	};

	var _reset = function () {
		filters = [];
		$(searchValSelector).val("");
		_drawFiltersActive();
	};

	var _addMd5 = function (md5) {
		var indexToRemove = [];
		for (var i = 0; i < filters.length; i++) {
			var filter = filters[i];
			if (filter.field == 'md5') {
				indexToRemove.push(i);
			}
		}
		for (var j = 0; j < indexToRemove.length; j++) {
			filters.splice(indexToRemove[j], 1);
		}
		var filter = {
			field: "md5",
			searchOperator: "$eq",
			searchVal: md5
		};
		filters.push(filter);
	};

	var _addFilter = function (filterName, filterOperator, filterValue) {
		var indexToRemove = [];
		for (var i = 0; i < filters.length; i++) {
			var filter = filters[i];
			if (filter.field == filterName) {
				indexToRemove.push(i);
			}
		}
		for (var j = 0; j < indexToRemove.length; j++) {
			filters.splice(indexToRemove[j], 1);
		}
		var filter = {
			field: filterName,
			searchOperator: filterOperator,
			searchVal: filterValue
		};
		filters.push(filter);
	};

	return {
		init: function (JSONData, searchCallback) {
			_buildFilterSelect(JSONData);
			$(addFilterBtnSelector).off("click");
			$(addFilterBtnSelector).on('click', _addFilterBtnCallback);
			$(searchBtnSelector).off("click");
			$(searchBtnSelector).on('click', function (event) {
				event.preventDefault();
				event.stopImmediatePropagation();
				jsonTable.resetPage();
				searchCallback(0, 10, false);
			});
			$(removeFilterSelector).off("click");
			$(removeFilterSelector).on('click', _reset);
		},
		reset: function () {
			_reset();
		},
		getFilters: function () {
			return filters;
		},
		addMd5: function (md5) {
			_addMd5(md5);
		},
		addFilter: function (filterName, filterOperator, filterValue) {
			_addFilter(filterName, filterOperator, filterValue);
		},
		show: function () {
			$('#section-filters').show();
		},
		hide: function () {
			$('#section-filters').hide();
		}
	}
}();
