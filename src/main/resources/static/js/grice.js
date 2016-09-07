$(document).ready(function () {
	
	$('.ui.accordion').accordion();
	
	$('.dropdown.link').dropdown({
		action: 'hide'
	});
	
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
	        type:"post",
	        url: this_.attr('href'),
	        cache:false,
	        dataType:"json",
	        success: function(result){
	        	if(result && result.success){
//	        		this_.addClass('current').siblings().removeClass('current');
	        		this_.addClass('active').siblings().removeClass('active');
	        		var data = result.payload;
	        		$('#article').html( md.render(data.content) );
	        		
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