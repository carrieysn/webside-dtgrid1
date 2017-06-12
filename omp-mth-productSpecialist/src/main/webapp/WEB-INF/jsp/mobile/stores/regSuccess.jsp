<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setAttribute("title", "商户注册");%>
<%@ include file="/WEB-INF/jsp/mobile/common/common.jsp"%>
	<body style="background-color:#f4f4f4">
		<header>
			<div class="head">
				<a href="${ctx}/mobile/user/center?token=${token}" class="return-two"><em></em></a>
				<label class="title">注册成功</label>
			</div>
		</header><!-- /header -->
		<div class="gaoshi top100">
			<p class="gs-img"><img src="${ctx}/static/mobile/image/icon-ok.jpg"></p>
			<p class="title-ok">注册完成</p>
			<a href="${ctx}/mobile/user/center?token=${token}" class="return-login">返回</a>
			<i class="icon-gs"></i>
		</div>
	</body>
</html>