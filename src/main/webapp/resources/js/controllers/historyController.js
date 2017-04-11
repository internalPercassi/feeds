'use strict';

var historyController = function () {
	var _that = {};
	var collectionName = 'uploadedFile';
	var sortFieldSel = '#sortField';

	var _search = function () {
		var sortConfig = {};
		sortConfig.sortField = $(sortFieldSel).val();
		sortConfig.sortType = $('input[name=sortType]:checked').val();

		var url = urlService.getDocs(collectionName, filterService.getFilters(), sortConfig);
		tableFactory.showDocs(collectionName, url);
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
				//$("body").addClass("loading");
				_that.vm.isLoading(true);
			},
			success: function (res) {
				try {
					tableFactory.showUploadedFiles();
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
		search: function () {
			_search();
		}
	}

}($);




