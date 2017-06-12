<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setAttribute("title", "门店查询");%>
<%@ include file="/WEB-INF/jsp/mobile/common/common.jsp"%>
<body style="background:#ffffff;">
	<header>
		<div class="head">
			<a href="javascript: window.history.go(-1);" class="return-two"><em></em></a>
			<label class="title">${stores.stores_name}</label>
		</div>
	</header><!-- /header -->
	<ul class="shop-number">
		<li>
			<p class="sz">会员数</p>
			<p id="member_count_label">0</p>
			<%-- <p>${empty storesAsset.voucher_balance ? '0' : storesAsset.voucher_balance}</p> --%>
		</li>
		<li>
			<p class="sz">金币</p>
			<p>${empty storesAsset.gold ? '0' : storesAsset.gold}</p>
			
		</li>
		<li>
			<p class="sz">现金</p>
			<p>¥${empty storesAsset.cash_balance ? '0.00' : storesAsset.cash_balance}</p>
			
		</li>
	</ul>
	<ul class="jy-list">
		<li class="sel">会员</li>
		<li>交易</li>
	</ul>
	<div class="jy-wrap">
		<div class="jy-bor">
			<table class="table-list" id="memberList" template="memberListTpl" url="${ctx}/mobile/stores/queryStoresConsumerList?stores_id=${stores.stores_id}&token=${token}"></table>
			<script id="memberListTpl" type="text/html">
				<tr class="ti">
					<td style="text-align:center">账号</td>
					<td style="text-align:center">昵称</td>
					<td style="text-align:right">零钱（元）</td>
					<td style="text-align:right">金币</td>
				</tr>
				{{each list as member i}}
					<tr>
						<td style="text-align:center">{{member.mobile | formatMobile}}</td>
						<td style="text-align:center">{{member.nick_name}}</td>
						<td style="text-align:right">{{member.cash_balance}}</td>
						<td style="text-align:right">{{member.gold}}</td>
					</tr>
				{{/each}}
			</script>
		</div>
		<div class="jy-bor" style="display: none;">
			<table class="table-list" id="tradeStatisticsList" template="tradeStatisticsListTpl" url="${ctx}/mobile/stores/queryStatisticsTransStore?stores_id=${stores.stores_id}&token=${token}"></table>
			<script id="tradeStatisticsListTpl" type="text/html">
				<tr class="ti">
					<td style="text-align:center">日期</td>
					<td style="text-align:right">超级返</td>
					<td style="text-align:right">我要批</td>
					<td style="text-align:right">惠易定</td>
					<td style="text-align:right">收银机</td>
				</tr>
				{{each list as trade i}}
					<tr>
						<td style="text-align:center">{{trade.order_date}}</td>
						<td style="text-align:right">{{trade.DDLX_07_amount}}</td>
						<td style="text-align:right">{{trade.DDLX_01_amount}}</td>
						<td style="text-align:right">{{trade.DDLX_06_amount}}</td>
						<td style="text-align:right">{{trade.DDLX_04_amount}}</td>
					</tr>
				{{/each}}
			</script>
		</div>
	</div>
	<%@ include file="/WEB-INF/jsp/mobile/common/footer.jsp"%>
</body>
<script type="text/javascript">
	$(function(){
		$('.jy-list li').click(function(){
			$('.jy-list li').removeClass('sel');
			$(this).addClass('sel');
			$('.jy-wrap .jy-bor').hide();
			var index = $(this).index('.jy-list li');
			$('.jy-wrap .jy-bor').eq(index).show();
			var table = $('.table-list').eq(index);
			if(!table.data('isLoad')){
				loadTableData(table);
			}
		});
		
		loadTableData($('.table-list').first());
		
		/**加载表格数据**/
		function loadTableData(table, params){
			var url = $(table).attr('url'), templateId = $(table).attr('template') || '', params = params || {};
			var loading = layer.load(2);
			M.post(url, params, function(result){
				layer.close(loading);
				if($(table).is('#memberList')){
					//显示会员数
					$('#member_count_label').text(result.data.length || '0');
				}
				var html = template(templateId, {list: result.data});
				$(table).html(html);
				$(table).data('isLoad',true);
			}, function(result){
				layer.close(loading);
				M.alert('加载列表失败！');
			});
		}
		
		template.helper('formatMobile', function (value) {
			if(value && value.length > 10){
				return value.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2')
			}
			return value;
		});
	});
</script>
</html>