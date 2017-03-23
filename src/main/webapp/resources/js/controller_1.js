var alert = function () {
	console.log.apply(console, arguments);
};

var facebookColumnName = {
};

$(document).ready(function () {
	var parParserModule = function () {
		"use strict";

		var GL_FILE_TYPE = 'GL';
		var table;
		var _resetTable = function () {
			$('#minBQty').val(undefined);
			$('#minSQty').val(undefined);

			if (table) {
				table.destroy();
				$('#dataGrid').empty();
			}
		};

		var _setComponentVisibility = function (fielTypeVal) {
			if (fielTypeVal && fielTypeVal == GL_FILE_TYPE) {
				$("#filterGL").show();
			} else {
				$("#filterGL").hide();
			}
		};

		var _renderTable = function (fileType) {
			_resetTable();
			$.fn.dataTable.ext.search = [];
			if (fileType && fileType == GL_FILE_TYPE) {
				$.fn.dataTable.ext.search.push(_filterGL);
			}

			$.ajax({
				url: "getFeeds?fileType=" + fileType+"&getColumnNames=true",				
				cache: false,
				contentType: false,
				processData: false,
				type: 'GET',
				beforeSend: function () {
					$("body").addClass("loading");
				},
				success: function (columnNames) {
					loadDataTable(columnNames.data);
				},
				error: function (jqXHR) {
					console.error(JSON.stringify(jqXHR));
				},
				complete: function () {
					$("body").removeClass("loading");
				}
			});

			var loadDataTable = function (columnNames) {
				table = $('#dataGrid').DataTable({
					columns: columnNames,
					dom: 'B<"clear">lfrtip',
					buttons: ['copy', 'csv'],
					processing: true,
					serverSide: true,
					paging: true,
					ajax: "/PerParserSPA/getFeeds?fileType=" + fileType
				});

				$("#dataGrid").append($('<tfoot/>').append($("#dataGrid thead tr").clone()));
				// Setup - add a text input to each footer cell
				$('#dataGrid tfoot th').each(function (i) {
					var title = $('#dataGrid thead th').eq($(this).index()).text();
					$(this).html('<input type="text" placeholder="' + title + '" data-index="' + i + '" />');
				});
				// Apply the search
				$(table.table().container()).on('blur', 'tfoot input', function () {
					table
							.column($(this).data('index'))
							.search(this.value)
							.draw();
				});
				$('#minBQty, #minSQty').blur(function () {
					table.draw();
				});
			};
		};

		var _filterGL = function (settings, data, dataIndex) {
			var minBQty = parseInt($('#minBQty').val(), 10);
			var minSQty = parseInt($('#minSQty').val(), 10);
			var stockedQty = parseFloat(data[6]) || 0;
			var bookedQty = parseFloat(data[7]) || 0;
			if (isNaN(minBQty) && isNaN(minSQty))
				return true;
			else
			{
				var stockedQty = parseFloat(data[6]) || 0;
				var bookedQty = parseFloat(data[7]) || 0;
				if ((!isNaN(minBQty) && !isNaN(minSQty) && minBQty <= bookedQty && minSQty <= stockedQty))
					return true;
				else
				{
					if (!isNaN(minBQty) && !isNaN(minSQty))
						return false;
					if ((!isNaN(minBQty) && minBQty <= bookedQty) ||
							(!isNaN(minSQty) && minSQty <= stockedQty))
						return true;
					else
						false;
				}
			}
		};

		var _uploadButtonCallback = function () {
			var form = $('#uploadForm')[0];
			var formData = new FormData(form);

			$.ajax({
				enctype: 'multipart/form-data',
				url: 'parseFile',
				data: formData,
				cache: false,
				contentType: false,
				processData: false,
				type: 'POST',
				beforeSend: function () {
					$("body").addClass("loading");
				},
				success: function (data) {
					try {
						_renderTable($("#fileType").val());
						_setComponentVisibility($("#fileType").val());
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

		var loadData = function () {
			
		}
		return {
			init: function () {
				$("#uploadBtn").click(_uploadButtonCallback);
			}
		}
	}();
	parParserModule.init();
});


