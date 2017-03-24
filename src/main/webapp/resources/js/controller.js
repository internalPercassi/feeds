'use strict';

var appConstants = {
	getDocUrl: 'getDocuments',
	uploadFileUrl: 'parseFile'
};

var PerParserController = function () {

	var _uploadButtonCallback = function () {
		var form = $('#uploadForm')[0];
		var formData = new FormData(form);
		var fileType = $('#fileType').val();
		$.ajax({
			enctype: 'multipart/form-data',
			url: appConstants.uploadFileUrl + '?fileType=' + fileType,
			data: formData,
			cache: false,
			contentType: false,
			processData: false,
			type: 'POST',
			beforeSend: function () {
				$("body").addClass("loading");
			},
			success: function (res) {
				try {
					_showUploadFile(0,10);
				} catch (e) {
					$("body").removeClass("loading");
					console.error(e);
				}
			},
			error: function (jqXHR, textStatus, errorThrown) {
				console.log("ERROR, textStatus=" + textStatus + ", errorThrown=" + errorThrown);
			},
			complete: function () {
				$("body").removeClass("loading");
			}
		});
	};

	var _showUploadFile = function (start, length) {
		jsonTable.resetPage();
		searchFilter.reset();
		var clickMd5CellCallback = function (rowData) {
			console.log(rowData);
			_renderFeedTable(rowData.type,rowData.md5, start, length);
		};
		var cellCallbackConfig = [{
				columnIndex: 1,
				callback: clickMd5CellCallback
			}];
		var url = "getUploadedFile?start=" + start + "&length=" + length;
		var renderUploadedFileCallback = function (start, length, getCsv) {
			var callbackUrl = "getUploadedFile?start=" + start + "&length=" + length;
			__renderGenericTable(callbackUrl, renderUploadedFileCallback, getCsv);
		};		
		__renderGenericTable(url, renderUploadedFileCallback, cellCallbackConfig);
	};


	var _renderFeedTable = function (fileType, md5, start, length) {
		jsonTable.resetPage();
		searchFilter.reset();
		var url = appConstants.getDocUrl + "?collectionName=" + fileType + "&start=" + start + "&length=" + length;
		searchFilter.addFilter("md5", "$eq", md5);		
		var renderFeedTableCallback = function (start, length, getCsv) {
			var callbackUrl = appConstants.getDocUrl + "?collectionName=" + fileType + "&start=" + start + "&length=" + length;
			__renderGenericTable(callbackUrl, renderFeedTableCallback, {}, getCsv);
		};		
		__renderGenericTable(url, renderFeedTableCallback);
	};

	var __renderGenericTable = function (url, searchAndPagingCallback, cellCallbackConfig, getCsv) {
		var dataType = 'json';
		var filters = JSON.stringify(searchFilter.getFilters());
		url += "&filters=" + filters;
		if (getCsv) {
			url += "&getCsv=true";
			dataType = 'text';
			var form$ = $('<form/>').attr("method", "post");
			form$.attr('action', encodeURI(url));
			$(document.body).append(form$);
			form$.submit();
			$(document.body).remove(form$);
			return;
		}
		$.ajax({
			url: encodeURI(url),
			dataType: dataType,
			cache: false,
			contentType: false,
			processData: false,
			type: 'POST',
			beforeSend: function () {
				$("body").addClass("loading");
			},
			success: function (res) {
				if (!getCsv) {
					jsonTable.drawTable(res.data, res.recordsTotal, '#dataGrid', '#tablePager', searchAndPagingCallback, cellCallbackConfig);
					searchFilter.init(res.data, searchAndPagingCallback);
				}
			},
			error: function (jqXHR, textStatus, errorThrown) {
				console.error(JSON.stringify(jqXHR));
			},
			complete: function () {
				$("body").removeClass("loading");
			}
		});
	}


	return {
		init: function () {
			console.log("PerParserController initialized");
			$("#uploadBtn").click(function () {
				_uploadButtonCallback();
			});
			$("#showUploadedFile").click(function (event) {
				event.preventDefault();
				event.stopImmediatePropagation();
				_showUploadFile();
			});
			$('#getCsv').off("click");
		}
	}
}();

PerParserController.init();