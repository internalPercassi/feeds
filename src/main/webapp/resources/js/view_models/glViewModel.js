
var glViewModel = function () {

	var _that = this;


	this.filters = {
		minstock: ko.observable(''),
		maxstock: ko.observable(''),
		minbook: ko.observable(''),
		maxbook: ko.observable(''),
		accstate: ko.observable(''),
		prodcod: ko.observable(''),
	};

	this.resetFilters = function () {
		_that.filters.minstock('');
		_that.filters.maxstock('');
		_that.filters.minbook('');
		_that.filters.maxbook('');
		_that.filters.accstate('');
		_that.filters.prodcod('');
	}

	this._setFilters = function () {
		var minstock = _that.filters.minstock();
		var maxstock = _that.filters.maxstock();
		var minbook = _that.filters.minbook();
		var maxbook = _that.filters.maxbook();
		var accstate = _that.filters.accstate();
		var prodcod = _that.filters.prodcod();

		var md5Filter = filterService.getFilter("md5");
		filterService.reset();
		if (md5Filter) {
			filterService.addFilter(md5Filter.field, md5Filter.searchOperator, md5Filter.searchVal);
		}

		if (minstock) {
			filterService.addFilter("stockedQty", "$gt", minstock);
		}
		if (maxstock) {
			filterService.addFilter("stockedQty", "$lt", maxstock);
		}
		if (minbook) {
			filterService.addFilter("bookedQty", "$gt", minbook);
		}
		if (maxbook) {
			filterService.addFilter("bookedQty", "$lt", maxbook);
		}
		if (accstate) {
			filterService.addFilter("accountingState", "$eq", accstate);
		}
		if (prodcod) {
			filterService.addFilter("uniqueProductCode", "$eq", prodcod);
		}
	};

	this.searchFilters = function () {
		_that._setFilters();
		glController.search();
	}

	this.getCSV = function () {
		_that._setFilters();
		glController.getCSV();
	}
};