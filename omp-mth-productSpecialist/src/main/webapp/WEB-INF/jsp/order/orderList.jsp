<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<form id="order_query_form">
	<table class="sto-inquire" id="orderConditionTable">
		<tr>
			<td><input type="text" name="order_like" placeholder="订单号、联系人、联系电话、物流单号" style="width: 380px;"/></td>			
			<td><label><input type="checkbox" name="order_status" value="processing"/>待确认</label></td>
			<td><label><input type="checkbox" name="order_status" value="confirmed,payed"/>待发货</label></td>
			<td><label><input type="checkbox" name="order_status" value="cancelled"/>已取消</label></td>
			<td>日期：</td>
			<td><input type="text" name="order_date_start" data-beatpicker="true" style="width: 120px;"/></td>
			<td>~&nbsp;&nbsp;</td>
			<td><input type="text" name="order_date_end" data-beatpicker="true" style="width: 120px;"/></td>
			<td>
				<a href="javascript:void(0);" class="ck" onclick="searchOrder();">查询</a>&nbsp;
				<a href="javascript:void(0);" class="ck" onclick="exportOrder();">导出</a>
			</td>
		</tr>
	</table>
</form>
<table class="sto-tab page-list-table" id="orderListTable" template="orderListTpl" url="${ctx}/order/loadTeamOrderListPage" isNullMsg="false" pageSize="100" pages="100,200,500" visiblePages="10"></table>
<script id="orderListTpl" type="text/html">
	<tr class="tab-ti">
		<td>订单号</td>
		<td style="text-align: right;">数量</td>
		<td style="text-align: right;">金额</td>
		<td>日期</td>
		<td>配送地址</td>
		<td>联系人</td>
		<td>联系电话</td>
		<td>订单状态</td>
		<td>结算状态</td>
		<td>物流信息</td>
		<td>用户备注</td>
		<td>后台备注</td>
		<td>操作</td>
	</tr>
	{{each list as order i}}
		{{order | parseJson:'logistics'}}
		<tr>
			<td>{{order.order_no}}</td>
			<td style="text-align: right;">{{order.item_num}}</td>
			<td style="text-align: right;">{{order.total_fee}}</td>
			<td>{{order.order_date}}</td>
			<td>{{order.delivery_address}}</td>
			<td>{{order.contact_person}}</td>
			<td>{{order.contact_tel}}</td>
			<td>
				{{if order.status == 'non_paid'}}
					<span class="orange">未支付</span>
				{{else if order.status == 'processing'}}
					<span class="orange">待确认</span>
				{{else if order.status == 'confirmed'}}
					<span class="orange">已确认</span>
				{{else if order.status == 'delivered'}}
					已发货
				{{else if order.status == 'payed'}}
					<span class="orange">已付款</span>
				{{else if order.status == 'received' || order.status == 'closed'}}
					<span class="green">已完成</span>
				{{else if order.status == 'activing'}}
					团购中
				{{else if order.status == 'cancelled'}}
					已取消
				{{else}}
					未知状态
				{{/if}}
			</td>
			<td>
				{{if order.status == 'received' && order.settle_status == 'pending'}}
					<span class="orange">待结算</span>
				{{else if order.settle_status == 'settled'}}
					<span class="green">已结算</span>
				{{else}}
				{{/if}}
			</td>
			<td>{{order.logistics.company}}<br/>{{order.logistics.number}}</td>
			<td>{{order.remark}}</td>
			<td>{{order.biz_remark}}</td>
			<td>
				{{if order.status == 'processing'}}
					<a href="javascript:confirmOrder('{{order.order_id}}');" class="ck">确认</a>
					<a href="javascript:deliveryOrder('{{order.order_id}}');" class="ck">发货</a>
					<a href="javascript:cancelOrder('{{order.order_id}}', '{{order.payment_way_key}}', {{order.total_fee}});" class="ck">取消</a>
				{{else if order.status == 'confirmed'}}
					<a href="javascript:deliveryOrder('{{order.order_id}}');" class="ck">发货</a>
					<a href="javascript:cancelOrder('{{order.order_id}}', '{{order.payment_way_key}}', {{order.total_fee}});" class="ck">取消</a>
				{{else if order.status == 'delivered'}}
				{{else if order.status == 'payed'}}
					<a href="javascript:deliveryOrder('{{order.order_id}}');" class="ck">发货</a>
					<a href="javascript:cancelOrder('{{order.order_id}}', '{{order.payment_way_key}}', {{order.total_fee}});" class="ck">取消</a>
				{{else if order.status == 'received'}}
				{{else if order.status == 'cancelled'}}
				{{/if}}
				<a href="javascript:editBizRemark('{{order.order_id}}');" class="ck">编辑</a>
			</td>
		</tr>
	{{/each}}
