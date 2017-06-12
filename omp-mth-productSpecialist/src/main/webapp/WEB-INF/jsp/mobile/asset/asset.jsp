<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setAttribute("title", "资金");%>
<%@ include file="/WEB-INF/jsp/mobile/common/common.jsp"%>
<body>
	<header>
		<div class="head">
			<a href="${ctx}/mobile/user/center?token=${token}" class="return-two"><em></em></a>
			<label class="title">资金</label>
		</div>
	</header><!-- /header -->
	<div class="funds-info">
		<div class="zzj">
			3,555.65<i>元</i>
		</div>
		<div class="xyzj">
			<p>冻结  <i>230.00元</i></p>
			<p>信用  <i>44230.00元</i></p>
		</div>
	</div>
	<ul class="payment-details">
		<li>
			<div>
				<p class="name">现金支付</p>
				<p class="date">2015/01/13 10:30:45</p>
			</div>
			<div class="first-div">+25.55</div>
		</li>
		<li>
			<div>
				<p class="name">在线支付</p>
				<p class="date">2015/01/13 10:30:45</p>
			</div>
			<div class="first-div">+25.55</div>
		</li>
		<li>
			<div>
				<p class="name">提现</p>
				<p class="date">2015/01/13 10:30:45</p>
			</div>
			<div class="first-div">-2,234.43</div>
		</li>
	</ul>
	<div class="tixian-push">
		<a href="#">提现</a>
		<a href="#">充值</a>
	</div>
</body>
<script type="text/javascript">
</script>
</html>