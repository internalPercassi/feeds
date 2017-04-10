
var app = $.sammy('#app', function () {
	this.use('Template');
	this.get('#/', function (context) {});
	this.get('#/FacebookProduct/:id', function (context) {
		context.app.swap('');
		var md5 = context.params['id'];
		context.load('/PerParserSPA/resources/views/pages/FacebookProduct.template')
				.then(function (response) {					
					filterService.reset();
					filterService.addFilter('md5', '$eq', md5);
					facebookController.search();
					var $container = $('#app'),
							$view = $container.find('.view'),
							$newView = $('<div>').addClass('view').html(response);
					if ($view.length) {
						ko.removeNode($view[0]); // Clean up previous view
					}
					$container.append($newView);
					ko.applyBindings(new facebookViewModel(), $newView[0]);
				});
	});

	this.get('#/GL/:id', function (context) {
		context.app.swap('');
		var md5 = context.params['id'];
		context.load('/PerParserSPA/resources/views/pages/gl.template')
		.then(function (response) {					
			filterService.reset();
			filterService.addFilter('md5', '$eq', md5);
			glController.search();
			var $container = $('#app'),
					$view = $container.find('.view'),
					$newView = $('<div>').addClass('view').html(response);
			if ($view.length) {
				ko.removeNode($view[0]); // Clean up previous view
			}
			$container.append($newView);
			ko.applyBindings(new glViewModel(), $newView[0]);
		});

	});

	this.get('#/OS/:id', function (context) {
		context.app.swap('');
		var md5 = context.params['id'];
		context.load('/PerParserSPA/resources/views/pages/os.template')
				.appendTo(context.$element())
				.then(function (response) {					
					filterService.reset();
					filterService.addFilter('md5', '$eq', md5);
					osController.search();
					var $container = $('#app'),
							$view = $container.find('.view'),
							$newView = $('<div>').addClass('view').html(response);
					if ($view.length) {
						ko.removeNode($view[0]); // Clean up previous view
					}
					$container.append($newView);
					ko.applyBindings(new osViewModel(), $newView[0]);
				});

	});

	this.get('#/newRelic', function (context) {
		context.app.swap('');
		context.load('/PerParserSPA/resources/views/pages/newRelic.template')
				.replace(context.$element())
				.then(function () {

				});

	});

	this.get('#/history/', function (context) {
		context.app.swap('');
		context.load('/PerParserSPA/resources/views/pages/history.template')
				.then(function (response) {
					var $container = $('#app'),
							$view = $container.find('.view'),
							$newView = $('<div>').addClass('view').html(response);


					if ($view.length) {
						ko.removeNode($view[0]); // Clean up previous view
					}
					tableFactory.showUploadedFiles();
					$container.append($newView);
					ko.applyBindings(new historyViewModel(), $newView[0]);
				});
	});


	this.before('.*', function () {
		var hash = document.location.hash;
		$(".nav.nav-sidebar").find("li").removeClass("active");
		$(".nav.nav-sidebar").find("a[href='" + hash + "']").parent().addClass("active");
	});
});


$(document).ready(function () {
	app.run('#/');
});