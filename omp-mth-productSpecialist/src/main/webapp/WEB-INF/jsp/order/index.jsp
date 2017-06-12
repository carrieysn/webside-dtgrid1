<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setAttribute("title", "买手-首页");
%>
<%@ include file="/common/layout/header.jsp"%>
<%@ include file="/common/layout/menu.jsp"%>
	<!-- main container -->
	<%@ include file="/WEB-INF/jsp/searchbar.jsp" %>
	<div class="main-container">
		<div class="buyer-center">
			<div class="buyer-fl">
				<%@ include file="/WEB-INF/jsp/order/orderList.jsp" %>
			</div>
			<%@ include file="/WEB-INF/jsp/userinfo.jsp"%>
		</div>
	</div>
<%@ include file="/common/layout/footer.jsp"%>