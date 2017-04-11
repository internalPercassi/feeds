
var historyViewModel = function () {

	var _that = this;

	this.isLoading = ko.observable(false);
	
	this.types = [
		{name: "GL"},
		{name: "OS"},
		{name: "FacebookProduct"}
	];

	this.filters = {
		name: ko.observable(''),
		type: ko.observable('')
	};

	this.resetFilters = function () {
		_that.filters.name('');
		_that.filters.type(null);
	}

	this.filteredSearch = function () {
		var fileName = _that.filters.name();
		var fileType;
		if (_that.filters.type()) {
			fileType = _that.filters.type().name;
		}
		console.log("_that.filters.name=" + fileName + ", _that.filters.type=" + fileType);
		filterService.reset();
		if (fileName) {
			filterService.addFilter("fileName", "$eq", fileName);
		}
		if (fileType){
			filterService.addFilter("type", "$eq", fileType);
		}
		historyController.search();
	}
};