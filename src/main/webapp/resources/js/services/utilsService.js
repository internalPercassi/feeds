'use strict';

var utilsService = function () {

    var _const ={
        dateFormat:'YYYY-MM-DD'
    }
    var _toBackEndDataTo = function (aDate) {
        if (aDate) {
            return moment(aDate).format(_const.dateFormat) + " 23:59";
        } else {
            return moment().format(_const.dateFormat) + " 23:59";
        }
    };

    var _toBackEndDataFrom = function (aDate) {
        if (aDate) {
            return moment(aDate).format(_const.dateFormat) + " 00:00";
        } else {
            return moment().format(_const.dateFormat) + " 00:00";
        }
    };

    return {
        toBackEndDataTo: function (aDate) {
            return _toBackEndDataTo(aDate);
        },
        toBackEndDataFrom: function (aDate) {
            return _toBackEndDataFrom(aDate);
        }
    }
}($);
