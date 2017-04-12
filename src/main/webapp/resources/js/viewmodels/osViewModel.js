
var osViewModel = function () {

	var _that = this;

	this.isLoading = ko.observable(false);

	this.filters = {
		productCode: ko.observable(''),
		modelCode: ko.observable(''),
		warehouse: ko.observable(''),
		physicalInventory: ko.observable(''),
		orderStatus: ko.observable(''),
		replenishmentLevel: ko.observable(''),
	};

	this.resetFilters = function () {
		_that.filters.productCode('');
		_that.filters.modelCode('');
		_that.filters.warehouse('');
		_that.filters.physicalInventory('');
		_that.filters.orderStatus('');
		_that.filters.replenishmentLevel('');
	}

	this._setFilters = function () {
		var productCode = _that.filters.productCode();
		var modelCode = _that.filters.modelCode();
		var warehouse = _that.filters.warehouse();
		var physicalInventory = _that.filters.physicalInventory();
		var orderStatus = _that.filters.orderStatus();
		var replenishmentLevel = _that.filters.replenishmentLevel();
		
		filterFactory.reset();

		if (productCode) {
			filterFactory.addFilter("productCode", "$eq", productCode);
		}
		
		if (productCode) {
			filterFactory.addFilter("productCode", "$eq", productCode);
		}
		
		if (modelCode) {
			filterFactory.addFilter("modelCode", "$eq", modelCode);
		}
		
		if (warehouse) {
			filterFactory.addFilter("warehouse", "$eq", warehouse);
		}
		
		if (physicalInventory) {
			filterFactory.addFilter("physicalInventory", "$eq", physicalInventory);
		}
		
		if (orderStatus){
			filterFactory.addFilter("orderStatus", "$eq", orderStatus);
		}
		
		if (replenishmentLevel){
			filterFactory.addFilter("replenishmentLevel", "$eq", replenishmentLevel);
		}				
	}
	
	this.searchFilters = function () {
		_that._setFilters();
		osController.search();
	}

	this.getCSV = function () {
		_that._setFilters();
		osController.getCSV();
	}
};