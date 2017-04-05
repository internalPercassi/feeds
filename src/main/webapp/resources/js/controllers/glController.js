(function($) {



  app.get('#/GL/:id', function(context) {
	  
	var param = this.params['id'];
	var str=location.href.toLowerCase();
	context.app.swap('');
	context.load('/PerParserSPA/resources/views/pages/gl.template')
	.appendTo(context.$element())
	.then(function(){
		tableController.showDocs('GL');
	});      
	   
  });

})(jQuery);