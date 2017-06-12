<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setAttribute("title", "卖单");%>
<%@ include file="/WEB-INF/jsp/mobile/common/common.jsp"%>
<body>
	<header id="search_contianer">
		<div class="head">
			<a href="${ctx}/mobile/user/center?token=${token}" class="return-two"></a>
			<label class="title">卖单</label>
		</div>
		<div class="had-inquire top-40">
			<input type="text" name="order_like" placeholder="订单号、商品名称、商品、厂商、联系电话、物流单号" class="inq-text" />
			<a href="javascript:searchOrder();" class="inq-btn">查询</a>
		</div>
		<div class="had-checkbox">
			<label><input type="checkbox" name="order_status" value="processing">待确认</label>
			<label><input type="checkbox" name="order_status" value="confirmed,payed">待发货</label>
		</div>
	</header><!-- /header -->
	<ul class="orders-wrap" id="order_list_ul"></ul>
	<script id="orderListTpl" type="text/html">
		{{each list as order i}}
			<li>
				<div>
					<em class="odd-number">订单号: {{order.order_no}}</em>
					<small class="order-status yfk">
						{{if order.status == 'processing'}}
							待确认
						{{else if order.status == 'confirmed'}}
							已确认
						{{else if order.status == 'delivered'}}
							已发货
						{{else if order.status == 'payed'}}
							已付款
						{{else if order.status == 'received' || order.status == 'closed'}}
							已完成
						{{else if order.status == 'cancelled'}}
							已取消
						{{else}}
							未知状态
						{{/if}}
					</small>
				</div>
				<div class="name-date">
					<p class="god-name">{{order.item_goods_title}}</p>
					<p>
						<em class="order-date">日期：{{order.order_date}}</em>
						<em class="order-number">x{{order.item_qty}}</em>
						<em class="order-price">¥{{order.total_fee}}</em>
					</p>
				</div>
				<div>
					<em class="order-name">{{order.contact_person}}：{{order.contact_tel}}</em>
					<em class="order-btn">
						{{if order.status == 'processing'}}
							<a href="javascript:cancelOrder('{{order.order_id}}', '{{order.payment_way_key}}', {{order.total_fee}});" class="ck">取消</a>
							<a href="javascript:deliveryOrder('{{order.order_id}}');" class="red">发货</a>
							<a href="javascript:confirmOrder('{{order.order_id}}');" class="red">确认</a>
						{{else if order.status == 'confirmed'}}
							<a href="javascript:cancelOrder('{{order.order_id}}', '{{order.payment_way_key}}', {{order.total_fee}});" class="ck">取消</a>
							<a href="javascript:deliveryOrder('{{order.order_id}}');" class="red">发货</a>
						{{else if order.status == 'payed'}}
							<a href="javascript:cancelOrder('{{order.order_id}}', '{{order.payment_way_key}}', {{order.total_fee}});" class="ck">取消</a>
							<a href="javascript:deliveryOrder('{{order.order_id}}');" class="red">发货</a>
						{{/if}}
					</em>
				</div>
				<div class="order-delivery">
					{{order.delivery_address}}
				</div>
			</li>
		{{/each}}
	</script>
	<div class="ship-slide">
		<div class="ship-abs">
			<ul class="ship-info">
				<li>
					<p class="ti">物流公司</p>
					<p class="nr">
						<input type="hidden" name="params">
						<input type="text" name="company" class="int" placeholder="请选择"/>
						<select>
							<option value="请选择">请选择</option>
							<option value="德邦物流">德邦物流</option>
							<option value="顺丰物流">顺丰物流</option>
							<option value="圆通物流">圆通物流</option>
							<option value="申通物流">申通物流</option>
							<option value="EMS物流">EMS物流</option>
							<option value="宅急送物流">宅急送物流</option>
						</select>
					</p>
				</li>
				<li>
					<p class="ti">物流单号</p>
					<p class="nr">
						<input type="text" name="number" placeholder="请输入"/>
					</p>
				</li>
			</ul>
		</div>
		<div class="btn-ups">
			<a href="javascript:void(0);" class="cancel">取消</a>
			<a href="javascript:void(0);" class="affirmance">发货</a>
		</div>
	</div>
