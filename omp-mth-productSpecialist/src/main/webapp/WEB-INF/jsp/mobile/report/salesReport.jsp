<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setAttribute("title", "销售报表");%>
<%@ include file="/WEB-INF/jsp/mobile/common/common.jsp"%>
<body style="background:#ffffff;">
	<header>
		<div class="head">
			<a href="javascript: window.history.go(-1);" class="return-two"><em></em></a>
			<label class="title">销售报表</label>
		</div>
	</header><!-- /header -->
	<div class="icon-filter areaContainer top-40">
		<em id="date_type_text" class="name-dq">地区：</em>
		<select class="sel-opt bind-province"  allow-null="true"></select>
		<select class="sel-opt bind-city" allow-null="true"></select>
		<select class="sel-opt bind-district" allow-null="true"></select>
		<input type="hidden" class="_path_" value="${area_path}">
	</div>
	<ul class="login-step date-top">
		<li>
			<span class="fl">日期：</span>
			<span class="fr k-645">
				<small><input type="date" name="begin_date" class="k-100" id="begin_date_input"> ~ 
				<input type="date" name="end_date" class="k-100" id="end_date_input"></small>
				<a href="javascript:void(0)" class="btn-ss" id="search_btn">查询</a>
			</span>
		</li>
	</ul>
	<ul class="title-list cutover-list">
		<li class="sel">按日期</li>
		<li>按门店</li>
		<li>按区域</li>
	</ul>
	<div class="goods-list">
		<%-- 按日期统计 --%>
		<div class="goods">
			<div style="overflow:auto;">
				<table class="table-list" id="dateReportListTable" template="dateReportListTpl" url="${ctx}/mobile/report/queryDateOrderList?token=${token}"></table>
				<script id="dateReportListTpl" type="text/html">
					<tr class="ti">
						<td>日期</td>
						<td style="text-align:right">订单数</td>
						<td style="text-align:right">订单金额</td>
						<td style="text-align:right">返款金额</td>
						<td style="text-align:right">参与门店</td>
						<td style="text-align:right">参与顾客</td>
					</li>
					{{each list as sales i}}
						<tr>
							<td>{{sales.order_date}}</td>
							<td style="text-align:right">{{sales.order_qty}}</td>
							<td style="text-align:right">{{sales.gmv_amount | formatNumber}}</td>
							<td style="text-align:right">{{sales.paid_amount | formatNumber}}</td>
							<td style="text-align:right">{{sales.store_qty}}</td>
							<td style="text-align:right">{{sales.consumer_qty}}</td>
						</tr>
					{{/each}}
				</script>
			</div>
		</div>
		<%-- 按门店统计 --%>
		<div class="goods" style="display:none;">
			<div style="overflow:auto;">
				<table class="table-list" id="storesReportListTable" template="storesReportListTpl" url="${ctx}/mobile/report/queryStoresOrderList?token=${token}"></table>
				<script id="storesReportListTpl" type="text/html">
					<tr class="ti">
						<td>门店</td>
						<td style="text-align: right;">门店订单数</td>
						<td style="text-align: right;">会员订单数</td>
						<td style="text-align: right;">有效订单数</td>
						<td style="text-align: right;">订单金额</td>
						<td style="text-align: right;">返款金额</td>
						<td style="text-align: right;">会员数</td>
					</tr>
					{{each list as report i}}
						<tr>
							<td>{{report.stores_name}}</td>
							<td style="text-align: right;">{{report.stores_order_qty}}</td>
							<td style="text-align: right;">{{report.consumer_order_qty}}</td>
							<td style="text-align: right;">{{report.closed_order_qty}}</td>
							<td style="text-align: right;">{{report.gmv_amount | formatNumber}}</td>
							<td style="text-align: right;">{{report.paid_amount | formatNumber}}</td>
							<td style="text-align: right;">{{report.consumer_qty}}</td>
						</tr>
					{{/each}}
				</script>
			</div>
		</div>
		<%-- 按区域 --%>
		<div class="goods" style="display:none;">
			<div style="overflow:auto;">
				<table class="table-list wid-td" id="areaReportListTable" template="areaReportListTpl" url="${ctx}/mobile/report/queryAreaOrderList?token=${token}"></table>
				<script id="areaReportListTpl" type="text/html">
					<tr class="ti">
						<td>城市</td>
						<td style="text-align: right;">订单数量</td>
						<td style="text-align: right;">订单金额</td>
						<td style="text-align: right;">返款金额</td>
						<td style="text-align: right;">参与门店</td>
						<td style="text-align: right;">参与顾客</td>
					</li>
					{{each list as report i}}
						<tr>
							<td>{{report.province_name || report.city_name || report.district_name}}</td>
							<td style="text-align: right;">{{report.order_qty}}</td>
							<td style="text-align: right;">{{report.gmv_amount | formatNumber}}</td>
							<td style="text-align: right;">{{report.paid_amount | formatNumber}}</td>
							<td style="text-align: right;">{{report.store_qty}}</td>
							<td style="text-align: right;">{{report.consumer_qty}}</td>
						</tr>
					{{/each}}
				</script>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/jsp/mobile/common/footer.jsp"%>
