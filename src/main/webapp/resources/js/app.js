
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
					loadView(response, new facebookViewModel());
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
					loadView(response, new glViewModel());
				});

	});

	this.get('#/OS/:id', function (context) {
		context.app.swap('');
		var md5 = context.params['id'];
		context.load('/PerParserSPA/resources/views/pages/os.template')

				.then(function (response) {
					filterService.reset();
					filterService.addFilter('md5', '$eq', md5);
					osController.search();
					loadView(response, new osViewModel());
				});

	});

	this.get('#/newRelic', function (context) {
		context.app.swap('');
		context.load('/PerParserSPA/resources/views/pages/newRelic.template')
				.replace(context.$element())
				.then(function () {});
	});

	this.get('#/history/', function (context) {
		context.app.swap('');
		context.load('/PerParserSPA/resources/views/pages/history.template')
				.then(function (response) {
					var vm = new historyViewModel();
					historyController.init();
					historyController.setViewModel(vm);
					loadView(response, vm);
					tableFactory.showUploadedFiles();

				});
	});



	this.before('.*', function () {
		var hash = document.location.hash;
		$(".nav.nav-sidebar").find("li").removeClass("active");
		$(".nav.nav-sidebar").find("a[href='" + hash + "']").parent().addClass("active");
	});

	var loadView = function (response, viewModel) {
		var $container = $('#app'),
				$view = $container.find('.view'),
				$newView = $('<div>').addClass('view').html(response);

		if ($view.length)
			ko.removeNode($view[0]);

		$container.append($newView);
		ko.applyBindings(viewModel, $newView[0]);
	}
});


$(document).ready(function () {
	app.run('#/');
});