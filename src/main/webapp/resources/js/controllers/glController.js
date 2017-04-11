var glController = function () {
	var collectionName = 'GL';

	var _search = function () {
		var url = urlService.getDocs(collectionName, filterService.getFilters());
		tableFactory.showDocs(collectionName, url);
	};

	var _getCSV = function () {
		tableFactory.downloadCsv(collectionName);
	};
	var _resetFilter = function () {
		filterService.reset();
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
		},
		resetFilter: function () {
			_resetFilter();
			_showDocs();
		},
	}
}($);

