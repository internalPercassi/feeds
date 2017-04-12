var facebookController = function () {
	var collectionName = 'FacebookProduct';
	var selectorId = '#facebookTable';
	var facebookTable;
	
	var tableOptions = {
		pageable: true,
		columnDefs: [
			{"visible": false, "targets": 0}
		]
	};
	
	var _search = function () {
		var url = urlFactory.getDocs(collectionName, filterFactory.getFilters());
		_loadFacebookGrid(collectionName, url);
	};

	var _resetFilter = function () {
		filterFactory.reset();
	};

	var _getCSV = function () {
		tableFactory.downloadCsv(collectionName);
	};
	
	var _loadFacebookGrid = function (collectionNamePar, url) {
		
		if (collectionNamePar) {
			collectionName = collectionNamePar;
		}
		
		if (!url) {
			url = urlFactory.getDocs(collectionName, filterFactory.getFilters());
		}
		
		var callback = function (res) {
			var tabOpt = jQuery.extend(true, {}, tableOptions);
			tabOpt.data = dataService.getRowsForDatatables(res);
			tabOpt.columns = dataService.getColumnsForDatatables(res);
			if (facebookTable) {
				facebookTable.destroy();
				facebookTable = undefined;
				$(selectorId).empty();
			}
			facebookTable = $(selectorId).DataTable(tabOpt);
			//_buildFiltersSelect();
			//_showFilters();
		}
		restService.post(url, callback);
	};
	
	var _init = function () {

	};
	return {
		init: function () {
			_init();
		},
		search: function () {
			_search();
		},
/*		resetFilter: function () {
			_resetFilter();
			_showDocs();
		},*/
		getCSV: function () {
			_getCSV();
		}
	}
}($);


