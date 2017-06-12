<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setAttribute("title", "统计分析");%>
<%@ include file="/WEB-INF/jsp/mobile/common/common.jsp"%>
<body style="background:#ffffff;">
	<header>
		<div class="head">
			<a href="javascript: window.history.go(-1);" class="return-two"><em></em></a>
			<label class="title">统计分析</label>
		</div>
	</header><!-- /header -->
	<div class="icon-filter top-40">
		<em id="date_type_text">昨日</em>&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" class="small-icons"></a>
		<ul class="select-nav">
			<li><a href="javascript:void(0);" date_type="-1" class="sel">昨日</a></li>
			<li><a href="javascript:void(0);" date_type="-7">最近7天</a></li>
			<li><a href="javascript:void(0);" date_type="-30">最近30天</a></li>
		</ul>
	</div>
	<div class="nav-left count-title-wrap">
	    <div class="swiper-container2">
	        <div class="swiper-wrapper count-title">
	        	<div class="swiper-slide sel" data_source="consumer">
	            	消费者
	            </div>
	            <div class="swiper-slide" data_source="stores">
	            	实体店
	            </div>
	            <div class="swiper-slide" data_source="supplier">
	            	供应商
	            </div>
	            <div class="swiper-slide" data_source="SJLY_09">
	            	兑换中心
	            </div>
	            <div class="swiper-slide" data_source="SJLY_06">
	            	惠易定2.0
	            </div>
	            <div class="swiper-slide" data_source="SJLY_07" style="line-height: normal;">
	            	慧顾家<br/>惠易定3.0
	            </div>
	            <div class="swiper-slide" data_source="SJLY_10">
	            	我要批
	            </div>
				<div class="swiper-slide" data_source="SJLY_11">
					超级返
				</div>
	        </div>
	        <!-- Add Pagination -->
	        <div class="swiper-pagination2"></div>
	    </div>
	</div>
	<div class="tab-list">
		<div class="tab-wrap">
			<%-- --%>
			<div class="wrap-tr" data_source="consumer" templateId="tradeTpl"></div>
			<div class="wrap-tr" data_source="stores" style="display: none;" templateId="tradeTpl"></div>
			<div class="wrap-tr" data_source="supplier" style="display: none;" templateId="tradeTpl"></div>
			<script id="tradeTpl" type="text/html">
				<table class="table-list">
					<tr class="ti">
						<td>统计日期</td>
						<td style="text-align: right;">现金余额</td>
						<%-- <td style="text-align: right;">礼券余额</td> --%>
						<td style="text-align: right;">金币余额</td>
						<td style="text-align: right;">现金交易</td>
						<%-- <td style="text-align: right;">礼券交易</td> --%>
						<td style="text-align: right;">金币交易</td>
					</tr>
					{{each resultList as trade i}}
						<tr>
							<td>{{trade.statistics_date_id}}</td>
							<td style="text-align: right;">{{trade.total_cash_balance | formatNumber}}</td>
							<%-- <td style="text-align: right;">{{trade.total_voucher_balance}}</td> --%>
							<td style="text-align: right;">{{trade.total_gold_balance}}</td>
							<td style="text-align: right;">{{trade.daily_cash_amount | formatNumber}}</td>
							<%-- <td style="text-align: right;">{{trade.daily_voucher_amount}}</td> --%>
							<td style="text-align: right;">{{trade.daily_gold_amount}}</td>
						</tr>
					{{/each}}
					<tr>
						<td><span>合计</td>
						<td><span>-</span></td>
						<%-- <td><span>-</span></td> --%>
						<td><span>-</span></td>
						<td style="text-align: right;"><span>{{totalMap.cash_amount_total | formatNumber}}</span></td>
						<%-- <td style="text-align: right;"><span>{{totalMap.voucher_amount_total}}</span></td> --%>
						<td style="text-align: right;"><span>{{totalMap.gold_amount_total}}</span></td>
					</tr>
					<tr>
						<td><span>日均</span></td>
						<td style="text-align: right;"><span>{{averageMap.total_cash_balance_average | formatNumber}}</span></td>
						<%-- <td style="text-align: right;"><span>{{averageMap.total_voucher_balance_average}}</span></td> --%>
						<td style="text-align: right;"><span>{{averageMap.total_gold_balance_average}}</span></td>
						<td style="text-align: right;"><span>{{averageMap.daily_cash_amount_average | formatNumber}}</span></td>
						<%-- <td style="text-align: right;"><span>{{averageMap.daily_voucher_amount_average}}</span></td> --%>
						<td style="text-align: right;"><span>{{averageMap.daily_gold_amount_average}}</span></td>
					</tr>
				</table>
			</script>
			<div class="wrap-tr" data_source="SJLY_09" style="display: none;" templateId="exchangeTpl"></div>
			<script id="exchangeTpl" type="text/html">
				<table class="table-list">
					<tr class="ti">
						<td>统计日期</td>
						<td style="text-align: right;">充值记录数</td>
						<td style="text-align: right;">充值金额</td>
						<td style="text-align: right;">兑换订单数</td>
						<td style="text-align: right;">兑换金额</td>
					</tr>
					{{each resultList as exchange i}}
						<tr>
							<td>{{exchange.statistics_date_id}}</span></td>
							<td style="text-align: right;">{{exchange.voucher_recharge_count}}</span></td>
							<td style="text-align: right;">{{exchange.voucher_recharge_amount | formatNumber}}</span></td>
							<td style="text-align: right;">{{exchange.voucher_exchange_count}}</span></td>
							<td style="text-align: right;">{{exchange.voucher_exchange_amount | formatNumber}}</span></td>
						</tr>
					{{/each}}
					<tr>
						<td><span>合计</span></td>
						<td style="text-align: right;"><span>{{totalMap.recharge_count_total}}</span></td>
						<td style="text-align: right;"><span>{{totalMap.recharge_amount_total | formatNumber}}</span></td>
						<td style="text-align: right;"><span>{{totalMap.exchange_count_total}}</span></td>
						<td style="text-align: right;"><span>{{totalMap.exchange_amount_total | formatNumber}}</span></td>
					</tr>
					<tr>
						<td><span>日均</span></td>
						<td style="text-align: right;"><span>{{averageMap.recharge_count_average}}</span></td>
						<td style="text-align: right;"><span>{{averageMap.recharge_amount_average | formatNumber}}</span></td>
						<td style="text-align: right;"><span>{{averageMap.exchange_count_average}}</span></td>
						<td style="text-align: right;"><span>{{averageMap.exchange_amount_average | formatNumber}}</span></td>
					</tr>
				</table>
			</script>
			<div class="wrap-tr" data_source="SJLY_06" templateId="orderTpl" style="display: none;"></div>
			<div class="wrap-tr" data_source="SJLY_07" templateId="orderTpl" style="display: none;"></div>
			<div class="wrap-tr" data_source="SJLY_10" templateId="orderTpl" style="display: none;"></div>
			<div class="wrap-tr" data_source="SJLY_11" templateId="orderTpl" style="display: none;"></div>
			<script id="orderTpl" type="text/html">
				<table class="table-list">
					<tr class="ti">
						<td>统计日期</p>
						<td style="text-align: right;">订单记录数</p>
						<td style="text-align: right;">订单金额</p>
					</tr>
					{{each resultList as order i}}
						<tr>
							<td>{{order.statistics_date_id}}</td>
							<td style="text-align: right;">{{order.order_count}}</td>
							<td style="text-align: right;">{{order.order_amount | formatNumber}}</td>
						</tr>
					{{/each}}
					<tr>
						<td><span>合计</span></td>
						<td style="text-align: right;"><span>{{totalMap.order_count_total}}</span></td>
						<td style="text-align: right;"><span>{{totalMap.order_amount_total | formatNumber}}</span></td>
					</tr>
					<tr>
						<td><span>日均</span></td>
						<td style="text-align: right;"><span>{{averageMap.order_count_average}}</span></td>
						<td style="text-align: right;"><span>{{averageMap.order_amount_average | formatNumber}}</span></td>
					</tr>
				</table>
			</script>
		</div>
	</div>
	<%@ include file="/WEB-INF/jsp/mobile/common/footer.jsp"%>
