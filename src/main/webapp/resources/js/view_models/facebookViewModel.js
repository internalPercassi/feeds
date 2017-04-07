
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
		console.log("_that.filters.id=" + id);
		filterService.reset();
		if (fileName) {
			filterService.addFilter("id", "$eq", id);
		}
		facebookController.search();
	}
};