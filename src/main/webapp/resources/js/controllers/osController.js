var osController = function () {
	var _that = {};
	var collectionName = appConstants.collectionNames.OS;
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

	var _loadOSGrid = function (collectionNamePar, url) {
		_that.vm.isLoading(true);
		
		if (collectionNamePar) {
			collectionName = collectionNamePar;
		}
		if (!url) {
			url = urlFactory.getDocs(collectionName, filterFactory.getFilters());
		}
		var callback = function (res) {
			var tabOpt = jQuery.extend(true, {}, tableOptions);
			tabOpt.data = tableFactory.getRowsForDatatables(res);
			tabOpt.columns = tableFactory.getColumnsForDatatables(res);
			if (osTable) {
				osTable.destroy();
				osTable = undefined;
				$(selectorId).empty();
			}
			osTable = $(selectorId).DataTable(tabOpt);
			_that.vm.isLoading(false);
		}
		restService.post(url, callback);
	};

	var _getCSV = function () {
		tableFactory.downloadCsv(collectionName);
	};

	var _setViewModel = function (vm) {
		_that.vm = vm;
	};
	
	var _init = function () {

	};
	
	return {
		init: function () {
			_init();
		},
		setViewModel: function (vm) {
			_setViewModel(vm);
		},
		search: function () {
			_search();
		},		
		getCSV: function () {
			_getCSV();
		}
	}
}($);