</body>
</html>
<script type="text/javascript" src="${ctx}/common/third-part/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/common/static/js/application.js"></script>
<script type="text/javascript">
	$(function() {
	    FastClick.attach(document.body);
	});
	//导航JS
	var swiper = new Swiper('.swiper-container2', {
	    pagination: '.swiper-pagination2',
	    slidesPerView: 'auto',
	    paginationClickable: true,
	    spaceBetween: 30
	});

	$(function(){
		$('.swiper-slide').click(function(){
			$('.swiper-slide').removeClass('sel');
			$(this).addClass('sel');
			var data_source = $(this).attr('data_source');
			$('.tab-wrap .wrap-tr').hide();
			$('.tab-wrap .wrap-tr[data_source="' + data_source + '"]').show();
			loadData();
		});
		
		//日期条件下拉
		$('.icon-filter .small-icons').click(function(){
			$('.select-nav').slideToggle(200);
		});
		
		//切换日期查询条件
		 $('.select-nav li').click(function(){
			 $('.select-nav li a').removeClass('sel');
			 var text = $(this).find('a').addClass('sel').text();
			 $('#date_type_text').text(text);
			 $('.select-nav').slideToggle(200);
			 $('.tab-wrap .wrap-tr').data('isLoad', false);
			 loadData();
		 });
		
		//默认加载第一个数据
		loadData();
		//加载数据
		function loadData(){
			var data_source = $('.swiper-slide.sel').attr('data_source');
			var target = $('.tab-wrap .wrap-tr[data_source="' + data_source + '"]');
			var isLoad = $(target).data('isLoad');
			if(!isLoad) {
				var templateId = $(target).attr('templateId');
				//加载信息
				var loading = layer.load(2);
				var params = {'data_source_type': data_source, 'date_type': $('.select-nav li a.sel').attr('date_type')};	//请求参数
				M.post('${ctx}/mobile/statistics/queryTradeOverview?token=${token}', params, function(result){
					layer.close(loading);
					var data = result.data;
					var html = template(templateId, data);
					$(target).html(html);
					$(target).data('isLoad', true);
				}, function(result){
					layer.close(loading);
					M.alert('加载信息失败！');
				});
			}
		}
	});
</script>