</body>
  <script type="text/javascript">
  	 //确定订单
  	 function confirmOrder(order_id){
  		if(window.confirm("是否确认订单？")){
  			$.post("${ctx}/mobile/order/editOrder?token=${token}", {'order_id': order_id, 'status': 'confirmed'}, function(result){
 				if(result.status == '1'){
 					searchOrder();
 				} else{
 					M.alert('操作失败！');
 				}
 			}, "json");
		}
  	 }
  	 
 	//订单取消功能
 	function cancelOrder(order_id, payment_way_key, amount){
 		if(order_id){
 			var value = window.prompt("请填写取消原因");
 			if(value){
 				var params = {'remark': value, 'order_id': order_id};
 				if(payment_way_key && payment_way_key == 'ZFFS_08'){	//金币支付
		    		amount = parseFloat(parseFloat(amount) * 100).toFixed(2);
		    	}
		    	params.amount = amount || 0;
		    	refundOrder(params);
 			}
 		}
 		//订单退款
 		function refundOrder(params){
 			$.post("${ctx}/mobile/order/refundOrder?token=${token}", params, function(result){
 				if(result.status == '1'){
 					searchOrder();
 				} else{
 					M.alert('订单取消失败！');
 				}
 			}, "json");
 		}
 	}
 	
 	//发货操作
 	function deliveryOrder(order_id){
 		$('.ship-slide').show();
		$('body').bind('touchmove', function(event) {
			event.preventDefault();
		});
		$('.ship-info input[name="params"]').val(JSON.stringify({'order_id': order_id}));
 	}
 	
 	//发货操作
 	$('.ship-slide .affirmance').click(function(){
 		var company = $('.ship-info input[name="company"]').val();
 		var number = $('.ship-info input[name="number"]').val();
 		if(!(company && number)){
 			M.alert('请填写物流信息！');
 		} else{
			var params = JSON.parse($('.ship-info input[name="params"]').val());
			var logistics = {company: company, number: number};
			params.logistics = JSON.stringify(logistics);
			$.post("${ctx}/mobile/order/deliveryOrder?token=${token}", params, function(result){
 				if(result.status == '1'){
 					searchOrder();
 				} else{
 					M.alert('订单发货失败！');
 				}
 				closeDeliverDiv();
 			}, "json");
 		}
	});
 	
 	//禁止点透
 	FastClick.attach(document.body);
 	//选择物流公司
 	$("select").change(function(){
		var txt = $(this).find('option:checked').text();
		$('.ship-info input[name="company"]').val(txt);
 	});
	//发货取消
	$('.ship-slide .cancel').click(function(){
		closeDeliverDiv();
	});
	//关闭发货弹出层
	function closeDeliverDiv(){
		$('body').unbind('touchmove');
		$('.ship-slide').hide();
		$('.ship-info input').val("");
	}
	//textarea光标事件
	$('.ship-info .nr input').focus(function(){
		$('body').unbind('touchmove');
	});
	$('.ship-info .nr input').blur(function(){
		$('body').bind('touchmove', function(event) {
			event.preventDefault();
		});
	});
	
 	var page = $("#order_list_ul").mPage({
		url: '${ctx}/mobile/order/loadSellOrderListPage?token=${token}',
		templateId: 'orderListTpl'
	});
 	/*搜索订单信息**/
 	function searchOrder(){
 		page.mPage('reload', getParams());		
		/*获取用户选择的参数信息*/
		function getParams(){
			var order_like = $("#search_contianer input[name='order_like']").val();
			var statusArray = new Array();
			$("#search_contianer input[name='order_status']:checked").each(function(){
				statusArray.push($(this).val());
			});
			return {'order_like': order_like, 'status': statusArray.join(",")};
		}
 	}
  </script>
</body>
</html>