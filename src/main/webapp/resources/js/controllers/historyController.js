'use strict';

var historyController = function () {
	var _that = {};
	var collectionName = appConstants.collectionNames.UploadedFile;
	var sortFieldSel = '#sortField';
	var selectorId = '#historyTable';
	var historyTable;
	
	var tableOptions = {
			pageable: true,
			columnDefs: [
				{"visible": false, "targets": 0}
			]
		};

	var _search = function () {
		var sortConfig = {};
		sortConfig.sortField = $(sortFieldSel).val();
		sortConfig.sortType = $('input[name=sortType]:checked').val();


	var url = urlFactory.getDocs(collectionName, filterFactory.getFilters(), undefined);
		_loadHistoryGrid(collectionName, url);
	};

	var _uploadFile = function () {
		filterFactory.reset();
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
				_that.vm.isLoading(true);
			},
			success: function (res) {
				try {
					historyController.showUploadedFiles();
				} catch (e) {
					_that.vm.isLoading(false);
					console.error(e);
				}
			},
			error: function (jqXHR, textStatus, errorThrown) {
				console.log("ERROR, textStatus=" + textStatus + ", errorThrown=" + errorThrown);
			},
			complete: function () {
				_that.vm.isLoading(false);
			}
		});
	};

	var _showUploadedFiles = function () {
		var url = urlFactory.getUploadedFiles();
		var callback = function (res) {
			var tabOpt = jQuery.extend(true, {}, tableOptions);
			tabOpt.data = tableFactory.getRowsForDatatables(res);
			tabOpt.columns = tableFactory.getColumnsForDatatables(res);
			if (historyTable) {
				historyTable.destroy();
				historyTable = undefined;
				$(selectorId).empty();
			}
			historyTable = $(selectorId).DataTable(tabOpt);
			$(selectorId + ' tbody').on('click', 'tr', function () {
				var data = historyTable.row(this).data();
				var md5 = data[0];
				var collectionName = data[2];
				appConstants.app.bind(collectionName, function (e, data) {
					this.redirect('#/' + data[2], data[0]);
				});
				appConstants.app.trigger(collectionName, data);
			});
		};
		restService.post(url, callback);
	};
	
	var _loadHistoryGrid = function (collectionNamePar, url) {
		if (collectionNamePar) {
			collectionName = collectionNamePar;
		}
		if (!url) {
			url = urlFactory.getDocs(collectionName, filterFactory.getFilters());
		}
		var callback = function (res) {
			var tabOpt = jQuery.extend(true, {}, tableOptions);
			tabOpt.data = tableFactory.getRowsForDatatables(res);
			tabOpt.columns = tableFactory.getColumnsForDatatables(res);
			if (historyTable) {
				historyTable.destroy();
				historyTable = undefined;
				$(selectorId).empty();
			}
			historyTable = $(selectorId).DataTable(tabOpt);
			//_buildFiltersSelect();
			//_showFilters();
		}
		restService.post(url, callback);
	};
	
	var _init = function () {

		$(document).on('change', 'input[type="file"]', function () {
			var input = $(this),
					numFiles = input.get(0).files ? input.get(0).files.length : 1,
					label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
			var input2 = $(this).parents('.input-group').find(':text'),
					log = numFiles > 1 ? numFiles + ' files selected' : label;

			if (input2.length) {
				input2.val(log);
				$('#uploadBtn').attr('disabled', label.length ? false : true);
			}
		});

	};

	var _setViewModel = function (vm) {
		_that.vm = vm;
	};

	return {
		init: function () {
			_init();
		},
		setViewModel: function (vm) {
			_setViewModel(vm);
		},
		uploadFile: function () {
			_uploadFile();
		},
		showUploadedFiles: function () {
			_showUploadedFiles();
		},
		search: function () {
			_search();
		}
	}

}($);




