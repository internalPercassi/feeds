'use strict';

var tableFactory = function () {
	var myTable;
	var collectionName;
	var selectorId = '#myTable';

	var tableOptions = {
		pageable: true,
		columnDefs: [
			{"visible": false, "targets": 0}
		]
	};		
	
	var _callAjax = function (url, successCbk) {
		$.ajax({
			url: url,
			async: true,
			dataType: 'json',
			cache: false,
			contentType: false,
			processData: false,
			type: 'POST',
			beforeSend: function () {
				$("body").addClass("loading");
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
				var collectionName = data[2];
				appConstants.app.bind(collectionName, function (e, data) {
					this.redirect('#/' + data[2], data[0]);
				});
				appConstants.app.trigger(collectionName, data);
			});
		};
		_callAjax(url, callback);
	};

	var _showDocs = function (collectionNamePar, url) {
		if (collectionNamePar) {
			collectionName = collectionNamePar;
		}
		if (!url) {
			url = urlService.getDocs(collectionName, filterService.getFilters());
		}
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
			if(collectionName == 'uploadedFile'){
				$(selectorId + ' tbody').on('click', 'tr', function () {
					var data = myTable.row(this).data();
					var collectionName = data[2];				
					appConstants.app.bind(collectionName, function (e, data) {
						this.redirect('#/' + data[2], data[0]);
					});
					appConstants.app.trigger(collectionName, data);
				});				
			}
		}
		_callAjax(url, callback);
	};

	var _downloadCsv = function () {
		var url = urlService.getCsv(collectionName, filterService.getFilters());
		var form$ = $('<form/>').attr("method", "post");
		form$.attr('action', url);
		$(document.body).append(form$);
		form$.submit();
		form$.remove();
	};	

	return {		
		showUploadedFiles: function () {
			_showUploadedFiles();
		},
		showDocs: function (collectionName) {
			_showDocs(collectionName);
		},		
		downloadCsv: function () {
			_downloadCsv();
		}
	}
}($);
