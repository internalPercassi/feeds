var glController = function () {
	
	var collectionName = 'GL';
	//var sortFieldSel = '#sortField';
	var selectorId = '#glTable';
	var glTable;
	
	var tableOptions = {
			pageable: true,
			columnDefs: [
				{"visible": false, "targets": 0}
			]
		};
	
	var _search = function () {
		var url = urlFactory.getDocs(collectionName, filterFactory.getFilters());
		_loadGlGrid(collectionName, url);
	};

	var _getCSV = function () {
		tableFactory.downloadCsv(collectionName);
	};
	
	var _resetFilter = function () {
		filterFactory.reset();
	};

	var _loadGlGrid = function (collectionNamePar, url) {
		if (collectionNamePar) {
			collectionName = collectionNamePar;
		}
		if (!url) {
			url = urlFactory.getDocs(collectionName, filterFactory.getFilters());
		}
		var callback = function (res) {
			var tabOpt = jQuery.extend(true, {}, tableOptions);
			tabOpt.data = dataService.getRowsForDatatables(res);
			tabOpt.columns = dataService.getColumnsForDatatables(res);
			if (glTable) {
				glTable.destroy();
				glTable = undefined;
				$(selectorId).empty();
			}
			glTable = $(selectorId).DataTable(tabOpt);
			//_buildFiltersSelect();
			//_showFilters();
		}
		restService.post(url, callback);
	};
	
	var _init = function () {
/*		appConstants.app.bind(collectionName, function (e, data) {
			this.redirect('#/' + data[2], data[0]);
		});*/
	};
	
	return {
		init: function () {
			_init();
		},
		search: function () {
			_search();
		},
		getCSV: function () {
			_getCSV();
		},
		resetFilter: function () {
			_resetFilter();
			_showDocs();
		},
	}
}($);

$(document).ready(function () {
	glController.init();
});
