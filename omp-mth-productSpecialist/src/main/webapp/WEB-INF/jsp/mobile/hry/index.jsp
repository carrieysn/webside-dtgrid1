<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setAttribute("title", "买手");%>
<%@ include file="/WEB-INF/jsp/mobile/common/common.jsp"%>
<body>
	<div class="head-fixed">
		<a href="${ctx}/mobile/user/center?token=${token}" class="cart"></a>
	</div>
	<!--banner-->
	<div class="device">
		<a class="arrow-left" href="javascript:void(0);"></a> <a class="arrow-right" href="javascript:void(0);"></a>
		<div class="swiper-container">
			<div class="swiper-wrapper">
				<c:forEach begin="1" end="2" var="status">
					<div class="swiper-slide">
						<img src="${ctx}/static/mobile/hry/image/banner-hry${status}.jpg">
					</div>
				</c:forEach>
			</div>
		</div>
		<div class="pagination"></div>
	</div>
	<!--banner -->

	<div class="index-nav">
		<div class="nav-left">
			<div class="swiper-c2"><!--swiper-container2-->
				<div class="swiper-wrapper">
					<div class="swiper-slide slide-quanbu sel search_condition" type="category" value="">
		            	<em class="sel"></em>
		            	<small>全部</small>
		            </div>
					<c:forEach items="${categoryList}" var="category">
						<div class="swiper-slide ${category.app_css} search_condition" type="category" value="${category.category}">
							<em></em> <small>${category.category}<%-- (${category.goods_count}) --%></small>
						</div>
					</c:forEach>
					<div class="nav-right">
						<a href="javascript:void(0);" class="fr-title"> 排序 </a>
						<ul class="nav-por">
							<li class="orderby sel" value="recommend">推荐</li>
							<li class="orderby" value="attention">关注</li>
							<li class="orderby" value="grade">评分</li>
							<li class="orderby" value="comment">评论</li>
						</ul>
					</div>
				</div>
				<!-- Add Pagination -->
				<div class="swiper-pagination2"></div>
			</div>
		</div>
		
	</div>
	<div class="goods">
		<%@include file="/WEB-INF/jsp/mobile/goodsListTpl.jsp"%>
	</div>
	<div class="btn-inquire">
		<a href="${ctx}/mobile/goods/create?token=${token}" class="btn-y">有好货</a> 
		<a href="${ctx}/mobile/goods/find?token=${token}" class="btn-z">找好货</a>
	</div>
</body>
<script type="text/javascript">
	//branner图JS
	var mySwiper = new Swiper('.swiper-container', {
		pagination : '.pagination',
		loop : true,
		grabCursor : true,
		paginationClickable : true
	});
	$('.arrow-left').click(function(e) {
		e.preventDefault();
		mySwiper.swipePrev();
	})
	$('.arrow-right').click(function(e) {
		e.preventDefault()
		mySwiper.swipeNext()
	})

	//导航JS
	var swiper = new Swiper('.swiper-container2', {
		pagination : '.swiper-pagination2',
		slidesPerView : 'auto',
		paginationClickable : true,
		spaceBetween : 30
	});

	$(function() {
		//排序显示隐藏
		$('.fr-title').click(function(){
			$('.nav-right .nav-por').toggle();
			$(this).toggleClass('fr-title-s');
		});
		$('.nav-por li').click(function(e){
			$('.nav-por li').removeClass('sel');
			$(this).addClass('sel');
			$('.nav-right .nav-por').hide();
			search();
		});
		//导航切换
		$('.swiper-c2 .swiper-slide').click(function(){
			$('.swiper-c2 .swiper-slide').children('em').removeClass('sel');
			$(this).children('em').addClass('sel');
			$('.swiper-c2 .swiper-slide').removeClass('sel');
			$(this).addClass('sel');
			search();	//搜索
		});
		//推荐换背景图片
		$('.g-TabBarBox .tj').click(function(){
			$(this).toggleClass('sel-tj');
		});
		//关注换背景图片
		$('.g-TabBarBox .gz').click(function(){
			$(this).toggleClass('sel-gz');
		});
		
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
				var pic_info = JSON.parse(pic_info);
				if($.isArray(pic_info) && pic_info[0]){
					return '${ctx}/file/preview.do?token=${token}&doc_id=' + pic_info[0].path_id;
				}
			}
			return '';
		});
		
		var page = $("#goods_list_ul").mPage({
			url: '${ctx}/mobile/goods/queryGoodsListPage?token=${token}',
			templateId: 'goodsListTpl'
		});
		
		/**加载商品列表**/
		function search(){
			page.mPage('reload', getParams());			
			/*获取用户选择的参数信息*/
			function getParams(){
				var params = {};
				$(".search_condition").filter(".sel").each(function(){
					var type = $(this).attr("type");
					var value = $(this).attr("value");
					if(value){
						params[type] = value;
					}
				});
				params.orderby = $(".orderby").filter(".sel").first().attr("value");
				return params;
			}
		}
	});
</script>
</html>