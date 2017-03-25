'use strict';

var appConstants = {
	getDocUrl: 'getDocuments',
	uploadFileUrl: 'parseFile'
};


var tableController = function () {
	var myTable;
	var selectorId = '#myTable';
	var tableOptions = {
		deferRender: true,
		scrollY: 600,
		scrollCollapse: true,
		scroller: true};
	var _callAjax = function (url, successCbk) {
		$.ajax({
			url: url,
			dataType: 'json',
			cache: false,
			contentType: false,
			processData: false,
			type: 'POST',
			beforeSend: function () {
				$("body").addClass("loading");
			},
			success: function (res) {
				successCbk(res);
			},
			error: function (jqXHR, textStatus, errorThrown) {
				console.error(JSON.stringify(jqXHR));
				$("body").removeClass("loading");
			},
			complete: function () {
				$("body").removeClass("loading");
			}
		});
	};

	var _showUploadedFiles = function () {
		var url = urlService.getUploadedFiles();
		var callback = function (res) {
			var tabOpt = jQuery.extend(true, {}, tableOptions);
			tabOpt.data = dataService.getRowsForDatatables(res);
			tabOpt.columns = dataService.getColumnsForDatatables(res);
			if (myTable) {
				myTable.destroy();
				myTable = undefined;
				$(selectorId).empty();
			}
			myTable = $(selectorId).DataTable(tabOpt);
			$(selectorId + ' tbody').on('click', 'tr', function () {
				var data = myTable.row(this).data();
				_showDocs(data[1]);//data[1]=collectionName
			});
		};
		_callAjax(url, callback);
	};

	var _showDocs = function (collectionName) {
		var url = urlService.getDocs(collectionName);
		var callback = function (res) {
			var tabOpt = jQuery.extend(true, {}, tableOptions);
			tabOpt.data = dataService.getRowsForDatatables(res);
			tabOpt.columns = dataService.getColumnsForDatatables(res);
			if (myTable) {
				myTable.destroy();
				myTable = undefined;
				$(selectorId).empty();
			}
			myTable = $(selectorId).DataTable(tabOpt);
		}
		_callAjax(url, callback);
	}
	return {
		showUploadedFiles: function () {
			_showUploadedFiles();
		},
		showDocs: function ( collectionName) {
			_showDocs( collectionName);
		}
	}
}($);
