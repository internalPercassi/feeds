var facebookController = function () {
	var collectionName = 'FacebookProduct';

	var _search = function () {
		var url = urlService.getDocs(collectionName, filterService.getFilters());
		tableFactory.showDocs(collectionName, url);
	};

	var _resetFilter = function () {
		filterService.reset();
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
		resetFilter: function () {
			_resetFilter();
			_showDocs();
		},
		getCSV: function () {
			_getCSV();
		}
	}
}($);


