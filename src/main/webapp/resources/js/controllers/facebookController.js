var facebookController = function () {
	var _that = {};
	var collectionName = 'FacebookProduct';
	var selectorId = '#facebookTable';
	var facebookTable;
	
	var tableOptions = {
			pageable: true,
			columnDefs: [
	            {
	                targets: [0,5,6,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27],
	                visible: false,
	            }
	        ],
	  		columns: [
			    { "title": "md5"},
			    { "title": "Id"},
			    { "title": "Availability"},
			    { "title": "Condition"},
			    { "title": "Description"},
			    { "title": "imageLink"},
			    { "title": "link"},
			    { "title": "Title"},
			    { "title": "price",},
			    { "title": "brand"},
			    { "title": "additionalImageLink"},
			    { "title": "ageGroup"},
			    { "title": "color"},
			    { "title": "expirationDate"},
			    { "title": "gender"},
			    { "title": "itemGroupId"},
			    { "title": "googleProductCategory"},
			    { "title": "material"},
			    { "title": "pattern"},
			    { "title": "productType"},
			    { "title": "salePrice"},
			    { "title": "shipping"},
			    { "title": "shippingWeight"},
			    { "title": "customLabel0"},
			    { "title": "customLabel1"},
			    { "title": "customLabel2"},
			    { "title": "customLabel3"},
			    { "title": "customLabel4"}
			]
		};
	
	var _search = function () {
		var url = urlFactory.getDocs(collectionName, filterFactory.getFilters());
		_loadFacebookGrid(collectionName, url);
	};

	var _resetFilter = function () {
		filterFactory.reset();
	};

	var _getCSV = function () {
		tableFactory.downloadCsv(collectionName);
	};
	
	var _loadFacebookGrid = function (collectionNamePar, url) {
		_that.vm.isLoading(true);
		if (collectionNamePar) {
			collectionName = collectionNamePar;
		}
		
		if (!url) {
			url = urlFactory.getDocs(collectionName, filterFactory.getFilters());
		}
		
		var callback = function (res) {
			var tabOpt = jQuery.extend(true, {}, tableOptions);
			tabOpt.data = tableFactory.getRowsForDatatables(res);
			var takis  = tableFactory.getColumnsForDatatables(res);
			if (facebookTable) {
				facebookTable.destroy();
				facebookTable = undefined;
				$(selectorId).empty();
			}
			facebookTable = $(selectorId).DataTable(tabOpt);
			_that.vm.isLoading(false);
		}
		restService.post(url, callback);
	};
	
	var _init = function () {

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
		search: function () {
			_search();
		},
		getCSV: function () {
			_getCSV();
		}
	}
}($);


