<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="buyer-fr">
	<div class="wap-right" >
		<a class="ui-index" href="${ctx}/product/index">首页</a>
		<ul class="nume" id="user_statistics_ul">
			<li class="${statistics_type eq 'buy' ? 'sel' : ''}" type="buy"><em>买单</em><small>0</small></li>
			<li class="${statistics_type eq 'sell' ? 'sel' : ''}" type="sell"><em>卖单</em><small>0</small></li>
			<li class="${statistics_type eq 'publish' ? 'sel' : ''}" type="publish"><em>创建</em><small>0</small></li>
			<li class="${statistics_type eq 'attention' ? 'sel' : ''}" type="attention"><em>关注</em><small>0</small></li>
			<li class="${statistics_type eq 'comment' ? 'sel' : ''}" type="comment"><em>评论</em><small>0</small></li>
			<li class="${statistics_type eq 'recommend' ? 'sel' : ''}" type="recommend"><em>推荐</em><small>0</small></li>
		</ul>
	</div>
	<script type="text/javascript">
		$(function(){
			//查询用户产品统计信息
			M.post("${ctx}/product/queryUserStatistics",{}, function(result){
				var statistics = result.data;
				$("#user_statistics_ul li[type='publish'] small").text(statistics.publish_num);
				$("#user_statistics_ul li[type='attention'] small").text(statistics.attention_num);
				$("#user_statistics_ul li[type='comment'] small").text(statistics.comment_num);
				$("#user_statistics_ul li[type='recommend'] small").text(statistics.recommend_num);
			});
			
			//用户产品统计列表页面
			$("#user_statistics_ul li").click(function(){
				var type = $(this).attr("type");
				window.location.href = "${ctx}/product/userProductList?statistics_type=" + type;
			});
			
			//加载我的卖单数据
			M.post("${ctx}/order/loadTeamOrderCount",{}, function(result){
				$("#user_statistics_ul li[type='sell'] small").text(result.data || 0);
			});
		});
	</script>
</div>