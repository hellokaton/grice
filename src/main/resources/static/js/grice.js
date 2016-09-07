$(document).ready(function () {
	
	$('.ui.accordion').accordion();
	
	if($('.accordion a.active').length > 0){
		$('.accordion a.active').parents('div.content').prev().click();
	} else{
		$('.accordion .content:eq(0) a:eq(0)').addClass('active');
		$('.accordion .title:eq(0)').click();
	}
	
    var md = new Remarkable({
        html: false,    // Enable HTML tags in source
        xhtmlOut: true,    // Use '/' to close single tags (<br />)
        breaks: false,    // Convert '\n' in paragraphs into <br>
        langPrefix: 'line-numbers language-',    // CSS language prefix for fenced blocks
        linkify: true    // Autoconvert URL-like text to links
    });
    
    $('#article').html( md.render($('#article').html()) );
    
	$('.nav-menus a').on('click', function(e){
		e.preventDefault();
		var this_ = $(this);
		$.ajax({
	        type:"get",
	        url: '/api' + this_.attr('href') + '.json',
	        cache:false,
	        dataType:"json",
	        success: function(result){
	        	if(result && result.success){
	        		this_.addClass('active').siblings().removeClass('active');
	        		var data = result.payload;
	        		$('#article').html( md.render(data.content) );
	        		
	        		Prism.highlightAll();
	        		
	        		var pos = document.title.indexOf(' - ');
	        		var title = data.title + ' - ' + document.title.substr(pos+3);
	        		// 修改title
	        		document.title = title;
	        		// 修改url
	                window.history.pushState({}, 0, this_.attr('href'));
	        	}
	        }
	    });
	});
	
});