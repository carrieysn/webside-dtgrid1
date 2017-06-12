<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("title", "买手-首页");%>
<%@ include file="/common/layout/header.jsp"%>
<%@ include file="/common/layout/menu.jsp"%>
	<!-- main container -->
	<%@ include file="/WEB-INF/jsp/searchbar.jsp" %>
	<div class="main-container">
		<div class="buyer-center">
			<div class="buyer-fl">
				<div class="wap-left">
					<c:if test="${empty keyword}">
						<div class="fenlei-wrap">
							<div class="area-bor-wrap">
								<p class="title">地区：</p>
								<ul class="area-ul">
									<c:forEach items="${areaList}" var="area">
										<li><a class="search_condition" type="area_id" text="${area.short_name}" value="${area.area_code}" href="javascript:void(0);">${area.short_name}<small>0</small></a></li>
									</c:forEach>
								</ul>
								<a class="icon-gd"></a>
							</div>
							<div class="area-bor-wrap">
								<p class="title">类目：</p>
									<ul class="pavilion-ul">
										<c:forEach items="${appEnumList}" var="appEnum">
											<c:forEach items="${appEnum.app_display_area_list}" var="displayArea">
												<li class="search_condition" type="display_area" text="${displayArea.display_area}" value="${displayArea.display_area}">
													<!-- <img src="${ctx}/static/main/image/${displayArea.icon_path}"/> -->
													<a href="javascript:void(0);">${displayArea.display_area}</a>
												</li>
											</c:forEach>
										</c:forEach>
									</ul>
								<a class="icon-gd"></a>
							</div>
							<div class="area-bor-wrap">
								<p class="title">模式：</p>
								<ul class="pavilion-ul">
									<c:forEach items="${categoryList}" var="category">
										<li class="search_condition" type="category" text="${category.key eq '试样' ? '返利': category.key}" value="${category.key}">
											<!--<img src="${ctx}/static/main/image/${category.icon_path}"/> -->
											<a href="javascript:void(0);">${category.key eq '试样' ? '返利': category.key}</a>
										</li>
									</c:forEach>
								</ul>
								<!-- <a class="icon-gd"></a> -->
							</div>
						</div>
						<p class="title">产品：<span class="search_span"></span>
							<span class="ti-right">
								<a class="orderby" value="comment" href="javascript:void(0);">评论</a>
								<a class="orderby" value="grade" href="javascript:void(0);">评分</a>
								<a class="orderby" value="attention" href="javascript:void(0);">关注</a>
								<a class="orderby" value="recommend" href="javascript:void(0);">推荐</a>
								排序：
							</span>
						</p>
					</c:if>
					<ul class="goods-ul page-list-table" id="goods_list_ul" template="goodsListTpl" url="${ctx}/product/queryProductPage?keyword=${keyword}" pageSize="50" visiblePages="10"></ul>
					<script id="goodsListTpl" type="text/html">
						{{each list as goods i}}
							<li>
								<div class="god-fl"><a href="javascript:void(0)"><img src="{{goods.pic_info | imgFormat}}"></a></div>
								<div class="god-fr">
									<div class="m-weight">
										<div class="m-fl">
											<h4>{{goods.title}}</h4>
											<p>{{goods.desc1}}</p>
											<p>模式：
												{{if goods.category == '预售'}}预售(PS)
												{{else if goods.category == '批发'}}批发(BS)-我要批
												{{else if goods.category == '兑换'}}兑换(XS)-名品汇
												{{else if goods.category == '试样'}}返利(FS)-领了么
												{{else if goods.category == '奖品'}}奖赠品(GS)
												{{else if goods.category == '团购'}}团购(TS)-伙拼团
												{{/if}}
											</p>
											<p>标签：{{goods.label | labelFormat}}</p>
										</div>
										<a class="m-fr" href="${ctx}/product/detail?goods_id={{goods.goods_id}}"></a>
									</div>
									<p class="god-icon">
										<span class="icon-fl">
											<a href="javascript:void(0);">推荐<small>{{goods.recommend_num}}</small></a>
											<a href="javascript:void(0);">关注<small>{{goods.attention_num}}</small></a>
											<a href="javascript:void(0);">评分<small>{{goods.grade_avg ? goods.grade_avg : 0}}</small></a>
											<a href="javascript:void(0);">评论<small>{{goods.comment_num}}</small></a>
										</span>
										<span class="icon-fr">
											{{if goods.user_name}}
												<small>发布者：{{goods.user_name}}</small>|
												<small>{{goods.created_date}}</small>
											{{else}}
												<small>发布时间：{{goods.created_date}}</small>
											{{/if}}
										</span>
									</p>
								</div>
							</li>
						{{/each}}
					</script>
				</div>
				<script type="text/javascript">
				
					
				
					$(function(){
						$('.icon-gd').click(function(){
							
							$(this).toggleClass('icon-sq');
							$(this).parent('.area-bor-wrap').toggleClass('area-height');
							
						})
						//格式代标签
						template.helper('labelFormat', function (label) {
							if(label){
								var label = label.split(',');
								var labelHtml = "";
								$.each(label, function(index, value){
									labelHtml += label[index] + "&nbsp;";
								});
								return labelHtml;
							}
						});
						
						//格式化图片信息
						template.helper('imgFormat', function (pic_info) {
							if(pic_info){
								var pic_info = $.parseJSON(pic_info);
								if($.isArray(pic_info) && pic_info[0]){
									return '${ctx}/file/preview.do?doc_id=' + pic_info[0].path_id;
								}
							}
							return '';
						});
						
						//加载各区域的产品数量
						loadAreaCount();
						function loadAreaCount(){
							M.post("${ctx}/product/queryCountByArea", {}, function(result){
								var data = result.data;
								if(result.data && result.data.length > 0){
									$.each(data,function(index, area){
										var area_id = area.area_id;
										var count = area.cunt;
										if(area_id){
											$(".area-ul li a[value='" + area_id + "']").find("small:eq(0)").text(count || "0");
										}
									});
								}
							},$.noop);
						}
						
						initSearchEvent();
						//初始化搜索查询事件
						function initSearchEvent(){
							$(".search_condition").bind("click.search", function(){
								var type = $(this).attr("type");
								var text = $(this).attr("text");
								var value = $(this).attr("value");
								$('.search_span a[type="' + type + '"]').remove();
								$(".search_condition[type='" + type + "']").removeClass("sel");
								var html = '<a href="javascript:void(0);" type="' + type + '" value="' + value + '">' + text + '<em>×</em></a>';
								//条件选中
								$(".search_condition[type='" + type + "'][value='" + value + "']").addClass("sel");
								$(html).appendTo(".search_span").bind("click.search", function(){
									var _type = $(this).attr("type");
									$(".search_condition[type='" + _type + "']").removeClass("sel");
									$(this).remove();
									search();
								});
								search();
							});
							
							//排序
							$(".orderby").click(function(){
								var orderby = $(this).attr("value");
								$(".orderby").removeClass("sel");
								$(this).addClass("sel");
								search();
							});
							
							//搜索
							function search(){
								var condition = {keyword: "${keyword}"};
								$(".search_span a[type]").each(function(){
									var type = $(this).attr("type");
									var value = $(this).attr("value");
									condition[type] = value;
								});
								if($(".orderby").filter(".sel").length == 1){
									condition.orderby = $(".orderby").filter(".sel").first().attr("value");
								}
								$("#goods_list_ul").attr("params", JSON.stringify(condition));
			   					M.paginator("#goods_list_ul");
							}
						}
					});
				</script>
			</div>
			<%@ include file="/WEB-INF/jsp/userinfo.jsp"%>
		</div>
	</div>
<%@ include file="/common/layout/footer.jsp"%>
