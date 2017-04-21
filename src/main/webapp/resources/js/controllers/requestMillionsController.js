/**
 * 
 */
var requestMillionsController = function () {
	
	var _that = {};
	
	// Example data
	var _data = [1, 159, 22, 81, 56, 255, 40, 2];
	
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