'use strict';

var appConstants = {
	getDocUrl: 'getDocuments',
	uploadFileUrl: 'parseFile'
};


var tableController = function () {
	var myTable;
	var collectionName;

	var selectorId = '#myTable';
	var sortFieldSel = '#sortField';
	var sortFieldSel = '#sortField';
	var serverFiltersSel = '#serverFilters';
	var serverFiltersSeparatorSel = '#serverFiltersSeparator';
	var serverFiltersFieldsSel = '#serverFiltersFields';
	var serverFiltersSearchOperatorSel = '#serverFiltersSearchOperator';
	var serverFilterSearchValSel = '#serverFilterSearchVal';

	var tableOptions = {
		deferRender: true,
		scrollY: 400,
		scrollCollapse: true,
		scroller: true,
		pageable: false};

	var _doFilter = function () {
		var sortConfig = {};
		sortConfig.sortField = $(sortFieldSel).val();
		sortConfig.sortType = $('input[name=sortType]:checked').val();

		var field = $(serverFiltersFieldsSel).val();
		var searchOperator = $(serverFiltersSearchOperatorSel).val();
		var searchVal = $(serverFilterSearchValSel).val();
		filterService.addFilter(field, searchOperator, searchVal);
		var url = urlService.getDocsFilter(collectionName, filterService.getFilters(), sortConfig);
		_showDocs(collectionName, url);
	};

	var _resetFilter = function () {
		filterService.reset();
	};

	var _buildFiltersSelect = function () {
		$(sortFieldSel).empty();
		$(serverFiltersFieldsSel).empty();
		var rowHash = dataService.getColumns();
		for (var key in rowHash) {
			$(sortFieldSel).append($('<option>', {
				value: rowHash[key].title,
				text: rowHash[key].title
			}));
			$(serverFiltersFieldsSel).append($('<option>', {
				value: rowHash[key].title,
				text: rowHash[key].title
			}));
		}
	};

	var _showFilters = function () {
		$(serverFiltersSel).show();
		$(serverFiltersSeparatorSel).show();
	}

	var _hideFilters = function () {
		$(serverFiltersSel).hide();
		$(serverFiltersSeparatorSel).hide();
	}

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

	var _uploadFile = function () {
		filterService.reset();
		var form = $('#uploadForm')[0];
		var formData = new FormData(form);
		var fileType = $('#fileType').val();
		var url = appConstants.uploadFileUrl + '?fileType=' + fileType;
		$.ajax({
			enctype: 'multipart/form-data',
			url: url,
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
					_showUploadedFiles();
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
			_hideFilters();
		};
		_callAjax(url, callback);
	};

	var _showDocs = function (collectionNamePar, url) {
		if (collectionNamePar) {
			collectionName = collectionNamePar;
		}
		if (!url) {
			url = urlService.getDocs(collectionName);
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
			_buildFiltersSelect();
			_showFilters();
		}
		_callAjax(url, callback);
	};

	var _downloadCsv = function () {
		var url = urlService.getCsv(collectionName, filterService.getFilters());
		var form$ = $('<form/>').attr("method", "post");
		form$.attr('action', encodeURI(url));
		$(document.body).append(form$);
		form$.submit();
		$(document.body).remove(form$);
	};

	return {
		uploadFile: function () {
			_uploadFile();
		},
		showUploadedFiles: function () {
			_showUploadedFiles();
		},
		showDocs: function (collectionName) {
			filterService.reset();
			_showDocs(collectionName);
		},
		doFilter: function () {
			_doFilter();
		},
		resetFilter: function () {
			_resetFilter();
			_showDocs();
		},
		downloadCsv: function () {
			_downloadCsv();
		}
	}
}($);
