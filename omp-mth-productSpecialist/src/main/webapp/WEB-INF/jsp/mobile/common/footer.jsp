<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="btn-inquire">
	<a href="${ctx}/mobile/goods/index?token=${token}" class="${menu_name eq 'index' ? 'sel' : ''}">首页</a>
	<a href="${ctx}/mobile/goods/find?token=${token}" class="${menu_name eq 'find' ? 'sel' : ''}">找好货</a>
	<a href="${ctx}/mobile/goods/create?token=${token}" class="${menu_name eq 'create' ? 'sel' : ''}">上好货</a>
	<a href="${ctx}/mobile/user/center?token=${token}" class="${menu_name eq 'center' ? 'sel' : ''}">我的</a>
</div>
