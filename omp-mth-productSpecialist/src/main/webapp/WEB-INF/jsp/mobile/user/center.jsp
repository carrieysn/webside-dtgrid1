<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/mth.tld" prefix="mth"%>
<%
	request.setAttribute("title", "用户中心");
	request.setAttribute("menu_name", "center");	
%>
<%@ include file="/WEB-INF/jsp/mobile/common/common.jsp"%>
<body>
	<header class="bj-red">
		<div class="head">
			<a href="${ctx}/mobile/goods/index?token=${token}" class="delete"></a>
			<a href="${ctx}/mobile/user/site?token=${token}" class="site"></a>
		</div>
		<div class="name">
			<p class="my">${sessionUser.name}</p>
			<p class="firm-name">${sessionUser.team.team_name}</p>
		</div>
	</header><!-- /header -->
	<div class="mine-title">
		<ul>
			<li>
				<a href="${ctx}/mobile/order/sellorder?token=${token}">
					<p class="ti">卖单</p>
					<p class="number" id="my_order_count"> 0 </p>
				</a>
			</li>
			<li>
				<p class="ti">买单</p>
				<p class="number"> 0 </p>
			</li>
			<li>
				<%-- <a href="${ctx}/mobile/assert/index?token=${token}"> --%>
					<p class="ti">资金</p>
					<p class="number">¥0</p>
				<!-- </a> -->
			</li>
		</ul>
	</div>
	<div class="mine-wrap">
		<ul>
			<li>
				<a href="${ctx}/mobile/user/goodsList?token=${token}&statistics_type=publish">
					<p class="number">${empty statistics.publish_num ? 0 : statistics.publish_num}</p>
					<p class="ti">创建</p>
				</a>
			</li>
			<li>
				<a href="${ctx}/mobile/user/goodsList?token=${token}&statistics_type=attention">
					<p class="number">${empty statistics.attention_num ? 0 : statistics.attention_num}</p>
					<p class="ti">关注</p>
				</a>
			</li>
			<li>
				<a href="${ctx}/mobile/user/goodsList?token=${token}&statistics_type=comment">
					<p class="number">${empty statistics.comment_num ? 0 : statistics.comment_num}</p>
					<p class="ti">评论</p>
				</a>
			</li>
			<li>
				<a href="${ctx}/mobile/user/goodsList?token=${token}&statistics_type=recommend">
					<p class="number">${empty statistics.recommend_num ? 0 : statistics.recommend_num}</p>
					<p class="ti">推荐</p>
				</a>
			</li>
			<li>
				<a href="${ctx}/mobile/investment/index?token=${token}">
					<p class="number" id="invesetment_qty">0</p>
					<p class="ti">招商</p>
				</a>
			</li>
		</ul>
	</div>
	<div class="mine-push">
		<ul>
			<li>
				<a href="${ctx}/mobile/stores/index?token=${token}">
					<div class="icon-mdcx"></div>
					门店查询
				</a>
			</li>
			<li>
				<a href="${ctx}/mobile/stores/register?token=${token}">
					<div class="icon-sfzc"></div>
					商户注册
				</a>
			</li>
			<li>
				<a href="javascript:void(0);">
					<div class="icon-rwgl"></div>
					任务管理
				</a>
			</li>
			<li>
				<a href="${ctx}/mobile/report/salesReport?token=${token}">
					<div class="icon-xsbb"></div>
					销售报表<em>(领了么)</em>
				</a>
			</li>
			<%-- <c:if test="${mth:hasAuth(token,'b730c1a2-06be-11e6-b922-fcaa1490ccaf')}">
				<li>
					<a href="${ctx}/mobile/statistics/analysis?token=${token}">
						<div class="icon-tjfx"></div>
						统计分析
					</a>
				</li>
			</c:if> --%>
			<li>
				<a href="${ctx}/mobile/broadcast/index?token=${token}">
					<div class="icon-jsbb"></div>
					实时播报<em>(领了么)</em>
				</a>
			</li>
			<li><!-- 10.18 -->
				<a href="${ctx}/mobile/broadcast/history/index?type=2016.10.18&token=${token}">
					<div class="icon-activity1"></div>
					10.18
				</a>
			</li>
			<li><!-- 11.11 -->
				<a href="${ctx}/mobile/broadcast/history/index?type=2016.11.11&token=${token}">
					<div class="icon-activity2"></div>
					11.11
				</a>
			</li>
			<li><!-- 11.11 -->
				<a href="${ctx}/mobile/broadcast/history/index?type=2017.03.18&token=${token}">
					<div class="icon-activity3"></div>
					3.18
				</a>
			</li>
			<li><!-- 5.20 -->
				<a href="${ctx}/mobile/broadcast/history/index?type=2017.05.20&token=${token}">
					<div class="icon-activity4"></div>
					5.20
				</a>
			</li>
			<li><!-- 11.11 -->
				<a href="javascript:void(0);">
					<div class="icon-gd">...</div>
				</a>
			</li>
			<!-- <li>
				<a href="javascript:void(0);">
					<div class="icon-gdgn"></div>
					更多功能
					<p class="text-expectancy">敬请期待！</p>
				</a>
			</li> -->
		</ul>
	</div>
	<%@ include file="/WEB-INF/jsp/mobile/common/footer.jsp"%>
</body>
<script type="text/javascript">
	$(function(){
		loadTeamOrderCount();
		//加载组织卖单数量
		function loadTeamOrderCount(){
			$.post("${ctx}/mobile/user/loadTeamOrderCount?token=${token}", function(result){
				if(result){
					var orderCount = result.data || "0";
					$("#my_order_count").html(" " + orderCount + " ");
				}
			}, "json");
		}
		
		/**加载用户发布招商信息总数**/
		loadInvesetmentCount();
		function loadInvesetmentCount(){
			$.post("${ctx}/mobile/investment/queryInvesetmentCount?token=${token}", function(result){
				if(result){
					var count = result.data.invesetment_qty || "0";
					$("#invesetment_qty").html(" " + count + " ");
				}
			}, "json");
		}
	});
</script>
</html>