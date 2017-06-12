<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setAttribute("title", "买手-首页");%>
<%@ include file="/common/layout/header.jsp"%>
<%@ include file="/common/layout/menu.jsp"%>
	<%@ include file="/WEB-INF/jsp/searchbar.jsp" %>
	<div class="main-container">
		<div class="buyer-center">
			<div class="buyer-fl">
				<div class="wap-left">
					<ul class="goods-ul page-list-table" id="goods_list_ul" template="goodsListTpl" url="${ctx}/product/queryUserProductPage?statistics_type=${statistics_type}" pageSize="50" visiblePages="10"></ul>
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
												{{else if goods.category == '试样'}}返利(FS)-超级返
												{{else if goods.category == '奖品'}}奖赠品(GS)
												{{else if goods.category == '团购'}}团购(TS)
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
											<small>发布者：{{goods.user_name}}</small>|
											<small>{{goods.created_date}}</small>
										</span>
									</p>
								</div>
							</li>
						{{/each}}
					</script>
				</div>
				<script type="text/javascript">
					$(function(){
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
					});
				</script>
			</div>
			<%@ include file="/WEB-INF/jsp/userinfo.jsp"%>
		</div>
	</div>
<%@ include file="/common/layout/footer.jsp"%>