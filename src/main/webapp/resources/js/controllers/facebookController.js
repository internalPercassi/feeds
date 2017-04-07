var facebookController = function () {
	var collectionName = 'FacebookProduct';

	var _search = function () {		
		var url = urlService.getDocs(collectionName, filterService.getFilters());
		tableFactory.showDocs(collectionName, url);
	};

	var _resetFilter = function () {
		filterService.reset();	
	};
	

	var _init = function () {
		app.bind('test', function (e, data) {
			this.redirect('#/' + data[2], data[0]);
		});		
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
	}
}($);
