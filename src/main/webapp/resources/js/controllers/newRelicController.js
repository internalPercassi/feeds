/**
 * 
 */
var newRelicController = function () {
	
	var _that = {};
	
	// Example data
	var _data = [65, 59, 80, 81, 56, 55, 40, 20];
	
	var _setViewModel = function (vm) {
		_that.vm = vm;
	};
	
	var _getData = function () {
		var data = _data;
		return data;
	};
	
	return {
		setViewModel: function (vm) {
			_setViewModel(vm);
		},
		getData: function () {
			return _getData();
		},
	}
}($);