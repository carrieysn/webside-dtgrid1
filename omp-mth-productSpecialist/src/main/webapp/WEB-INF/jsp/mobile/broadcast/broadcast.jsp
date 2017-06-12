<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setAttribute("title", "实时播报");%>
<%@ include file="/WEB-INF/jsp/mobile/common/common.jsp"%>
<body>
	<div class="jsbb-bj">
		<header>
			<div class="head">
				<a href="${ctx}/mobile/user/center" class="return-two"><em></em></a>
				<label class="title">${now}</label>
				<a class="top-right"></a>
			</div>
		</header><!-- /header -->
		<!-- <div class="kaiqi">
			<p>开启红包<em id="gift_total">0</em>个</p>
			<p>派发奖品<em id="lucky_total">0</em>件</p>
		</div> -->
		<div class="dingdan">
			<div class="dd-wrap">
				<p class="nm">今日订单</p>
				<p class="big-number" id="order_total">0</p>
				<p class="decimal" id="order_amount">¥0.00</p>
			</div>
		</div>
		<div class="option-js">
			<div class="option-wrap">
				<div class="order_flow_chart" id="orderFlowChart"></div>
			</div>
			<ul class="data-list" id="categoryListTable"></ul>
			<script type="text/html" id="categoryListTpl">
				{{each list as goods i}}
					{{if i == 11}}{{
						<li class="icongd">
							<p class="nm">更多</p>
							<p class="nu">...</p>
						</li>
					{{/if}}
					<li {{if i > 10}}class="gd-nr" style="display:none;"{{/if}}>
						<p class="nm">{{goods.goods_type}}</p>
						<p class="nu">{{goods.goods_qty | formatPercent}}%</p>
					</li>
				{{/each}}
			</script>
		</div>
		<div class="md-wrap">
			<ul class="md-list">
				<li>
					<p class="nm">参与门店</p>
					<p class="nu" id="stores_total">0</p>
				</li>
				<li>
					<p class="nm">参与顾客</p>
					<p class="nu" id="consumer_total">0</p>
				</li>
			</ul>
			<div class="option-md-wrap">
				<div class="order_chart" id="orderChart"></div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${ctx}/static/mobile/js/echarts.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#categoryListTable').on('click', '.icongd', function(){
			$('#categoryListTable .gd-nr').fadeToggle();	
		});
		
		//加载抽奖红包数量
		/* M.post('loadGiftLuckyTotal?token=${token}',{}, function(result){
			var data = result.data || {};
			$('#lucky_total').text(M.fmoney(data.lucky_qty || '0'));
			$('#gift_total').text(M.fmoney(data.gift_qty || '0'));
		}, function(result){
			M.alert(result.msg || '加载抽奖红包数失败！');
		}); */
		
		//加载今日订单数据
		M.post('loadTodayOrderTotal?token=${token}',{}, function(result){
			var data = result.data || {};
			$('#order_total').text(M.fmoney(data.order_qty));
			$('#order_amount').text('¥' +　M.fmoney(data.order_amount || '0', 2));
		}, function(result){
			M.alert(result.msg || '加载今日订单数据失败！');
		});
		
		//加载参与会员统计
		M.post('loadOrderMemberNum?token=${token}',{}, function(result){
			var array = result.data || [];
			var stores_total = 0, consumer_total = 0;
			var areaArray = new Array();
			var qtyArray = new Array();
			//倒序
			for(var i = array.length - 1;i > -1;i--){
				var data = array[i];
				stores_total += parseInt(data.store_qty);
				consumer_total += parseInt(data.consumer_qty);
				if(data.order_qty > 0){
					areaArray.push(data.city_name);
					qtyArray.push(data.order_qty);
				}
			}
			$('#stores_total').text(M.fmoney(stores_total || '0'));
			$('#consumer_total').text(M.fmoney(consumer_total || '0'));
			//加载图表
			loadOrderChart(areaArray, qtyArray);
		}, function(result){
			M.alert(result.msg || '加载参与会员数失败！');
		});
		
		//加载商品分类
		M.post('loadGoodsCateoryStatistics?token=${token}',{}, function(result){
			var data = result.data || {};
			var total = data.total_qty || 1;
			template.helper('formatPercent', function (value) {
				total = total > 0 ? total : 1;
				return parseFloat(value * 100 / total).toFixed(1);
			});
			var html = template('categoryListTpl', data);
			$('#categoryListTable').html(html);
		}, function(result){
			M.alert(result.msg || '统计商品分类失败！');
		});
		
		//加载订单流量图
		M.post('loadOrderTraffic?token=${token}',{}, function(result){
			var array = result.data || [];
			var timeArray = new Array();
			var totalArray = new Array();
			for(var i = 0;i < array.length;i++){
				var data = array[i];
				timeArray.push((data.start_time.split(' ')[1]).substr(0,5));
				totalArray.push(data.order_qty);
			}
			loadOrderFlowChart(timeArray, totalArray);
		}, function(result){
			M.alert(result.msg || '加载实时订单流量图失败！');
		});
		
	});
