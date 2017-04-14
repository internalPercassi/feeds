/**
 * 
 */
var loadTimeController = function () {
	
	var _that = this;
	var _data = {};
	
	var params = {
		fromDate: '2017-04-03',
		toDate: '2017-04-13'
	} 
		
	var _callAjax = function (type, url, successCbk) {
		$.ajax({
			url: 'getNewRelicData',
			dataType: 'json',
			cache: false,
			data: params,
			async: false,
			contentType: false,
			processData: false,
			type: 'GET',
			beforeSend: function () {
			},
			success: function (res) {
				if (res && res.daily && res.daily.length > 0) {
					_that._data = res;
				} else {
					console.log("http resposonse is null");
				}
			},
			error: function (jqXHR, textStatus, errorThrown) {
				console.error(JSON.stringify(jqXHR));
			},
			complete: function () {
			}
		});
		return _that._data;
	};
	
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
		getData: function (url, callback) {
			return _callAjax('GET', url, callback);
		}
	}
}($);