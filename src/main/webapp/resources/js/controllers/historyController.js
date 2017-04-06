'use strict';
var app = $.sammy.apps['#app'];
var appConstants = {
	getDocUrl: 'getDocuments',
	uploadFileUrl: 'parseFile'
};


var historyController = function () {
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
	var filtersActivesSel = '#filtersActivesP';

	var tableOptions = {
		deferRender: true,
		scrollY: 400,
		scrollCollapse: true,
		scroller: true,
		pageable: false,
		columnDefs: [
			{"visible": false, "targets": 0}
		]};

	var _search = function () {
		var sortConfig = {};
		sortConfig.sortField = $(sortFieldSel).val();
		sortConfig.sortType = $('input[name=sortType]:checked').val();

		var url = urlService.getDocs(collectionName, filterService.getFilters(), sortConfig);
		tableFactory.showDocs(collectionName, url);
	};

	var _drawFilterList = function () {
		$(filtersActivesSel).empty();
		var elementsToAppen = [];
		filterService.getFilters().forEach(function (value, i) {
			var htmlStr = " " + value.field + " " + value.searchOperator + " " + value.searchVal + " ";
			elementsToAppen.push($('<span>').addClass('label label-primary').html(htmlStr));
		});
		$(filtersActivesSel).append(elementsToAppen);
	};


	var _resetFilter = function () {
		filterService.reset();
		_drawFilterList();
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
			async:true,
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
//		showUploadedFiles: function () {
//			_showUploadedFiles();
//		},
//		showDocs: function (collectionName) {
//			filterService.reset();
//			_showDocs(collectionName);
//		},
		search: function () {
			_search();
		},
		resetFilter: function () {
			_resetFilter();
			_showDocs();
		},
//		downloadCsv: function () {
//			_downloadCsv();
//		},
//		addFilter: function () {
//			_addFilter();
//		}
	}
}($);


(function($) {

  app.get('#/history/', function(context) {

	    var str=location.href.toLowerCase();
	    context.app.swap('');
	    context.load('/PerParserSPA/resources/views/pages/history.template')
	    .appendTo(context.$element())
	    .then(function(){
	    	
	    	historyController.init();
	    	tableFactory.showUploadedFiles();
	    
	    	var historyViewModel = function() {
	    		
	    		var _that = this;
	    		
	    		this.types = [
	    			{name:"GL"},
	    			{name:"OS"},
	    			{name:"Facebook"}	    			
	    		];
	    		
	    		this.filters = {
		    	    name: ko.observable(''),
		    	    type: ko.observable('')
	    		};
	    		
	    		this.resetFilters = function(){
	    			_that.filters.name('');
	    			_that.filters.type(null);
	    		}
	    		
	    		this.filteredSearch = function(){
	    			
	    		}
	    	};
	    	ko.applyBindings(new historyViewModel());    	
	    });      
	    
        app.bind('test', function(e, data) {        
        	this.redirect('#/' + data[2], data[0]);      
          });
  });

})(jQuery);