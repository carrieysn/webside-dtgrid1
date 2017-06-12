<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setAttribute("title", "用户中心");%>
<%@ include file="/WEB-INF/jsp/mobile/common/common.jsp"%>
<body>
	<header>
		<div class="head">
			<a href="${ctx}/mobile/user/center" class="return-two"></a>
			<label class="title">设置</label>
		</div>
	</header><!-- /header -->
	<ul class="mine-icon-psw">
		<li class="icon-xg">
			<a href="${ctx}/mobile/user/modifyPwd?token=${token}">
				修改密码
			</a>
		</li>
		<li class="icon-xg">
			<a href="${ctx}/mobile/user/logout?token=${token}">
				退出登录
			</a>
		</li>
	</ul>
</body>
</html>