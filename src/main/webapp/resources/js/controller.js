'use strict';

var PerParserController = function () {

	var _uploadButtonCallback = function () {
		var form = $('#uploadForm')[0];
		var formData = new FormData(form);
		var fileType= $('#fileType').val();
		$.ajax({
			enctype: 'multipart/form-data',
			url: 'parseFile?fileType='+fileType,
			data: formData,
			cache: false,
			contentType: false,
			processData: false,
			type: 'POST',
			beforeSend: function () {
				$("body").addClass("loading");
			},
			success: function (res) {//TODO the servlet must return md5 of file...
				try {
					_renderFeedTable(res.md5);
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

	var _showUploadedFileButtonCallback = function (event, start, length) {
		event.preventDefault();
		event.stopImmediatePropagation();
		jsonTable.resetPage();
		var clickMd5CellCallback = function (event) {
			event.preventDefault();
			event.stopImmediatePropagation();
			_renderFeedTable(event.target.innerHTML, start, length);
		};
		var cellCallbackConfig = [{
				columnIndex: 1,
				callback: clickMd5CellCallback
			}];
		var url = "getUploadedFile?start=" + start + "&length=" + length;
		var renderUploadedFileCallback = function (start, length, getCsv) {
			var callbackUrl = "getUploadedFile?start=" + start + "&length=" + length;
			__renderGenericTable("", callbackUrl, renderUploadedFileCallback, getCsv);
		};
		searchFilter.reset();
		__renderGenericTable("", url, renderUploadedFileCallback, cellCallbackConfig);
	};


	var _renderFeedTable = function (md5, start, length) {
		jsonTable.resetPage();
		var url = "getFacebookFeed?start=" + start + "&length=" + length;
		var renderFeedTableCallback = function (start, length, getCsv) {
			var callbackUrl = "getFacebookFeed?start=" + start + "&length=" + length;
			__renderGenericTable(md5, callbackUrl, renderFeedTableCallback, {}, getCsv);
		};

		searchFilter.reset();
		__renderGenericTable(md5, url, renderFeedTableCallback);
	};

	var __renderGenericTable = function (md5, url, searchAndPagingCallback, cellCallbackConfig, getCsv) {
		var filters = searchFilter.getFilters();
		var postData;
		var dataType = 'json';
		if (md5) {
			postData = searchFilter.addMd5(md5);
		}
		if (filters && filters.length > 0) {
			postData = filters;
		}
		if (getCsv) {
			url += "&getCsv=true";
			dataType = 'text';
			var form$ = $('<form/>').attr("method", "post");
			var inputFilters = $('<input>').attr('type', 'hidden').attr('name', 'postData').val(JSON.stringify(postData));
			form$.append(inputFilters);
			form$.attr('action', url); 
			$(document.body).append(form$);
			form$.submit();
			return;
		}
		$.ajax({
			url: url,
			data: JSON.stringify(postData),
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
			$("#uploadBtn").click(_uploadButtonCallback);
			$("#showUploadedFile").click(_showUploadedFileButtonCallback);
			$('#getCsv').off("click");
		}
	}
}();

PerParserController.init();