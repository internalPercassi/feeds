var osController = function () {
	var collectionName = 'OS';
	var selectorId = '#osTable';
	var osTable;
	
	var tableOptions = {
			pageable: true,
			columnDefs: [
				{"visible": false, "targets": 0}
			]
		};
	
	var _search = function () {
		var url = urlFactory.getDocs(collectionName, filterFactory.getFilters());
		_loadOSGrid(collectionName, url);
	};

	var _loadGlGrid = function (collectionNamePar, url) {
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
			if (osTable) {
				osTable.destroy();
				osTable = undefined;
				$(selectorId).empty();
			}
			osTable = $(selectorId).DataTable(tabOpt);
			//_buildFiltersSelect();
			//_showFilters();
		}
		restService.post(url, callback);
	};

	var _getCSV = function () {
		tableFactory.downloadCsv(collectionName);
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
		getCSV: function () {
			_getCSV();
		}
	}
}($);
