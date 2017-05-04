'use strict';

var restService = function () {
	
	var _callAjax = function (type, url, successCbk) {
		$.ajax({
			url: url,
			dataType: 'json',
			cache: false,
			contentType: false,
			processData: false,
			type: type,
			beforeSend: function () {
                            $('#loading').show();
			},
			success: function (res) {
				if (res && res.data && res.data.length > 0) {
					successCbk(res);
				} else {
					console.log("http resposonse is null");
					successCbk(res);
				}
			},
			error: function (jqXHR, textStatus, errorThrown) {
				console.error(JSON.stringify(jqXHR));
                                $.notify(jqXHR.responseText, "error");
			},
			complete: function () {
                            $('#loading').hide();
			}
		});
	};

	return {
		post: function (url, callback) {
			_callAjax('POST', url, callback);
		},
		get: function (url, callback) {
			_callAjax('GET', url, callback);
		}
	}
}($);