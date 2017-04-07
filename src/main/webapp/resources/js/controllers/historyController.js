'use strict';
var app = $.sammy.apps['#app'];
var appConstants = {
	getDocUrl: 'getDocuments',
	uploadFileUrl: 'parseFile'
};


var historyController = function () {
	var collectionName = 'uploadedFile';
	var sortFieldSel = '#sortField';
	var filtersActivesSel = '#filtersActivesP';

	var _search = function () {
		var sortConfig = {};
		sortConfig.sortField = $(sortFieldSel).val();
		sortConfig.sortType = $('input[name=sortType]:checked').val();

		var url = urlService.getDocs(collectionName, filterService.getFilters(), sortConfig);
		tableFactory.showDocs(collectionName, url);
	};




	var _resetFilter = function () {
		filterService.reset();
	
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
					tableFactory.showUploadedFiles();
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


	var _init = function () {

		$(document).on('change', ':file', function () {
			var input = $(this),
					numFiles = input.get(0).files ? input.get(0).files.length : 1,
					label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
			input.trigger('fileselect', [numFiles, label]);
		});

		// We can watch for our custom `fileselect` event like this

		var input = $(this).parents('.input-group').find(':text');

		if (!input.val())
			$('#uploadBtn').attr('disabled', true);

		$(':file').on('fileselect', function (event, numFiles, label) {

			var input = $(this).parents('.input-group').find(':text'),
					log = numFiles > 1 ? numFiles + ' files selected' : label;

			if (input.length) {
				input.val(log);
				$('#uploadBtn').attr('disabled', label.length ? false : true);
			} else
			if (log)
				alert(log);
		});
	};

	return {
		init: function () {
			_init();
		},
		uploadFile: function () {
			_uploadFile();
		},
		search: function () {
			_search();
		},
		resetFilter: function () {
			_resetFilter();
			_showDocs();
		},
	}
}($);


(function ($) {

	app.get('#/history/', function (context) {
		context.app.swap('');
		context.load('/PerParserSPA/resources/views/pages/history.template')
				.then(function (response) {

					historyController.init();
					tableFactory.showUploadedFiles();

			        var $container = $('#app'),
		            $view = $container.find('.view'),
		            $newView = $('<div>').addClass('view').html(response);
			        
			        
			        if ($view.length) {
			            ko.removeNode($view[0]); // Clean up previous view
			        }
			        $container.append($newView);
			        
			        
			        
					var historyViewModel = function () {

						var _that = this;

						this.types = [
							{name: "GL"},
							{name: "OS"},
							{name: "FacebookProduct"}
						];

						this.filters = {
							name: ko.observable(''),
							type: ko.observable('')
						};

						this.resetFilters = function () {
							_that.filters.name('');
							_that.filters.type(null);
						}

						this.filteredSearch = function () {
							var fileName = _that.filters.name();
							var fileType;
							if (_that.filters.type()) {
								fileType = _that.filters.type().name;
							}
							console.log("_that.filters.name=" + fileName + ", _that.filters.type=" + fileType);
							filterService.reset();
							if (fileName) {
								filterService.addFilter("fileName", "$eq", fileName);
							}
							if (fileType){
								filterService.addFilter("type", "$eq", fileType);
							}
							historyController.search();
						}
					};
					 ko.applyBindings(new historyViewModel(), $newView[0]);
				});

		app.bind('test', function (e, data) {
			this.redirect('#/' + data[2], data[0]);
		});
	});

})(jQuery);