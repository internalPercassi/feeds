
var facebookViewModel = function () {

	var _that = this;

	
	this.filters = {
		id: ko.observable('')
	};

	this.resetFilters = function () {
		_that.filters.id('');
	}

	this.filteredSearch = function () {
		var id = _that.filters.id();		
		filterService.reset();
		if (id) {
			filterService.addFilter("id", "$eq", id);
		}
		facebookController.search();
	}
};