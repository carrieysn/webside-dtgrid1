<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common.jsp" %>
<body>
	<script type="text/javascript" src="${static_resource_server}/common/third-part/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="${ctx}/common/static/js/portal.js?v=1.12"></script>
	<!-- header -->
    <div class="header-wrap">
        <div class="header">
            <span class="logo">每天惠运营管理系统</span>
            <div class="head-right">
                <div class="had-news">
                    <a href="javascript:void(0);" class="new-h" id="new-h">
                        <em></em>
                        <span>0</span>
                    </a>
                    <ul class="news-ul" style="display: none;">
                        <li>消息</li>
						<li><a href="javascript:void(0);">2015.11.24每天惠运营系统正式上线</a></li>
						<!-- <li>查看全部</li> -->
                    </ul>
                    <div class="log-h" id="log-h">
                        <p class="d" id="_user_name_">&nbsp;</p>
                        <p id="_user_team_">&nbsp;</p>
                    </div>
                    <ul class="log-ul" style="display: none;">
                    	<li><a href="javascript:void(0);" class="mima" id="_change_pwd_a">修改密码</a></li>
						<li><a href="javascript:_logout();" class="abort">退出</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="password-info" id="_password-info-div" style="display: none;">
		<form id="_changePasswordForm_">
			<table class="password-type-fl">
				<tr>
					<td align="right" width="90"><em class="mandatory">*</em>原密码：</td>
					<td align="left">
						<input type="password" datatype="*" name="oldPassword" nullmsg="请输入原始密码！">
					</td>
				</tr>
				<tr>
					<td align="right"><em class="mandatory">*</em>新密码：</td>
					<td align="left">
						<input type="password" datatype="*6-32" name="newPassword" errormsg="请输入6到32位密码！">
					</td>
				</tr>
				<tr>
					<td align="right"><em class="mandatory">*</em>确认密码：</td>
					<td align="left">
						<input type="password" datatype="*6-32" name="newPassword1" recheck="newPassword" errormsg="两次密码不一致！" name="newPassword">
					</td>
				</tr>
			</table>
		</form>
	</div>