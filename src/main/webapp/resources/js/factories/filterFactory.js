'use strict';
var filterFactory = function () {
	var filters = [];

	var _addFilter = function (field, searchOperator, searchVal) {
		console.log("filterFactory _addFilter "+field+" "+searchOperator+" "+searchVal);
		if (!field || !searchOperator || !searchVal) {
			return;
		}
		
		var filter = {
			field: field,
			searchOperator: searchOperator,
			searchVal: encodeURIComponent(searchVal)
		};

		filters.forEach(function (value, i) {
			if (value.field == field) {
				return;
			}
		});

		filters.push(filter);
	}

	var _removeFilter = function (field) {
		var tmp = [];
		filters.forEach(function (value, i) {
			if (value.field == field) {
				tmp.push(value);
			}
		});
		filters = tmp;
	};

	var _reset = function () {
		console.log("filterFactory _reset ");
		filters = [];
	}
	var _getFilters = function () {
		var ret = [];
		filters.forEach(function (value, i) {
			ret.push(value);
		});
		return ret;
	};
	
	var _getFilter = function(fieldName){
		var ret;
		filters.forEach(function (value, i) {
			if (value && value.field == fieldName){
				ret = value;
			}
		});
		return ret;
	}
	return {
		addFilter: function (field, searchOperator, searchVal) {
			return _addFilter(field, searchOperator, searchVal);
		},
		removeFilter: function () {
			return _removeFilter();
		},
		reset: function () {
			return _reset();
		},
		getFilters: function () {
			return _getFilters();
		},
		getFilter: function(fieldName){
			return _getFilter(fieldName);
		}
	};
}()