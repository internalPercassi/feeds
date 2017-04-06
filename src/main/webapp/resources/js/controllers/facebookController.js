(function ($) {



	app.get('#/FacebookProduct/:id', function (context) {
		var md5 = this.params['id'];
		context.app.swap('');
		context.load('/PerParserSPA/resources/views/pages/FacebookProduct.template')
				.appendTo(context.$element()).then(function () {
//			tableController.init();
//			filterService.reset();
//			filterService.addFilter("md5", "$eq", md5)
			tableController.showDocs('FacebookProduct');
		});
	});

})(jQuery);