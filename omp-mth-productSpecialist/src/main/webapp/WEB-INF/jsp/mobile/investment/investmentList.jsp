<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/mth.tld" prefix="mth"%>
<%request.setAttribute("title", "招商列表");%>
<%@ include file="/WEB-INF/jsp/mobile/common/common.jsp"%>
<body style="background: #f2f2f2;">
	<header>
		<div class="head">
			<a href="${ctx}/mobile/user/center?token=${token}" class="return-two"><em></em></a>
			<label class="title zs-title">招商列表</label>
			<c:if test="${mth:hasAuth(token,'383010a7-b51b-11e6-8685-00163e010763')}"> 
				<a class="icon-zs-add" href="${ctx}/mobile/investment/create?token=${token}"></a>
			</c:if>
		</div>
	</header>
	<!-- /header -->
	<div class="goods dingbu40">
		<ul class="god-warp" id="investment_list_ul"></ul>
		<script type="text/html" id="investmentListTpl">
			{{each list as investment i}}
				<li subject_id="{{investment.subject_id}}">
					<div class="zs-name">{{investment.title}}</div>
					<p class="zs-nr">{{investment.content}}</p>
					<p class="zs-date">
						<span>发布者：{{investment.team_name}} - {{investment.user_name}}</span> <span>有效期：{{investment.expiration_date}}</span>
						<a class="icon-bianji" href="${ctx}/mobile/investment/edit?token=${token}&subject_id={{investment.subject_id}}"></a>
					</p>
				</li>
			{{/each}}
		</script>
	</div>
	<%@ include file="/WEB-INF/jsp/mobile/common/footer.jsp"%>
</body>
<script type="text/javascript">
	$(function(){
		var investmentPage = $("#investment_list_ul").mPage({
			url: '${ctx}/mobile/investment/queryMyInvestmentListPage?token=${token}',
			templateId: 'investmentListTpl',
			isLazy: false
		});
		
		$('#investment_list_ul').on('click', 'li', function(){
			var subject_id = $(this).attr('subject_id');
			if(subject_id){
				window.location.href = '${ctx}/mobile/investment/detail?token=${token}&subject_id=' + subject_id;
			}
		});
	});
</script>
</html>