</script>
<script type="text/javascript">
	var orderFlowChart = echarts.init(document.getElementById('orderFlowChart'), 'macarons');
	var orderChart = echarts.init(document.getElementById('orderChart'), 'macarons');
	/**加载订单流量统计**/
	function loadOrderFlowChart(timeArray, totalArray){
		orderFlowChart.setOption({
			tooltip: {
		        trigger: 'axis',
		        axisPointer: {
		            animation: false
		        }
		    },
		    calculable: true,
		    xAxis: {
		        type: 'category',
		        boundaryGap: false,
		        axisLine: {
		            lineStyle: {
		                color: '#524865',
		                width: 1
		            }
		        },
		        splitLine: {
		            lineStyle: {
		                color: '#524865',
		                width: 1
		            }
		        },
		        axisLabel: {
		        	interval:17,
		            textStyle: {
		                color: '#ffffff'
		            }
		        },
		        data: timeArray
		    },
		    yAxis: {
		        type: 'value',
		        axisLine: {
		            lineStyle: {
		                color: '#524865',
		                width: 1
		            }
		        },
		        axisLabel: {
		        	interval:0,
		        	show:false,
		        	margin: 5,
		            textStyle: {
		                color: '#ffffff'
		            }
		        },
		        splitLine: {
		            lineStyle: {
		                color: '#524865',
		                width: 1
		            }
		        },
		    },
		    series: [{
		        name: '订单数',
		        type: 'line',
		        stack: '总量',
		        itemStyle: {
		            normal: {
		                color: '#00bff7',
		                label: {
		                    position: 'top'
		                },
		                lineStyle: {
		                    width: 1,
		                }
		            }
		        },
		        label: {
		            normal: {
		                show: true,
		                position: 'top',
		                textStyle: {
		                    color: '#fff'
		                }
		            }
		        },
		        data: totalArray
		    }]
		
		});
	}
	
	/***加载订单图表数据**/
	function loadOrderChart(areaArray, qtyArray){
		orderChart.setOption({
		    
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis: {
		        axisLine: {
		        	interval:0,
		            lineStyle: {
		                color: '#524865',
		                width: 0
		            }
		        },
		        splitLine: {
		            lineStyle: {
		                color: '#524865',
		                width: 0
		            }
		        },
		        type: 'value',
		        position: 'none',
		        splitLine: {
		            show: false
		        },
		        axisLabel: {
		            show: false
		        }
		    },
		    yAxis: {
		        type: 'category',
		        splitLine: {
		            lineStyle: {
		                width: 0
		            },
		        },
		        axisLabel: {
		        	interval:0,
		            textStyle: {
		                color: '#ffffff'
		            }
		        },
		        data: areaArray
		    },
		    series: [{
		        name: '订单数',
		        type: 'bar',
		        barWidth:20,
	            barGap:'80%',
	            barCategoryGap:'40%',
		        itemStyle: {
		            normal: {
		                color: '#f3ee64',
		                lineStyle: {
		                    width: 1,
		                }
		            }
		        },
		        label: {
		            normal: {
		                show: true,
		                position: 'right',
		                textStyle: {
		                    color: '#fff'
		                }
		            }
		        },
		        data: qtyArray
		    }]
		});
	}
</script>
</html>