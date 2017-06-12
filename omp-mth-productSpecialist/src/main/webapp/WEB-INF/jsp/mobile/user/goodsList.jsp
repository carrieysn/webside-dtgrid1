<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setAttribute("title", "商品列表");%>
<%@ include file="/WEB-INF/jsp/mobile/common/common.jsp"%>
<body>
	<header>
		<div class="head">
			<a href="${ctx}/mobile/user/center?token=${token}" class="return-two"></a>
			<label class="title">
				<c:choose>
					<c:when test="${statistics_type eq 'publish'}">创建</c:when>
					<c:when test="${statistics_type eq 'attention'}">关注</c:when>
					<c:when test="${statistics_type eq 'comment'}">评论</c:when>
					<c:when test="${statistics_type eq 'recommend'}">推荐</c:when>
				</c:choose>
			</label>
		</div>
	</header><!-- /header -->
	<div class="goods top-40">
		<%@include file="/WEB-INF/jsp/mobile/goodsListTpl.jsp"%>
	</div>
	<%@ include file="/WEB-INF/jsp/mobile/common/footer.jsp"%>
</body>
<script type="text/javascript">
	$(function(){
		var page = $("#goods_list_ul").mPage({
			url: '${ctx}/mobile/user/queryGoodsListPage?token=${token}&statistics_type=${statistics_type}',
			templateId: 'goodsListTpl'
		});
		
		/**加载商品列表**/
		function search(){
			page.mPage('reload', getParams());
			/*获取用户选择的参数信息*/
			function getParams(){
				var params = {};
				$(".search_condition").filter(".sel").each(function(){
					var type = $(this).attr("type");
					var value = $(this).attr("value");
					if(value){
						params[type] = value;
					}
				});
				params.orderby = $(".orderby").filter(".sel").first().attr("value");
				return params;
			}
		}
	});
</script>
</html>