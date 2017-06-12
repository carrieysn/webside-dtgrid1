<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ul class="login-step ">
	<li>
		<span class="fl">店铺名称</span>
		<span class="fr"><input type="text" name="stores_name" placeholder="输入店铺名称" datatype="*" nullmsg="请输入店铺名称！"></span>
	</li>
	<li>
		<span class="fl">店主姓名</span>
		<span class="fr"><input type="text" name="contact_person" placeholder="输入店主姓名" datatype="*" nullmsg="请输入店主姓名！"></span>
	</li>
	<li>
		<span class="fl">联系电话</span>
		<span class="fr"><input type="number" name="contact_tel" id="contact_tel_input" placeholder="输入手机号" datatype="m" nullmsg="请输入联系电话！" errormsg="请输入正确的联系电话！"></span>
	</li>
</ul>
<ul class="login-step top0">
	<li>
		<span class="fl">所在地区</span>
		<span class="fr areaContianer">
			<select class="bind-province"></select>
			<select class="bind-city"></select>
			<select class="bind-district" name="area_id" datatype="*" nullmsg="请选择所在地区！"></select>
			<input type="hidden" class="_path_" value="${_path_}">
			<input type="hidden" name="area_code" value="1000000">
		</span>
	</li>
	<li>
		<span class="fl">详细地址</span>
		<span class="fr"><input type="text" name="address" placeholder="输入详细地址" datatype="*" nullmsg="请输入详细地址！"></span>
	</li>
	<!-- <li>
		<span class="fl">位置</span>
		<span class="fr"><i class="icon-dw">精准定位</i></span>
	</li> -->
</ul>
<ul class="login-step top0">
	<li>
		<span class="fl">门店类型</span>
		<span class="fr">
			<select name="business_type_key" datatype="*" nullmsg="请选择门店类型！" style="width:2.9rem;">
				<option value="">选择类型</option>
				<c:forEach items="${businessTypeList}" var="businessType">
					<option value="${businessType.data_key}">${businessType.data_val}</option>
				</c:forEach>
			</select>
		</span>
	</li>
	<li>
		<span class="fl">门店面积</span>
		<span class="fr"><input type="text" name="area_size" placeholder="输入门店面积"></span>
	</li>
</ul>
<div class="btn-ups padding2">
    <a href="javascript:void(0);" class="cancel" onclick="prev(1);">上一步</a>
	<a href="javascript:void(0);" class="affirmance" onclick="next(1);">下一步</a>
</div>
<script type="text/javascript">
	$(function(){
		M.area('.areaContianer');
	});
</script>