</body>
<script type="text/javascript" src="${ctx}/common/static/js/areaSelector.js"></script>
<script type="text/javascript">
	$(function(){
		M.area('.areaContainer');
		//销售报表切换
		$('.title-list li').click(function(){
			$('.title-list li').removeClass('sel');
			$(this).addClass('sel');
			$('.goods-list .goods').hide();
			var index = $(this).index('.title-list li');
			var table = $('.goods-list .goods').eq(index).show().find('.table-list');
			loadData(table);
		});
		
		//查询事件
		$('#search_btn').on('click', function(){
			var begin_date = $('#begin_date_input').val();
			var end_date = $('#end_date_input').val();
			if(!begin_date){
				M.alert('请输入开始时间！');
				return false;
			}
			if(!end_date){
				M.alert('请输入结束时间！');
				return false;
			}
			var area_id = undefined, area_type = undefined;
			if($('.areaContainer .bind-district').val()){
				area_id = $('.areaContainer .bind-district').val();
				area_type = 'district';
			} else if($('.areaContainer .bind-city').val()){
				area_id = $('.areaContainer .bind-city').val();
				area_type = 'city';
			} else if($('.areaContainer .bind-province').val()){
				area_id = $('.areaContainer .bind-province').val();
				area_type = 'province';
			} else{
				area_id = 'ebb41a70-bb51-11e5-a4b3-00163e0009c6';
				area_type = 'country';
			}
			var params = {'area_id': area_id, 'area_type': area_type, 'begin_date': begin_date, 'end_date': end_date};
			$('.goods-list .goods .table-list').data('params', JSON.stringify(params));
			reloadData();
			
		});
		//重新加载表格数据
		function reloadData(){
			$('.goods-list .table-list').data('isLoad', false);
			var index = $('.title-list li.sel').index('.title-list li');
			var table = $('.goods-list .goods').eq(index).find('.table-list');
			loadData(table);
		}
		
		/**加载数据**/
		function loadData(table){
			var isLoad = $(table).data('isLoad');
			var params = $(table).data('params');
			if(!isLoad && params){
				loadTableData(table, JSON.parse(params));
			}
		}
		
		/***加载表格数据**/
		function loadTableData(table, params){
			var url = $(table).attr('url'), templateId = $(table).attr('template') || '', params = params || {};
			var loading = layer.load(2);
			M.post(url, params, function(result){
				layer.close(loading);
				if(result.data.length < 1){
					M.alert('暂无数据！');
				}
				var html = template(templateId, {list: result.data});
				$(table).html(html);
				$(table).data('isLoad',true);
			}, function(result){
				layer.close(loading);
				M.alert(result.msg || '加载列表失败！');
			});
		}
	});
</script>
</html>