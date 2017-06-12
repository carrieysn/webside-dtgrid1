<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="buyer-search">
	<div class="wap-search">
		<input type="text" value="${keyword}"/>
		<input type="submit" value="找好货" class="but1 search_input"/>
		<a href="${ctx}/product/create" class="but2">有好货</a>
	</div>
	<script type="text/javascript">
		//关键字搜索
		$(function(){
			$(".search_input").first().bind("click.search", function(){
				var keyword = $(this).prev().first().val();
				window.location.href = "${ctx}/product/index?keyword=" + keyword;
			}).prev().first().bind("keydown", function(e){
				if(e.which == '13'){
					var keyword = $(this).val();
					window.location.href = "${ctx}/product/index?keyword=" + keyword;
				}
			});
		});
	</script>
</div>