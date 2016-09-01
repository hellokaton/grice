$(document).ready(function () {
	$('#content').html(marked($('#content').html()));
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
	        		this_.addClass('current').siblings().removeClass('current');
	        		var data = result.payload;
	        		$('#content').html(marked(data.content));
	        		$('#content pre code').each(function(i, block) {
	        		    hljs.highlightBlock(block);
	        		  });
	        		var pos = document.title.indexOf(' - ');
	        		var title = data.title + ' - ' + document.title.substr(pos+3);
	        		// 修改title
	        		document.title = title;
	        		// 修改url
	                window.history.pushState({},0, this_.attr('href'));
	        	}
	        }
	    });
	});
});
