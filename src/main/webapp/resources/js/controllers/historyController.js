'use strict';

var historyController = function () {
    var _that = {};
    var collectionName = appConstants.collectionNames.UploadedFile;
    var selectorId = '#historyTable';
    var historyTable;

    var tableOptions = {
        pageable: true,
        columnDefs: [
            {"visible": false, "targets": 0},
            {
                "targets": 4,
                "width": "20%",
                "data": null,
                /*		            "title": "Actions",*/
                "defaultContent": "" +
                        "<div class='col-md-12'> " +
                        "<div class='col-md-6'>" +
                        "<div class='btn btn-default form-control' onclick='historyController.openFile(this)'>Open</div> " +
                        "</div>" +
                        "<div class='col-md-6'>" +
                        "<div class='btn btn-default form-control' onclick='historyController.removeFile(this)'>Remove</div> " +
                        "</div>" +
                        "</div>"
            }
        ],
        columns: [
            {"title": "md5"},
            {"title": "File Name"},
            {"title": "Type"},
            {"title": "Upload Date"},
            {"title": "Actions"}
        ],
        order: [[ 3, "desc" ]]
    };

    var _search = function (filters) {
        var sortConfig = {
            sortField:"date",
            sortType:-1
        };
        var url = urlFactory.getDocs(collectionName, filterFactory.getFilters(),sortConfig);
        _loadHistoryGrid(collectionName, url);
    };


    var _uploadFile = function () {
        filterFactory.reset();
        var form = $('#uploadForm')[0];
        var formData = new FormData(form);
        var fileType = $('#fileType').val();
        var localeCod = "it_IT";//TODO, aggiungere alla form di upload (serve solo per facebook)
        var url = appConstants.uploadFileUrl + '?fileType=' + fileType + "&localeCod=" + localeCod;

        $.ajax({
            enctype: 'multipart/form-data',
            url: url,
            data: formData,
            cache: false,
            contentType: false,
            processData: false,
            type: 'POST',
            beforeSend: function () {
                $('#loading').show();
            },
            success: function (res) {
                try {
                    historyController.showUploadedFiles();
                    $.notify("File uploaded successfully", "success");
                } catch (e) {
                    console.error(e);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("ERROR, textStatus=" + textStatus + ", errorThrown=" + errorThrown);
                $.notify(jqXHR.responseText, "error");
            },
            complete: function () {
                $('#loading').hide();
            }
        });
    };

    var _showUploadedFiles = function () {
        var url = urlFactory.getUploadedFiles();
        restService.post(url, callback);
    };

    var _loadHistoryGrid = function (collectionNamePar, url) {
        if (collectionNamePar) {
            collectionName = collectionNamePar;
        }
        if (!url) {
            url = urlFactory.getDocs(collectionName, filterFactory.getFilters());
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

    var _openFile = function (element) {
        var data = historyTable.row($(element).parents('tr')).data();
        var collectionName = data[2];
        appConstants.app.bind(collectionName, function (e, data) {
            this.redirect('#/' + data[2], data[0]);
        });
        appConstants.app.trigger(collectionName, data);
    };

    var _removeFile = function (element) {
        var data = historyTable.row($(element).parents('tr')).data();
        deleteFile({md5: data[0], fileType: data[2]});
    };

    var callback = function (res) {
        var tabOpt = jQuery.extend(true, {}, tableOptions);
        tabOpt.data = tableFactory.getRowsForDatatables(res);
        if (historyTable) {
            historyTable.destroy();
            historyTable = undefined;
            $(selectorId).empty();
        }
        historyTable = $(selectorId).DataTable(tabOpt);
        var uploadedFileName = $('.input-group').find(':text');
        if (uploadedFileName.length) {
            uploadedFileName.val('');
            $('#uploadBtn').attr('disabled', true);
        }


    };

    var deleteFile = function (data) {
        $.ajax({
            url: "deleteUploadedFile",
            data: JSON.stringify(data),
            cache: false,
            contentType: "application/json",
            type: 'POST',
            beforeSend: function () {
                $('#loading').show();
            },
            success: function (res) {
                try {
                    historyController.showUploadedFiles();
                    $.notify("File deleted successfully", "success");
                } catch (e) {
                    $('#loading').hide();
                    console.error(e);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("ERROR, textStatus=" + textStatus + ", errorThrown=" + errorThrown);
                $.notify("Error during file delete", "error");
            },
            complete: function () {
                $('#loading').hide();
            }
        });
    }
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
        },
        openFile: function (element) {
            _openFile(element);
        },
        removeFile: function (element) {
            _removeFile(element);
        }
    }
}($);




