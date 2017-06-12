<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setAttribute("title", "买手-首页");%>
<%@ include file="/common/layout/header.jsp"%>
<%@ include file="/common/layout/menu.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/main/select2/css/select2.min.css" />
<script type="text/javascript" src="${ctx}/static/main/select2/js/select2.full.min.js"></script>
<script type="text/javascript" src="${ctx}/static/main/select2/js/i18n/zh-CN.js"></script>
<!-- main container -->
<%@ include file="/WEB-INF/jsp/searchbar.jsp" %>
<div class="main-container">
	<div class="buyer-center">
		<div class="buyer-fl">
			<div class="wap-left">
				<form id="productForm" method="post" onsubmit="return false;">
					<input type="hidden" name="goods_id" value="${goods.goods_id}">
					<input type="hidden" name="created_date" value="${goods.created_date}">
					<p class="new-ti">产品详情</p>
					<table class="new-tab">
						<tr>
							<td align="right" width="60"><em class="mandatory">*</em>名称：</td>
							<td colspan="3" width="600">
								<label><input type="text" name="title" value="${goods.title}" placeholder="产品名称0/50" datatype="*1-50" errormsg="最多输入50个字符！"></label>
							</td>
						</tr>
						<tr>
							<td align="right" valign="top" width="60"><em class="mandatory">*</em>描述：</td>
							<td colspan="3">
								<label><textarea name="desc1" placeholder="请对您的产品进行描述0/500" datatype="*1-500" errormsg="最多输入500个字符！">${goods.desc1}</textarea></label>
							</td>
						</tr>
						<tr>
							<td align="right" valign="top" width="60"><em class="mandatory">*</em>照片：</td>
							<td class="photo pic_td" colspan="3">
								<input type="hidden" name="pic_info" id="pic_info" value="${goods.pic_info}">
								<!-- <div>
									<p><a class="preview"></a><a href="javascript:void(0);" class="add-img"></a></p>
									<p><label>添加描述</label><input type="hidden" class="pic_desc"></p><em></em>
								</div> -->
								<ul>
									<c:forEach begin="0" end="4">
										<li>
											<p><a class="preview"></a><a href="javascript:void(0);" class="add-img"></a></p><em></em>
											<p><label>添加描述</label><input type="hidden" class="pic_desc"></p>
										</li>
									</c:forEach>
								</ul>
							</td>
						</tr>
						<tr>
							<td align="right" valign="top" width="60">详情：</td>
							<td class="photo pic_detail_td" colspan="3">
								<input type="hidden" name="pic_detail_info" id="pic_detail_info" value="${goods.pic_detail_info}">
								<!--<div>
									<p><a class="preview"></a><a href="javascript:void(0);" class="add-img"></a></p>
									<p><label>添加描述</label><input type="hidden" class="pic_desc"></p><em></em>
								</div>-->
								<ul>
									<c:forEach begin="0" end="9">
										<li>
											<p><a class="preview"></a><a href="javascript:void(0);" class="add-img"></a></p><em></em>
											<p><label>添加描述</label><input type="hidden" class="pic_desc"></p>
										</li>
									</c:forEach>
								</ul>
							</td>
						</tr>
						<tr>
							<td></td>
							<td colspan="3">
								<div class="img-rule">
									<p class="ti"><!-- 图片上传规则： --></p>
									<p>1.图片尺寸：800x800像素（请保证为正方形）</p>
									<p>2.只能上传200kb以内jpg格式的图片</p>
								</div>
							</td>
						</tr>
						<tr>
							<td align="right" class="new-tag" width="70"><em class="mandatory">*</em>联系人：</td>
							<td width="250">
								<input type="text" name="contact_person" value="${goods.contact_person}" class="teg-input" style="width: 240px;" datatype="*1-30" errormsg="请输入正确联系人！" />
							</td>
							<td align="right" class="new-tag" width="60"><em class="mandatory">*</em>电话：</td>
							<td width="250">
								<input type="text" name="contact_tel" value="${goods.contact_tel}" class="teg-input" style="width: 240px;" datatype="m" errormsg="请输入正确的手机号码" />
							</td>
						</tr>
						<tr>
							<td align="right" class="new-tag" width="70">厂家：</td>
							<td width="250">
								<input type="text" name="manufacturer" value="${goods.manufacturer}" class="teg-input" style="width: 240px;"/>
							</td>
							<td></td>
							<td></td>
							<%-- <td align="right" class="new-tag" width="70">经销商：</td>
							<td width="250">
								<input type="text" name="supplier" value="${goods.supplier}" class="teg-input" style="width: 240px;"/>
								<select name="supplier_id" style="width: 240px;" id="supplier_select" datatype="*">
									<c:if test="${not empty goods.supplier_id}">
										<option value="${goods.supplier_id}" selected="selected">${goods.supplier}</option>
									</c:if>
								</select>
							</td> --%>
						</tr>
						<tr>
							<td align="right" width="60">标签：</td>
							<td class="new-tag td-tag" colspan="3">
								<!-- <a href="#" class="addhax addhax-sel" >惠安心</a>
								<a href="#" class="addhax" >大厂直供</a>
								<a href="#" class="addhax" >新奇特</a> -->
								<input type="hidden" id="tags_str" name="label" value="${goods.label}"/> 
								<input type="text" class="teg-input"/>
								<a href="javascript:void(0);" class="add" id="add_tag">添加</a>
								<a href="javascript:void(0);" class="btn-clear" id="clear_tag">清除</a>
							</td>
						</tr>
					</table>
					<p class="new-ti">销售方案</p>
					<table class="release-cen">
						<tr>
							<td align="right" width="100" valign="top"><em class="mandatory">*</em>类目：</td>
							<td colspan="3">
								<input type="hidden" name="display_area" id="display_area" value="${goods.display_area}">
								<c:forEach items="${appEnumList}" var="appEnum" varStatus="enumStatus">
									<c:forEach items="${appEnum.app_display_area_list}" var="displayArea" varStatus="status">
										<label for="display_area_ck${enumStatus.index}${status.index}"><input type="checkbox" name="display_area_ck" id="display_area_ck${enumStatus.index}${status.index}" value="${displayArea.display_area}" datatype="*" style="width: 13px;height: 13px;">
										${displayArea.display_area}</label>
									</c:forEach>
									<br/>
								</c:forEach>
							</td>
						</tr>
						<tr>
							<td align="right" width="100"><em class="mandatory">*</em>模式：</td>
							<td>
								<select name="category" style="width:150px; border:1px solid #DDD;float:left; height:30px;color:#666;font-size:14px;" datatype="*" id="category_select">
									<option value="">---请选择---</option>
									<c:forEach items="${categoryList}" var="category">
										<option value="${category.key}" <c:if test="${category.key eq goods.category}">selected="selected"</c:if>>${category.name}</option>
									</c:forEach>
								</select>
							</td>
							<%-- <td id="product_source_tr" style="display: none;" colspan="2">
								<div class="td-lj">
									<p>链接：</p>
									<input type="text" id="product_source" name="product_source" value="${goods.product_source}" class="teg-input" style="width: 240px;" ignore="ignore"/>
								</div>
							</td> --%>
						</tr>
						<tr>
							<td align="right" width="100"><em class="mandatory">*</em>市场价：</td>
							<td><input type="text" name="market_price" value="${goods.market_price}" datatype="relativePrice" errormsg="市场价不能小于优惠价！"/></td>
							<td align="right" width="100"><em class="mandatory">*</em>优惠价：</td>
							<td>
								<input type="text" name="discount_price" value="${goods.discount_price}" datatype="relativePrice" errormsg="优惠价不能大于市场价！"/>
								<input type="hidden" id="ladder_price" name="ladder_price" value="{}">
							</td>
						</tr>
						<tr>
							<td align="right" width="100"><em class="mandatory">*</em>规格：</td>
							<td><input type="text" name="specification" value="${goods.specification}" datatype="*"/></td>
							<td align="right" width="100"><em class="mandatory">*</em>库存：</td>
							<td width="500">
								<input type="text" name="sale_qty" value="${goods.sale_qty}" datatype="minNum" errormsg="请输入正确的数量！" style="width: 145px;"/>
							</td>
						</tr>
						<tr>
						<td align="right" width="100"><em class="mandatory">*</em>起订：</td>
							<td>
								<input type="text" name="min_buy_qty" value="${goods.min_buy_qty}" datatype="minNum" errormsg="请输入正确的数量！" style="width: 145px;"/>
							</td>
							<td align="right" width="100">限购：</td>
							<td>
								<input type="text" name="max_buy_qty" value="${goods.max_buy_qty}" datatype="n" ignore="ignore"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="100"><em class="mandatory">*</em>开卖时间：</td>
							<td colspan="3">
								<input type="text" name="valid_thru" value="${goods.valid_thru}" datatype="*" data-beatpicker="true" data-beatpicker-id="validDatePicker"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="100" valign="top">配送范围：
								<input type="hidden" id="delivery_area" name="delivery_area" value="${goods.delivery_area}">
							</td>
							<td class="peisong delivery_area_div" colspan="3">
								<div class="delivery_area_div">
									<div class="selectContainer" isCode="true">
										<select class="bind-province" allow-null="true"></select>
										<select class="bind-city" allow-null="true"></select>
										<select class="bind-district" allow-null="true"></select>
										<input type="hidden" class="_path_" value="${deliveryArea.path}">
									</div>
								</div>
								<div style="margin-top: -15px;">
									<input type="text" name="remark" value="${goods.remark}" style="width: 580px;">（特殊说明）
								</div>
							</td>
						</tr>
						<tr id="payment_way_div">
							<td align="right" width="100"><em class="mandatory">*</em>支付方式：</td>
							<td colspan="3" class="zhifu-label">
								<label><input type="radio" name="payment_way" value="online" <c:if test="${goods.payment_way eq 'online' or empty goods.payment_way}">checked="checked"</c:if> datatype="*">在线支付</label>
								<label><input type="radio" name="payment_way" value="offline" <c:if test="${goods.payment_way eq 'offline'}">checked="checked"</c:if> datatype="*">货到付款</label>
							</td>
						</tr>
					</table>
					<table class="new-tab">
						<tr>
							<td></td>
							<td></td>
						</tr>
					</table>
					<div class="but-push">
						<div><a href="javascript:window.history.go(-1);">取消</a></div>
						<div class="release">
							<a href="javascript:void(0);" id="submit_btn">发布</a>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div id="export-div" style="display: none;">
			<div class="audit-cen">
				<p>
					<a class="preview" style="display: none"><img style="width: 240px;height: 240px;"></a>
					<a href="javascript:void(0);" class="add-img upload-img"></a>
				</p>
				<textarea placeholder="描述0/100" id="upload-img-desc" ></textarea>
			</div>
		</div>
		<%@ include file="/WEB-INF/jsp/userinfo.jsp" %>
	</div>
	<script type="text/javascript" src="${ctx}/common/static/js/areaSelector.js"></script>
	<script type="text/javascript">
		var goods_display_area = "${goods.display_area}";
	</script>
	<script type="text/javascript" src="${ctx}/static/main/js/create.js?v=0.15"></script>
	<%-- 编辑时，对图片与标签进行初始化 --%>
	<c:if test="${edit eq true}">
		<script type="text/javascript">
			$(function(){
				//1、初始化标签信息
				var label = "${goods.label}";
				if(label){
					var TagObj = $(".td-tag .teg-input");
					label = label.split(/[,，]/);
					$.each(label, function(index, value){
						TagObj.before("<small>" + value + "</small>");
					});
				}
				//2、初始化图片信息
				var picArray = ${empty goods.pic_info ? '[]' : goods.pic_info};
				if(picArray && picArray.length > 0){
					$.each(picArray, function(index, pic){
						var imgUpload = $(".pic_td .add-img:eq(" + index + ")");
						var doc_id = pic.path_id;
						var title = pic.title;
						initPicImage(imgUpload, doc_id, title);
					});
				}
				
				var picDetailArray = ${empty goods.pic_detail_info ? '[]' : goods.pic_detail_info};
				if(picDetailArray && picDetailArray.length > 0){
					$.each(picDetailArray, function(index, pic){
						var imgUpload = $(".pic_detail_td .add-img:eq(" + index + ")");
						var doc_id = pic.path_id;
						var title = pic.title;
						initPicImage(imgUpload, doc_id, title);
					});
				}
				
				//初始化图片信息
				function initPicImage(imgUpload, doc_id, title){
					var img = $('<img class="product_img" doc_id="' + doc_id + '" src="' + ctx + '/file/preview.do?doc_id=' + doc_id + '">');	// 需要展示的图片
					$(imgUpload).closest("p").find(".preview").append(img);
					$(imgUpload).hide().next("div").hide();
					$(imgUpload).parent().siblings('em').show().click(function(){
						$(this).hide();
						$(imgUpload).closest("p").find(".preview").empty();
						$(imgUpload).parent().siblings('p').find(".pic_desc").val("");
						$(imgUpload).parent().siblings('p').find("label").text("添加描述");
						$(imgUpload).show().next("div").show();
					});
					$(imgUpload).parent().siblings('p').find(".pic_desc").val(title);
					$(imgUpload).parent().siblings('p').find("label").text(title);
				}
			});
		</script>
	</c:if>
</div>
<%@ include file="/common/layout/footer.jsp"%>