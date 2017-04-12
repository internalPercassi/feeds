
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

		var md5Filter = filterFactory.getFilter("md5");
		filterFactory.reset();
		if (md5Filter) {
			filterFactory.addFilter(md5Filter.field, md5Filter.searchOperator, md5Filter.searchVal);
		}

		if (minstock) {
			filterFactory.addFilter("stockedQty", "$gt", minstock);
		}
		if (maxstock) {
			filterFactory.addFilter("stockedQty", "$lt", maxstock);
		}
		if (minbook) {
			filterFactory.addFilter("bookedQty", "$gt", minbook);
		}
		if (maxbook) {
			filterFactory.addFilter("bookedQty", "$lt", maxbook);
		}
		if (accstate) {
			filterFactory.addFilter("accountingState", "$eq", accstate);
		}
		if (prodcod) {
			filterFactory.addFilter("uniqueProductCode", "$eq", prodcod);
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