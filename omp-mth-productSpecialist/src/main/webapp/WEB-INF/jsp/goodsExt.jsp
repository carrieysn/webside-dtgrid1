<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<form id="releaseForm">
		<table class="release-cen">
			<tr>
				<td align="right" width="270" valign="top">类目：</td>
				<td colspan="3">
					<input type="hidden" name="display_area" id="display_area" value="${goods.display_area}">
					<c:forEach items="${displayAreaList}" var="displayArea">
						<label><input type="checkbox" name="display_area_ck" value="${displayArea.display_area}" datatype="*">${displayArea.display_area}</label>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td align="right" width="250">类型：</td>
				<td>
					<select name="category" style="width:150px; border:1px solid #DDD;float:left; height:30px;color:#666;font-size:14px;" datatype="*">
						<option value="">---请选择---</option>
						<c:forEach items="${categoryList}" var="category">
							<option value="${category.category}" <c:if test="${category.category eq goods.category}">selected="selected"</c:if>>${category.category}</option>
						</c:forEach>
					</select>
				</td>
				<td align="right" width="250">规格：</td>
				<td width="410"><input type="text" name="specification" value="${goods.specification}" datatype="*"/></td>
			</tr>
			<tr>
				<td align="right" width="270">市场价：</td>
				<td><input type="text" name="market_price" value="${goods.market_price}" datatype="relativePrice" errormsg="市场价不能小于优惠价！"/></td>
				<td align="right" width="270">优惠价：</td>
				<td>
					<input type="text" name="discount_price" value="${goods.discount_price}" datatype="relativePrice" errormsg="优惠价不能大于市场价！"/>
					<input type="hidden" id="ladder_price" name="ladder_price" value="{}">
				</td>
			</tr>
			<%-- <tr>
				<td align="right" width="270">阶梯价：
					<input type="hidden" id="ladder_price" name="ladder_price" value="${goods.ladder_price}">
				</td>
				<td colspan="3">
					<div class="ladder-div">
						<input type="text" ladder_type="start" style="width: 60px;background-color: #efefef;" readonly="readonly" value="0"/>&nbsp;&nbsp;-&nbsp;&nbsp;
						<input type="text" ladder_type="end" style="width: 60px;" datatype="n" ignore="ignore"/>
						<input type="text" ladder_type="price" style="width: 80px;" placeholder="价格" datatype="money" ignore="ignore"/>
					</div>
					<div class="ladder-div" style="margin-top: 5px;">
						<input type="text" ladder_type="start" style="width: 60px;background-color: #efefef;" readonly="readonly"/>&nbsp;&nbsp;-&nbsp;&nbsp;
						<input type="text" ladder_type="end" style="width: 60px;background-color: #efefef;" readonly="readonly"/>
						<input type="text" ladder_type="price" style="width: 80px;" placeholder="价格" datatype="money" ignore="ignore"/>
					</div>
					<div class="ladder-div" style="margin-top: 5px;">
						<input type="text" ladder_type="start" style="width: 60px;" datatype="n" ignore="ignore"/>&nbsp;&nbsp;-&nbsp;&nbsp;
						<input type="text" ladder_type="end" style="width: 60px;background-color: #efefef;" readonly="readonly"/>
						<input type="text" ladder_type="price" style="width: 80px;" placeholder="价格" datatype="money" ignore="ignore"/>
					</div>
				</td>
			</tr> --%>
			<tr>
				<td align="right" width="270">库存量：</td>
				<td>
					<input type="text" name="sale_qty" value="${goods.sale_qty}" datatype="orderNum" errormsg="库存量不能小于起订量！" style="width: 145px;"/>
				</td>
				<td align="right" width="270">起订量：</td>
				<td>
					<input type="text" name="min_buy_qty" value="${goods.min_buy_qty}" datatype="orderNum" errormsg="起订量不能大于库存量！" style="width: 60px;"/>
					<input type="text" name="goods_unit" value="${goods.goods_unit}" placeholder="单位" style="width: 60px;"/>
				</td>
			</tr>
			<tr>
				<td align="right" width="270">限购：</td>
				<td>
					<input type="text" name="max_buy_qty" value="${goods.max_buy_qty}" datatype="n" ignore="ignore"/>
				</td>
				<td align="right" width="270">有效期：</td>
				<td>
					<input type="text" name="valid_thru" value="${goods.valid_thru}" datatype="*" data-beatpicker="true" data-beatpicker-id="validDatePicker"/>
				</td>
			</tr>

			<tr>
				<td align="right" width="270" valign="top">配送范围：
					<input type="hidden" id="delivery_area" name="delivery_area" value="${goods.delivery_area}">
				</td>
				<td class="peisong delivery_area_div" colspan="3">
					<div class="city-div">
						<c:if test="${empty deliveryareaList or deliveryareaList.size() <0}">
							<div class="selectContainer" isCode="true">
								<select class="bind-province" allow-null="true"></select>
								<select class="bind-city" allow-null="true"></select>
								<select class="bind-district" allow-null="true"></select>
								<input type="hidden" class="_path_" value="${delivery.path}">
								<a class="add">+</a>
							</div>
						</c:if>
						<c:forEach items="${deliveryareaList}" var="delivery" varStatus="status">
							<div class="selectContainer" isCode="true">
								<select class="bind-province" allow-null="true"></select>
								<select class="bind-city" allow-null="true"></select>
								<select class="bind-district" allow-null="true"></select>
								<input type="hidden" class="_path_" value="${delivery.path}">
								<c:choose>
									<c:when test="${status.index eq 0}">
										<a class="add">+</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0);" class="add" onclick="$(this).closest('.selectContainer').remove();">-</a>
									</c:otherwise>
								</c:choose>
							</div>
						</c:forEach>
					</div>
				</td>
			</tr>
			<tr id="payment_way_div">
				<td align="right" width="270">支付方式：</td>
				<td colspan="3">
					<label><input type="radio" name="payment_way" value="online" <c:if test="${goods.payment_way eq 'online'}">checked="checked"</c:if> datatype="*">在线支付</label>
					<label><input type="radio" name="payment_way" value="offline" <c:if test="${goods.payment_way eq 'offline'}">checked="checked"</c:if> datatype="*">货到付款</label>
					<!-- 
					<select name="payment_way" datatype="*">
						<option value="">---请选择---</option>
						<option value="online" <c:if test="${goods.payment_way eq 'online'}">selected="selected"</c:if>>在线支付</option>
						<option value="offline" <c:if test="${goods.payment_way eq 'offline'}">selected="selected"</c:if>>货到付款</option>
					</select>
					 -->
				</td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
		$(function(){
			initSelector();
			//初始化地区选择
			function initSelector(){
				M.area(".selectContainer");
				$(".delivery_area_div .selectContainer .add").first().bind("click.select", function(){
					//添加一个配送区域
					var selectedEl = $(this).closest(".selectContainer").clone(false);
					selectedEl.find("option").remove();
					selectedEl.find("._path_").val("");
					selectedEl.appendTo(".delivery_area_div");
					M.area(selectedEl);
					selectedEl.find(".add").text("-").unbind("click.select").bind("click.select", function(){
						$(this).closest(".selectContainer").remove();
					});
				});
			}
			
			//格式化已选择的地区
			function formatDeliveryArea(){
				var areaArray = new Array();
				$(".delivery_area_div .selectContainer").each(function(){
					var _this = this;
					var district = $(this).find(".bind-district").val();
					var city = $(this).find(".bind-city").val();
					var province = $(this).find(".bind-province").val();
					var areaCode = district || city || province;
					if(areaCode){
						areaArray.push(areaCode);
					}
				});
				$("#delivery_area").val(areaArray.join(",") || "100000");  //默认为全国
				return false;
			}
			//发布验证
			var releaseForm = $('#releaseForm').Validform({
				tiptype : 3,
				datatype:{
					"relativePrice": function(gets,obj,curform,regxp){
						if(regxp.money.test(gets)){
							var market_price_el = $("#releaseForm input[name='market_price']");
							var market_price = market_price_el.val();
							var discount_price_el =  $("#releaseForm input[name='discount_price']")
							var discount_price = discount_price_el.val();
							if(market_price && discount_price){
								if(parseFloat(gets) <= 0){
									return '请输入正确的金额！';
								}
								if(parseFloat(market_price) < parseFloat(discount_price)){
									return false;
								}else{
									market_price_el.removeClass("Validform_error");
									market_price_el.nextAll(".Validform_checktip").removeClass("Validform_wrong");
									discount_price_el.removeClass("Validform_error");
									discount_price_el.nextAll(".Validform_checktip").removeClass("Validform_wrong");
								}
							}
							return;
						}
						return "请输入正确的金额！";
					},
					"orderNum": function(gets,obj,curform,regxp){
						if(regxp.n.test(gets)){
							var min_buy_qty_el = $("#releaseForm input[name='min_buy_qty']");
							var min_buy_qty = min_buy_qty_el.val();
							var stock_qty_el = $("#releaseForm input[name='sale_qty']")
							var stock_qty = stock_qty_el.val();
							if(min_buy_qty && stock_qty){
								if(parseInt(min_buy_qty) > parseInt(stock_qty)){
									return false; 
								}else{
									min_buy_qty_el.removeClass("Validform_error");
									min_buy_qty_el.nextAll(".Validform_checktip").removeClass("Validform_wrong");
									stock_qty_el.removeClass("Validform_error");
									stock_qty_el.nextAll(".Validform_checktip").removeClass("Validform_wrong");
								}
							}
							return true;
						}
						return "请输入正确的数量！";
					}
				}
			});
			
			validDatePicker.on('select', function(){
				releaseForm.check(false, "[name='valid_thru']");
			});
			//发布
			$("#submit_btn").on('click', function () {
				if(validGoodsForm()){
					layer.open({
						title:  ['发布', 'font-size:15px;font-weight: bold;'],
						type: 1,
						icon: 1,
					    btn: ['确定', '取消'],
						shadeClose: true,
						shade: 0.8,
						zIndex: 98,
						shadeClose: false,
						area: ['630px', '520px'],
						content: $("#release-div"),
						yes: function(index){
							if(releaseForm.check(false)){
								publish();
							}
						},
						cancel: function(index){
					    	layer.close(index);
					    },
					    end: function(index){
					    }
					});
				}
			});
			
			var vf = $('#productForm').Validform({
				tiptype : 3
			});
			
			//验证并初始化一些表单信息
			function validGoodsForm(){
				if(vf.check(false)){
					//验证成功
					//将上传的图片数据进行格式化
					var productImgs = new Array();
					$(".photo .product_img").each(function(){
						var img = {};
						img.path_id = $(this).attr("doc_id");
						img.title = $(this).parent().parent().parent().find(".pic_desc").val();
						productImgs.push(img);
					});
					if(productImgs.length < 1){
						M.alert('请至少上传一张产品图片');
						return false;
					} else{
						$("#pic_info").val(JSON.stringify(productImgs));
					}
					return true;
				}
				return false;
			}
			
			//初始化类目选择框
			initAreaCheck();
			function initAreaCheck(){
				var display_area = "${goods.display_area}"
				if(display_area){
					var areas = display_area.split(",");
					for(var i = 0;i < areas.length;i++){
						var value = areas[i];
						$("input[name='display_area_ck'][value='" + value + "']").attr("checked", true);
					}
				}
			}
			//格式化类目
			function formatDisplayArea(){
				var array = new Array();
				$("input[name='display_area_ck']:checked").each(function(){
					array.push($(this).val());
				});
				$("#display_area").val(array.join(","));
			}
			
			/*initLadderPrice();
			//初始化阶梯价
			function initLadderPrice(){
				var ladder_price = '${goods.ladder_price}';
				if(ladder_price){
					var ladderArray = JSON.parse(ladder_price);
					for(var i = 0;i < ladderArray.length;i++){
						var ladder = ladderArray[i];
						var ladder_div = $(".ladder-div").eq(i);
						for(var key in ladder){
							$(ladder_div).find("input[ladder_type='" + key + "']").val(ladder[key]);
						}
					}
				}
			}
			//格式化阶梯价
			function formatLadderPrice(){
				var array = new Array();
				$(".ladder-div").each(function(){
					var ladder = {};
					$(this).find("input").each(function(){
						var ladder_type = $(this).attr("ladder_type");
						ladder[ladder_type] = $(this).val();
					});
					array.push(ladder);
				});
				array[1].start=array[0].end;
				array[1].end=array[2].start;
				for(var i = 0;i < array.length;i++){
					if((array[i].start && array[i].start != '0') || array[i].end){
						if(!array[i].price){
							M.alert("请填写对应的阶梯价！");
							return false;
						}
					}
				}
				$("#ladder_price").val(JSON.stringify(array));
				return true;
			}*/
			
			//发布产品信息
			function publish(){
				/* var flag = formatLadderPrice();
				if(!flag){
					return false;
				} */
				layer.confirm("是否确认发布产品？", function(index){
					formatDeliveryArea();
					formatDisplayArea();
					var loadIdContract = layer.load();
					var goods = $.extend({}, M.formatElement("#productForm"), M.formatElement("#releaseForm"));
					M.post("${ctx}/product/save", goods, function(result){
					 //发布成功
					 layer.closeAll();
					 M.alert('发布成功！', function(){
						 var goods_id = result.data; //产品编号
						 window.location.href = "${ctx}/product/detail?goods_id=" + goods_id; 
					 });
					}, function(result){
					 layer.close(loadIdContract);
					 M.alert('发布失败!');
					});
				});
			}
		});
	</script>
