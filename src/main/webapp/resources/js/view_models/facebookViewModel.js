
var facebookViewModel = function () {

	var _that = this;


	this.filters = {
		id: ko.observable('')
	};

	this.resetFilters = function () {
		_that.filters.id('');
	}	

	this._setFilters = function () {
		var id = _that.filters.id();
		var md5Filter = filterService.getFilter("md5");
		filterService.reset();
		if (md5Filter) {
			filterService.addFilter(md5Filter.field, md5Filter.searchOperator, md5Filter.searchVal);
		}
		if (id) {
			filterService.addFilter("id", "$eq", id);
		}
	}

	this.searchFilters = function () {
		_that._setFilters();
		facebookController.search();
	}

	this.getCSV = function () {
		_that._setFilters();
		facebookController.getCSV();
	}
};