<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<div class="main-wrap">
			<div class="line-wrap">
                <span>模式</span>
                <input type="text" class="ipt-txt" placeholder="选择模式" value="${goods.category}"/>
                <input type="hidden" class="ipt-val" name="category" value="${goods.category}"/>
                <select class="tp-select tp" name="" datatype="*" nullmsg="请选择模式">
                    <option value="">请选择</option>
                    <c:forEach items="${categoryList}" var="category">
                    	<option value="${category.key}" <c:if test="${category.key eq goods.category}">selected="selected"</c:if>>${category.name}</option>
                   	</c:forEach>
                </select>
            </div>
            <div class="line-wrap bt">
                <span>市场价</span>
                <input type="number" class="ipt-val" name="market_price" value="${goods.market_price}" placeholder="请输入市场价" datatype="money" nullmsg="请输入市场价"/>
            </div>
            <div class="line-wrap">
                <span>优惠价</span>
                <input type="number" class="ipt-val" name="discount_price" value="${goods.discount_price}" placeholder="请输入优惠价" datatype="money" nullmsg="请请输入优惠价"/>
            </div>
            <div class="line-wrap bt">
                <span>起订</span>
                <input type="number" class="ipt-val" name="min_buy_qty" value="${goods.min_buy_qty}" placeholder="请输入起订量" datatype="n" nullmsg="请输入起订量"/>
            </div>
            <div class="line-wrap">
                <span>限购</span>
                <input type="number" class="ipt-val" name="max_buy_qty" value="${goods.max_buy_qty}" placeholder="请输入限购量" datatype="n" nullmsg="请输入限购量"/>
            </div>
            <div class="line-wrap">
                <span>有效期</span>
                <input type="text" class="ipt-val" placeholder="选择日期" value="${goods.valid_thru}"/>
                <input type="date" class="tp-ipt tp" name="valid_thru" value="${goods.valid_thru}"/>
            </div>
            <div class="line-wrap bt selectContainer" isCode="true">
            	<input type="hidden" id="delivery_area" name="delivery_area" value="${goods.delivery_area}">
                <span>配送范围</span>
                <select class="delivery-sel bind-district" allow-null="true"></select>
                <select class="delivery-sel bind-city" allow-null="true"></select>
                <select class="delivery-sel bind-province" allow-null="true"></select>
                <input type="hidden" class="_path_" value="${deliveryArea.path}">
            </div>
             <div class="line-wrap">
                <span>特殊说明</span>
                <input type="text" class="ipt-val" name="remark" value="${goods.remark}" placeholder="特殊说明"/>
            </div>
            <div class="line-wrap">
                <span>支付方式</span>
                <input type="text" class="ipt-txt" placeholder="选择支付方式" datatype="*" nullmsg="请选择支付方式" value="${empty goods.payment_way or goods.payment_way eq 'online'  ? '在线支付' : '货到付款'}"/>
                <input type="text" class="ipt-val" name="payment_way" value="${empty goods.payment_way ? 'online' : goods.payment_way}"/>
                <select class="tp-select tp">
                    <option>请选择</option>
                    <option value="online" <c:if test="${goods.payment_way eq 'online' or empty goods.payment_way}">selected="selected"</c:if>>在线支付</option>
                    <option value="offline" <c:if test="${goods.payment_way eq 'offline'}">selected="selected"</c:if>>货到付款</option>
                </select>
            </div>
        </div>