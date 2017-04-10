
var osViewModel = function () {

	var _that = this;

	this.filters = {
		productCode: ko.observable(''),
		modelCode: ko.observable(''),
		warehouse: ko.observable(''),
		physicalInventory: ko.observable(''),
		status: ko.observable(''),
		replenishmentLevel: ko.observable(''),
	};

	this.resetFilters = function () {
		_that.filters.productCode('');
		_that.filters.modelCode('');
		_that.filters.warehouse('');
		_that.filters.physicalInventory('');
		_that.filters.status(null);
		_that.filters.replenishmentLevel('');
	}

	this.filteredSearch = function () {
		var productCode = _that.filters.productCode();
		var modelCode = _that.filters.modelCode();
		var warehouse = _that.filters.warehouse();
		var physicalInventory = _that.filters.physicalInventory();
		var orderStatus = _that.filters.orderStatus();
		var replenishmentLevel = _that.filters.replenishmentLevel();
		
		filterService.reset();

		if (productCode) {
			filterService.addFilter("productCode", "$eq", productCode);
		}
		
		if (productCode) {
			filterService.addFilter("productCode", "$eq", productCode);
		}
		
		if (modelCode) {
			filterService.addFilter("modelCode", "$eq", modelCode);
		}
		
		if (warehouse) {
			filterService.addFilter("warehouse", "$eq", warehouse);
		}
		
		if (physicalInventory) {
			filterService.addFilter("physicalInventory", "$eq", physicalInventory);
		}
		
		if (status){
			filterService.addFilter("status", "$eq", status);
		}
		
		if (replenishmentLevel){
			filterService.addFilter("replenishmentLevel", "$eq", replenishmentLevel);
		}
		
		osController.search();
	}
};