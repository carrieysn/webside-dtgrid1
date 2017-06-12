<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setAttribute("title", "买手");%>
<%@ include file="/WEB-INF/jsp/mobile/common/common.jsp"%>
<body>
	<div class="point-page">
		<div class="point-img"></div>
		<div class="nr">本应用全部功能需要单独授权使用，<br/>请联系每天惠运营平台管理员，谢谢。</div>
		<a href="${ctx}/mobile/user/logout?token=${token}" class="know-btn">知道了</a>
	</div>
</body>
</html>