</script>
<div id="delivery_info_div" class="added-ups" style="display: none;">
	<form id="deliveryForm">
		<table>
			<tr>
				<td align="right">物流公司：</td>
				<td align="left">
					<input type="text" name="company" datatype="*"/>
				</td>
			</tr>
			<tr>
				<td align="right">物流单号：</td>
				<td align="left">
					<input type="text" name="number" datatype="*"/>
				</td>
			</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
	//确认订单
	function confirmOrder(order_id){
		layer.confirm('是否确认订单？', {icon: 3}, function(index){
			layer.close(index);
			var params = {'order_id' : order_id, 'status': 'confirmed'};
			editOrder(params);
		});
		
		//编辑订单信息
		function editOrder(params, fn){
			if(params){
				var loading = layer.load();
				M.post("${ctx}/order/editOrder", params, function(result){
					layer.close(loading);
					searchOrder();
					if($.isFunction(fn)){
						fn();
					}
				}, function(result){
					layer.close(loading);
					M.alert('操作失败！');
				});
			}
		}
	}
	
	//订单取消功能
	function cancelOrder(order_id, payment_way_key, amount){
		if(order_id){
			layer.prompt({
			    title: '请填写取消原因',
			    formType: 2
			}, function(value, index, elem){
			    layer.close(index);
				var params = {'remark': value, 'order_id': order_id};
		    	if(payment_way_key && payment_way_key == 'ZFFS_08'){	//金币支付
		    		amount = parseFloat(parseFloat(amount) * 100).toFixed(2);
		    	}
		    	params.amount = amount || 0;
		    	refundOrder(params);
			});
		}
		
		//订单退款
		function refundOrder(params){
			var loading = layer.load();
			M.post('${ctx}/order/refundOrder', params, function(result){
				layer.close(loading);
				searchOrder();
			}, function(result){
				layer.close(loading);
				M.alert('订单取消失败！');
			});
		}
	}
	
	//发货
	function deliveryOrder(order_id){
		var deliveryForm = $('#deliveryForm').Validform({
			tiptype : 3
		});
		layer.open({
			title:  ['物流信息', 'font-size:15px;font-weight: bold;'],
			type: 1,
			icon: 1,
		    btn: ['确定', '取消'],
			shadeClose: true,
			shade: 0.8,
			zIndex: 98,
			shadeClose: false,
			area: ['400px', '210px'],
			content: $("#delivery_info_div"),
			yes: function(index){
				if(deliveryForm.check(false)){
					//算出物流信息
					var logistics = JSON.stringify(M.formatElement("#deliveryForm"));
					var loading = layer.load();
					M.post('${ctx}/order/deliveryOrder', {'order_id': order_id, 'logistics': logistics}, function(result){
						layer.close(loading);
						layer.close(index);	//关闭弹出层
						searchOrder();
					}, function(result){
						layer.close(loading);
						M.alert('操作失败！');
					});
				}
			},
			cancel: function(index){
		    	layer.close(index);
		    },
		    end: function(index){
		    	deliveryForm.resetForm();	//重置表单
		    }
		});
	}
	
	//搜索订单信息
	function searchOrder(){
		$("#orderListTable").attr("params", JSON.stringify(formatParams()));
		M.paginator("#orderListTable");
	}
	
	//导出订单
	function exportOrder(){
		var params = formatParams();
		if(!params.order_date_start){
			M.alert('请选择开始时间！');
			return false;
		}
		window.location.href = "${ctx}/order/exportOrder?" + $.param(params);
	}
	
	/**格式化参数**/
	function formatParams(){
		var params = M.formatElement('#order_query_form')
		var statusArray = new Array();
		$("#orderConditionTable input[name='order_status']:checked").each(function(){
			statusArray.push($(this).val());
		});
		params.status = statusArray.join(",");
		return params;
	}
	
</script>