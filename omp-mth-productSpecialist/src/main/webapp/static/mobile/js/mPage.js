/****
 * 移动端分页插件
 * @param $
 */
(function($){
	/**取窗口可视范围的高度 **/  
	function getClientHeight() {
	    var clientHeight = 0;
	    if (document.body.clientHeight && document.documentElement.clientHeight) {
	        var clientHeight = (document.body.clientHeight < document.documentElement.clientHeight) ? document.body.clientHeight : document.documentElement.clientHeight;
	    } else {
	        var clientHeight = (document.body.clientHeight > document.documentElement.clientHeight) ? document.body.clientHeight : document.documentElement.clientHeight;
	    }
	    return clientHeight;
	}
	/**取窗口滚动条高度 **/  
	function getScrollTop() {
	    var scrollTop = 0;
	    if (document.documentElement && document.documentElement.scrollTop) {
	        scrollTop = document.documentElement.scrollTop;
	    } else if (document.body) {
	        scrollTop = document.body.scrollTop;
	    }
	    return scrollTop;
	}
	/**取文档内容实际高度**/
	function getScrollHeight() {
	    return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight);
	}
	
	/**创建分页插件**/
	function create(target, param){
		init(target);
		var state = $.data(target, 'mPage');
		var opt = state.options;
		// 滚动到距离页面底部200px时自动加载
	    $(document).scroll(function() {
	    	if($(target).is(':visible')){	//元素显示时才能触发相关事件
	    		var bheight = getScrollHeight() - getScrollTop() - getClientHeight();
	    		if (bheight < 200) {
	    			load(target);
	    		}
	    	}
	    });
	    if(!opt.isLazy){	//是否延时加载
	    	load(target);
	    }
	}
	
	/**初始化相关信息**/
	function init(target){
		var state = $.data(target, 'mPage');
		var opt = $.extend(state.options, {param: {}, pageNum: 1, isLoading: false, hasMore: true});
		$(target).html('');
	}
	
	/**加载数据**/
	function load(target){
		var state = $.data(target, 'mPage');
		var opt = $.extend(state.options, params || {});
		if(!opt.isLoading && opt.hasMore){
			opt.onBeforeLoad.call(target);	//加载数据前操作
			var params = $.extend({}, opt.params, {pageNum: opt.pageNum, pageSize: opt.pageSize});
			opt.isLoading = true;
			$.post(opt.url, params, function(result){
				if(result.status == '1'){
					var html = template(opt.templateId, {list: result.data.result});
					$(target).append(html);
					opt.isLoading = false;
					var page = result.data;
					opt.hasMore = parseInt(page.total) - (parseInt(page.pageNum) * parseInt(page.pageSize)) > 0;
					opt.pageNum++ ;
					opt.onLoadSuccess.call(target);
				} else{
					alert(result.msg || '系统繁忙！');
				}
			}, "json");
		}
	}
	
	//mobile分页
	$.fn.mPage = function(options, param){
		if (typeof options == 'string'){
			var method = $.fn.mPage.methods[options];
			if (method){
				return method(this, param);
			} else {
				throw new Error('no method');
			}
		}
		
		return this.each(function(){
			var state = $.data(this, 'mPage');
			if (state){
				$.extend(state.options, options);
			}
			$.data(this, 'mPage', {options: $.extend({}, $.fn.mPage.defaults, options)})
			create(this, param);
		});
	}
	
	$.fn.mPage.methods = {
		reload: function(jq, param){	//重新加载，分页参数会初始化，并将容器内容数据清空
			return jq.each(function(){
				var state = $.data(this, 'mPage');
				var opt = state.options;
				opt.params = $.extend({}, opt.defaultParams, param);
				init(this);	//初始化信息
				load(this);
			});
		}
	}
	
	//默认参数
	$.fn.mPage.defaults = $.extend({},{
		pageSize: 5,
		isNullMsg: false,
		isLazy: false,	//是否延时加载
		nullMsg: '没有查询到相关记录!',
		defaultParams: {},	//默认的参数
		marginHeight: 200,	//当滚动条距离底部多少距离时，加载分页
		onLoadSuccess: function(){},
		onBeforeLoad: function(){}
	});
})(jQuery)