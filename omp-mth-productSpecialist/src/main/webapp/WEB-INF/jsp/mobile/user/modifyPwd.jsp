<%@page import="com.meitianhui.platform.constant.ConfigConstant"%>
<%@page import="com.meitianhui.platform.utils.ConfigurationHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setAttribute("title", "修改密码");
	request.setAttribute("portal_server", ConfigurationHelper.getInstance().getValue(ConfigConstant.PORTAL_SERVER));
%>
<%@ include file="/WEB-INF/jsp/mobile/common/common.jsp"%>
<body>
	<header>
		<div class="head">
			<a href="javascript: window.history.go(-1);" class="return-two"><em></em></a>
			<label class="title">修改密码</label>
		</div>
	</header><!-- /header -->
	<form id="_changePasswordForm_" onsubmit="return false">
		<ul class="login-step">
			<li>
				<span class="fl">原密码</span>
				<span class="fr"> <input type="password" name="oldPassword" placeholder="请输入原密码" datatype="*" nullmsg="请输入原始密码！"></span>
			</li>
			<li>
				<span class="fl">新密码</span>
				<span class="fr"><input type="password" name="newPassword" placeholder="请输入新密码" datatype="*6-32" errormsg="请输入6到32位密码！" nullmsg="请输入新密码"></span>
			</li>
			<li>
				<span class="fl">确认密码</span>
				<span class="fr"><input type="password" name="newPassword" placeholder="请再次输入新密码" datatype="*6-32" name="newPassword1" recheck="newPassword" errormsg="两次密码不一致！" nullmsg="请再次输入新密码"></span>
			</li>
		</ul>
		<a href="javascript:void(0);" class="btn-xyb">确认</a>
	</form>
</body>
<script type="text/javascript" src="${ctx}/common/third-part/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/common/third-part/Validform_v5.3.2.js"></script>
<script type="text/javascript" src="${ctx}/common/static/js/application.js"></script>
<script type="text/javascript">
	$(function(){
		//修改密码
	    var _changePasswordForm = $('#_changePasswordForm_').Validform({
	    	tiptype:function(msg,o,cssctl){
	    		if(o.type == 3){
	    			M.alert(msg);
	    		}
	    	}
		});
		
	  	//请求地址
		var _portal_server_ = "${portal_server}";
		if(_portal_server_.startsWith("https:")){
			_portal_server_ = _portal_server_.substring(6)
		} else if(_portal_server_.startsWith("http:")){
			_portal_server_ = _portal_server_.substring(5)
		}
		
		/**修改密码**/
		$('.btn-xyb').click(function(){
			if(_changePasswordForm.check(false)){
				var loading = layer.load(2);
				var params = $.extend(M.formatElement('#_changePasswordForm_'),{token: token});
				$.getJSON(_portal_server_ + '/omp-mth-api/userAccount?service=account.changePwd&jsoncallback=?&params=' + JSON.stringify(params), function(result){
					layer.close(loading);
					if(result.rsp_code == 'succ'){
						M.alert("密码修改成功！", function(){
							window.location.href = "${ctx}/mobile/user/center?token=${token}";
						});
					} else{
						M.alert(result.error_msg || "密码修改失败！");
					}
				});
			}
		});
	});
</script>
</html>