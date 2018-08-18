(function($){
	var zp = {
		init:function(obj,pageinit){
			return (function(){
				zp.addhtml(obj,pageinit);
				zp.bindEvent(obj,pageinit);
			}());
		},
		addhtml:function(obj,pageinit){
			return (function(){
				obj.empty();
				/*上一页*/
				if (pageinit.current > 1) {
					obj.append('<a href="javascript:;" class="prebtn" title="上一页"></a>');
				} else{
					obj.remove('.prevPage');
					obj.append('<a class="disabled prebtn" title="上一页"></a>');
				}
				/*中间页*/
				if (pageinit.current >4 && pageinit.pageNum > 4) {
					obj.append('<a href="javascript:;" class="zxfPagenum">'+1+'</a>');
					obj.append('<a href="javascript:;" class="zxfPagenum">'+2+'</a>');
					
				}
				if (pageinit.current >4 && pageinit.current <= pageinit.pageNum-5) {
					var start  = pageinit.current - 2,end = pageinit.current + 2;
				}else if(pageinit.current >4 && pageinit.current > pageinit.pageNum-5){
					var start  = pageinit.pageNum - 4,end = pageinit.pageNum;
				}else{
					var start = 1,end = 5;
				}
				if (start > 3) {
					obj.append('<span>&nbsp;...&nbsp;</span>');
				}
				for (;start <= end;start++) {
					if (start <= pageinit.pageNum && start >=1) {
						if (start == pageinit.current) {
							obj.append('<a class="current">'+ start +'</a>');
						} else if(start == pageinit.current+1){
							obj.append('<a href="javascript:;" class="zxfPagenum nextpage">'+ start +'</a>');
						}else{
							obj.append('<a href="javascript:;" class="zxfPagenum">'+ start +'</a>');
						}
					}
				}
				if (end < pageinit.pageNum) {
					obj.append('<span>&nbsp;...&nbsp;</span>');
				}
				/*下一页*/
				if (pageinit.current >= pageinit.pageNum) {
					//obj.remove('.nextbtn');
					obj.append('<a class="disabled nextbtn" title="下一页"></a>');
				} else{
					obj.append('<a href="javascript:;" class="nextbtn" title="下一页"></a>');
				}
			}());
		},
		bindEvent:function(obj,pageinit){
			return (function(){
				obj.on("click","a.prebtn",function(){
					var cur = parseInt(obj.children("a.current").text());
					if (pageinit.current > 1) {
						var current = $.extend(pageinit, {"current":cur-1});
						zp.addhtml(obj,current);
						if (typeof(pageinit.backfun)=="function") {
							pageinit.backfun(current);
						}
					}
				});
				obj.on("click","a.zxfPagenum",function(){
					var cur = parseInt($(this).text());
					var current = $.extend(pageinit, {"current":cur});
					zp.addhtml(obj,current);
					if (typeof(pageinit.backfun)=="function") {
						pageinit.backfun(current);
					}
				});
				obj.on("click","a.nextbtn",function(){
					var cur = parseInt(obj.children("a.current").text());
					if (pageinit.current < pageinit.pageNum) {
						var current = $.extend(pageinit, {"current":cur+1});
						zp.addhtml(obj,current);
						if (typeof(pageinit.backfun)=="function") {
							pageinit.backfun(current);
						}
					}
				});
			}());
		}
	}
	$.fn.createPage = function(options){
		var pageinit = $.extend({
			pageNum : 10,
			current : 1,
			backfun : function(){}
		},options);
		zp.init(this,pageinit);
	}
	
	
}(jQuery));
