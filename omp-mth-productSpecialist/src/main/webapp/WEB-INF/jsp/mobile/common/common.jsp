<%@page import="com.meitianhui.platform.utils.ActionHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	session.setAttribute("ctx", request.getContextPath());
	session.setAttribute("token", ActionHelper.getInstance().getToken(request));
	session.setAttribute("sessionUser", ActionHelper.getInstance().getSessionUser(request));
	session.setAttribute("static_resource_server", "//mps-static.meitianhui.com/product");	//静态资源访问地址
%>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes" /><!-- 删除苹果默认的工具栏和菜单栏 -->
	<meta name="apple-mobile-web-app-status-bar-style" content="black" /><!-- 设置苹果工具栏颜色 -->
	<meta name="format-detection" content="telphone=no, email=no" /><!-- 忽略页面中的数字识别为电话，忽略email识别 -->
	<!-- 启用360浏览器的极速模式(webkit) -->
	<meta name="renderer" content="webkit">
	<!-- 避免IE使用兼容模式 -->
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<!-- 针对手持设备优化，主要是针对一些老的不识别viewport的浏览器，比如黑莓 -->
	<meta name="HandheldFriendly" content="true">
	<!-- 微软的老式浏览器 -->
	<meta name="MobileOptimized" content="320">
	<!-- uc强制竖屏 -->
	<meta name="screen-orientation" content="portrait">
	<!-- QQ强制竖屏 -->
	<meta name="x5-orientation" content="portrait">
	<!-- UC强制全屏 -->
	<meta name="full-screen" content="yes">
	<!-- QQ强制全屏 -->
	<meta name="x5-fullscreen" content="true">
	<!-- UC应用模式 -->
	<meta name="browsermode" content="application">
	<!-- QQ应用模式 -->
	<meta name="x5-page-mode" content="app">
	<!-- windows phone 点击无高光 -->
	<meta name="msapplication-tap-highlight" content="no">
	<!-- 禁用iPhone手机浏览器上给电话号码自动加上的link样式 -->
	<meta name="format-detection" content="telephone=no">
	<title>${empty title ? '买手' : title }</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/mobile${empty bt ? '' : '/'.concat(bt)}/css/reset.css?v=0.11">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/mobile${empty bt ? '' : '/'.concat(bt)}/css/slide.css?v=0.11">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/mobile${empty bt ? '' : '/'.concat(bt)}/css/common.css?v=0.11">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/mobile${empty bt ? '' : '/'.concat(bt)}/css/index.css?v=0.17">
	<script type="text/javascript" src="${static_resource_server}/common/third-part/jquery-1.11.3.min.js"></script>
	<!-- art-template模板引擎 -->
	<script type="text/javascript" src="${static_resource_server}/common/third-part/art-template.js"></script>
	<%-- <script type="text/javascript" src="${ctx}/static/mobile/js/zepto.min.js"></script> --%>
	<script type="text/javascript" src="${static_resource_server}/common/third-part/swiper.min.js"></script>
  	<script type="text/javascript" src="${static_resource_server}/common/third-part/flexible.js"></script>
  	<script type="text/javascript" src="${static_resource_server}/common/third-part/fastclick.min.js"></script>
  	<script type="text/javascript" src="${static_resource_server}/common/third-part/layer/layer.js"></script>
	<script type="text/javascript" src="${ctx}/common/static/js/application.js?v=0.12"></script>
  	<!-- 分页控件 -->
  	<script type="text/javascript" src="${ctx}/static/mobile/js/mPage.js"></script>
  	<script type="text/javascript">
		var ctx = "${ctx}"; var token = "${token}";
	</script>
</head>