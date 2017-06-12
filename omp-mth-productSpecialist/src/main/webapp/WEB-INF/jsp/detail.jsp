<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/layout/header.jsp"%>
<%@ include file="/common/layout/menu.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- main container -->
<%@ include file="/WEB-INF/jsp/searchbar.jsp" %>
<div class="main-container">
	
	<div class="buyer-center">
		<div class="buyer-fl detail-fl">
			<div class="wap-left">
				<div class="wap-edit">
					<a href="${ctx}/product/index" class="return">返回</a>
					<c:if test="${goods.owner_id eq sessionUser.user_id}">
						<a href="javascript:void(0)" onclick="deleteProduct();" class="return">删除</a>
						<a href="${ctx}/product/edit?goods_id=${goods.goods_id}">编辑</a>
					</c:if>
				</div>
				<div class="det-ti">
					<p class="dt-fl">${goods.title}</p>
					<p class="dt-fr">
						<small>${goods.user_name}</small> <small>${goods.user_tel}</small> <%-- <small>${goods.team_name}</small> --%>
						<small>${goods.created_date}</small>
					</p>
				</div>
				<div class="cen">${goods.desc1}</div>
				<div class="det-tag">
					<small>标签：</small>
					<c:forEach items="${labels}" var="label">
						<span>${label}</span>
					</c:forEach>
				</div>
				<div class="det-text icon-lxr">
					<span><small>联系人：</small>${goods.contact_person} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
				</div>
				<div class="det-text icon-dh">
					<span><small>电话：</small>${goods.contact_tel}</span>
				</div>
				<div class="det-text">
					<span><small>经销商：</small>${goods.supplier}</span>
				</div>
				<div class="det-text">
					<span><small>浏览：</small><label id="view_label">0</label></span>
					
				</div>
				<div class="det-text">
					<span><small>销量：</small><label id="sell_label">0</label></span>
				</div>
				<c:if test="${goods.category eq '试样'}">
					<div class="det-text">
						<p>链接：<a href="${goods.product_source}" target="_blank">${goods.product_source}</a></p>
					</div>
				</c:if>
				<div class="det-photo">
					<div>
						<a href="javascript:void(0);"><img src="${ctx}/file/preview.do?doc_id=${bigPicInfo.path_id}"></a>
						<p>${bigPicInfo.title}</p>
					</div>
					<c:if test="${picInfoList.size() > 0}">
						<ul>
							<c:forEach items="${picInfoList}" var="picInfo">
								<li>
									<a href="javascript:void(0);"><img src="${ctx}/file/preview.do?doc_id=${picInfo.path_id}"></a>
									<p>${picInfo.title}</p>
								</li>
							</c:forEach>
						</ul>
					</c:if>
				</div>
				<div class="det-push">
                    <div class="div-tj">
                       <%--  <ul class="select display_area_list">
                            <li>选择推荐的展馆</li>
                            <li>
                            	<c:forEach items="${displayAreaList}" var="displayArea">
	                            	<a class="recommend_btn" href="javascritp:void(0);" display_area_id="${displayArea.display_area_id}">${displayArea.display_area}</a>
                            	</c:forEach>
                            </li>
                        </ul> --%>
                        <c:choose>
                        	<c:when test="${isRecommend eq true}">
		                        <a href="javascript:void(0);" class="sel">已推荐<small>${goods.recommend_num}</small></a>
                        	</c:when>
                        	<c:otherwise>
                        		<a href="javascript:void(0);" id="recommend_a">推荐<small>${goods.recommend_num}</small></a>
                        	</c:otherwise>
                        </c:choose>
                    </div>
                    <div>
                    	<c:choose>
                    		<c:when test="${isAttentioned eq true}">
                    			<a href="javascript:void(0);" class="sel">已关注<small>${goods.attention_num}</small></a>
                    		</c:when>
                    		<c:otherwise>
		                       <a href="javascript:void(0);" id="attention_a">关注<small>${goods.attention_num}</small></a>
                    		</c:otherwise>
                    	</c:choose>
                    </div>
                    <%-- <c:if test="${goods.owner_id ne sessionUser.user_id}">
	                    <div>
	                    	<a href="javascript:void(0);" class="btn-cg">采购</a>
	                    </div>
                    </c:if> --%>
                </div>
                <c:if test="${picDetailInfoList.size() > 0}">
	                <div class="score detail-bef" name="total_grade" level="0">详情</div>
	                <div class="detail-img">
	                	<c:forEach items="${picDetailInfoList}" var="pic">
	                		<p>
			                	<img src="${ctx}/file/preview.do?doc_id=${pic.path_id}" style="cursor: pointer;">
			                	<span>${pic.title}</span>
		                	</p>
	                	</c:forEach>
	                </div>
                </c:if>
				<div class="score" name="total_grade" level="0">
					评分
					<a href="javascript:void(0);"><a href="javascript:void(0);"></a><a href="javascript:void(0);">
					</a><a href="javascript:void(0);"></a><a href="javascript:void(0);"></a>
					<small>0分</small>
				</div>
				<ul class="score-ul">
					<li name="brand_grade"><span>品牌</span></li>
					<li name="supplier_grade"><span>供应商</span></li>
					<li name="pack_grade"><span>包装</span></li>
					<li name="cost_performance_grade"><span>性价比</span></li>
					<li name="quality_grade"><span>质量</span></li>
					<li name="competitiveness_grade"><span>竞争力</span></li>
					<li name="transportation_grade"><span>运输</span></li>
					<li name="appearance_grade"><span>外观</span></li>
				</ul>
				<div class="score sc-pj">
					评论 <small>${goods.comment_num}</small>
				</div>
				<div class="assess">
					<textarea id="comment_content"></textarea>
					<p>
						<small id="number-small">0/80</small>
						<a href="javascript:void(0);"><input id="comment_publish_btn" type="submit" value="发布"></a>
					</p>
				</div>
				<ul class="assess-ul page-list-table" template="commentListTpl" url="${ctx}/product/queryGoodsCommentPage?goods_id=${goods.goods_id}" isNullMsg="false" pageSize="10" visiblePages="10"></ul>
				<script id="commentListTpl" type="text/html">
					{{each list as comment i}}
					<li>
						<!-- <div class="as-fl">
							<img src="${ctx}/common/static/image/tuxiang.jpg" width="50" height="50" />
						</div> -->
						<div class="as-fr">
							<p class="ti">
								{{comment.user_name}}<small>{{comment.created_date}}</small>
							</p>
							<p class="pl">{{comment.content}}</p>
						</div>
					</li>
					{{/each}}
				</script>
			</div>
		</div>
		<div class="buyer-fr detail-fr">
			<div class="wap-right">
				<p class="dj-ti">销售方案</p>
				<table class="buyer-goods-info">
					<tr>
						<td align="right" width="80">商品码：</td>
						<td>${goods.goods_code}</td>
					</tr>
					<tr>
						<td align="right" width="80">类目：</td>
						<td>${goods.display_area}</td>
					</tr>
					<tr>
						<td align="right" width="80">模式：</td>
						<td><c:choose><c:when test="${goods.category eq '试样'}">返利</c:when><c:otherwise>${goods.category}</c:otherwise></c:choose></td>
					</tr>
					<tr>
						<td align="right">规格：</td>
						<td>${goods.specification}</td>
					</tr>
					<tr>
						<td align="right">库存：</td>
						<td>${goods.sale_qty}</td>
					</tr>
					<tr>
						<td align="right">优惠价：</td>
						<td>${goods.discount_price}</td>
					</tr>
					<tr>
						<td align="right">市场价：</td>
						<td>${goods.market_price}</td>
					</tr>
					<tr>
						<td align="right">起订：</td>
						<td>${goods.min_buy_qty}</td>
					</tr>
					<tr>
						<td align="right">限购：</td>
						<td>${goods.max_buy_qty }</td>
					</tr>
					<tr>
						<td align="right">邮费：</td>
						<td>${goods.shipping_fee}</td>
					</tr>
					<tr>
						<td align="right" valign="top">支付方式：</td>
						<td>
							<c:choose>
								<c:when test="${goods.payment_way eq 'online'}">在线支付</c:when>
								<c:when test="${goods.payment_way eq 'offline'}">货到付款</c:when>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td align="right" valign="top">配送范围：</td>
						<td>
							${empty goods.delivery_area_path ? '全国' : goods.delivery_area_path}
						</td>
					</tr>
					<tr>
						<td align="right" valign="top">特殊说明：</td>
						<td>
							${empty goods.remark ? '无' : goods.remark}
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<c:if test="${goods.owner_id eq sessionUser.user_id}">
		<script type="text/javascript">
			//删除产品信息
			function deleteProduct(){
				layer.confirm("是否确定删除产品？", function(index){
					layer.close(index);
					var loading = layer.load();
					M.post("${ctx}/product/delete",{goods_id: "${goods.goods_id}"}, function(result){
						layer.close(loading);
						M.alert(result.msg || "删除成功！", function(){
							window.location.href = "${ctx}/product/index";
						});
					}, function(result){
						layer.close(loading);
						M.alert(result.msg || "删除失败！");
					});
				});
			}
		</script>
	</c:if>
	<script type="text/javascript">
		$(function(){
			initGradeLevel();
			//初始化评分等级
			function initGradeLevel(){
				var gradeHtml = "";
				for(var i = 0;i < 5;i++){
					gradeHtml += '<a href="javascript:void(0);"></a>';
				}
				$(gradeHtml).appendTo(".score-ul li").hover(function(){
					var index = $(this).index();
					$(this).closest("li").find("a:lt(" + index + ")").addClass('sel');
				}, function(){
					//还原之前的评分
					var li = $(this).closest("li");
					var index = li.attr("level") || 0;
					li.find("a").removeClass("sel");
					li.find("a:lt(" + index + ")").addClass('sel');
				}).click(function(){
					//选择评分
					var index = $(this).index();
					$(this).closest("li").attr("level", index).find("a:lt(" + index + ")").addClass('sel');
					calcGrade();
				});
				
				//计算综合评分
				function calcGrade(){
					var count = 0, countLevel = 0;
					$(".score-ul li").each(function(){
						var level = $(this).attr("level");
						if(level && $.isNumeric(level)){
							countLevel += parseInt(level);
						}
						count++;
					});
					//计算评分
					var avgLevel = (countLevel / count).toFixed(1);
					var index = Math.ceil(avgLevel);
					$(".score[name='total_grade']").attr("level", avgLevel);
					$(".score a").removeClass("sel");
					$(".score a:lt(" + index + ")").addClass("sel");
					$(".score small").first().text(avgLevel + "分");
				}
			}
			
			//显示已推荐的分类信息，渲染对应元素，并绑定推荐事件
			/*M.post("${ctx}/product/queryUserRecommendListByGoodsId",{goods_id: "${goods.goods_id}"}, function(result){
				var data = result.data;
				if(data && data.length > 0){
					$.each(data, function(index, recommend){
						var display_area_id = recommend.display_area_id;
						$(".display_area_list .recommend_btn[display_area_id='" + display_area_id + "']").removeAttr("display_area_id").addClass("sel");
					});
				}
				$(".display_area_list .recommend_btn[display_area_id]").bind("click", function(){
					var _this = this;
					var display_area_id = $(this).attr("display_area_id");
					if(display_area_id){
						M.post("${ctx}/product/recommendGoods",{goods_id: "${param.goods_id}", display_area_id: display_area_id}, function(result){
							M.alert('推荐成功！');
							//推荐数+1
							var oldNum = parseInt($("#recommend_a small").text() || 0);
							$("#recommend_a small").text(++oldNum);
							$(_this).unbind("click").addClass("sel");
						}, function(result){
							M.alert(result.msg || '推荐失败');
						});
					}
				});
			});*/
			//推荐
			$("#recommend_a").bind("click", function(){
				var _this = this;
				M.post("${ctx}/product/recommendGoods", {goods_id: "${goods.goods_id}", display_area_id: "${display_area_id}"}, function(result){
					M.alert('推荐成功！');
					var oldNum = parseInt($(_this).find("small").text() || 0);
					$(_this).find("small").text(++oldNum);
					$(_this).unbind("click").addClass("sel").text("已推荐");
				}, function(result){
					M.alert(result.msg || '推荐失败！');
				});
			});
			
			//关注
			$("#attention_a").bind("click", function(){
				var _this = this;
				M.post("${ctx}/product/attentionGoods", {goods_id: "${goods.goods_id}"}, function(result){
					//关注数+1
					var oldNum = parseInt($("#attention_a small").text() || 0);
					$("#attention_a small").text(++oldNum);
					$("#attention_a").unbind("click").addClass("sel").text("已关注");
				}, function(result){
					M.alert(result.msg || '关注失败！');
				});
			});
			
			//发布
			$("#comment_publish_btn").click(function(){
				var comment_content = $("#comment_content").val();
				var total_grade = parseFloat($(".score[name='total_grade']").attr("level") || 0);
				if(comment_content || total_grade > 0){
					if(comment_content){
						if(!(comment_content.length > 0 && comment_content.length < 81)){
							M.alert("请输入0-80字评价内容！");
							return false;
						}
					}
					var form = {goods_id: "${param.goods_id}", comment_content: comment_content, total_grade: total_grade};
					if(total_grade > 0){	//拼接评分数据
						$(".score-ul li").each(function(){
							var name = $(this).attr("name");
							var level = parseInt($(this).attr("level") || 0);
							form[name] = level;
						});
					}
					//提交评论评分
					layer.confirm("是否确认发布评论？", function(index){
						layer.close(index);
						var loadIdContract = layer.load();
						M.post("${ctx}/product/commentGoods",form, function(result){
							layer.close(loadIdContract);
							M.alert(result.msg || '发布评论成功！', function(){
								window.location.reload();
							});
						}, function(){
							layer.close(loadIdContract);
						});
					});
				} else{
					M.alert("请先输入评分内容！")
				}
			});
			
			//加载商品销量信息
			loadGoodsSellInfo();
			function loadGoodsSellInfo(){
				M.post("${ctx}/product/queryGoodsSellInfo",{'goods_id': '${goods.goods_id}'}, function(result){
					if(result && result.data){
						var data = result.data;
						$("#view_label").text(data.view || "0");
						$("#sell_label").text(data.sell || "0");
					}
				});
			}
			/* layer.photos({
				photos: '.detail-img',
				full:true
			 }); */
		});
	</script>
</div>
<%@ include file="/common/layout/footer.jsp"%>