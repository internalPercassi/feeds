var osController = function () {
	var collectionName = 'OS';

	var _search = function () {
		var url = urlService.getDocs(collectionName, filterService.getFilters());
		tableFactory.showDocs(collectionName, url);
	};

	var _getCSV = function () {
		tableFactory.downloadCsv(collectionName);
	};
	
	return {		
		search: function () {
			_search();
		},		
		getCSV: function () {
			_getCSV();
		}
	}
}($);
