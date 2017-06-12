<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setAttribute("title", "找好货");
	request.setAttribute("menu_name", "find");	
%>
<%@ include file="/WEB-INF/jsp/mobile/common/common.jsp"%>
<body>
	<header>
		<div class="head">
			<!--<a href="${ctx}/mobile/goods/index?token=${token}" class="return"><em></em></a>-->
			<form action="${ctx}/mobile/goods/find?token=${token}">
				<label class="search cen-search"><input id="search_input" type="search" name="keyword" placeholder="请输入关键字" value="${keyword}"></label>
			</form>
		</div>
	</header><!-- /header -->
	<!-- /搜索记录 -->
	<div class="search-hstory">
		<!-- <ul>
			<li><a href="goods-list.html">玩具</a></li>
		</ul>
		<a href="#" class="btn-clear">清除搜索历史</a> -->
	</div>
	<div class="area top-40">
		<ul>
			<li class="sel search_condition" type="area_id" value="">全国</li>
			<c:forEach items="${areaList}" var="area" begin="0" end="3">
		    	<li class="search_condition" type="area_id" value="${area.area_code}">${area.short_name}</li>
	    	</c:forEach>
	    </ul>
	    <a href="javascript:void(0);" class="fr-icon"></a>
	</div>
	<ul class="area-wrap">
		<c:forEach items="${areaList}" var="area" begin="4">
			<li class="search_condition" type="area_id" value="${area.area_code}">${area.short_name}</li>
		</c:forEach>
		
	</ul>
	<ul class="nav-por">
		<li>推荐</li>
		<li>关注</li>
		<li>评分</li>
		<li>评论</li>
	</ul>
	<div class="index-nav top-10">
		<div class="nav-left left-23">
		    <div class="swiper-c2"><!-- swiper-container2 -->
		        <div class="" id=""><!-- swiper-wrapper -->
		        	<div class="swiper-slide slide-quanbu search_condition" type="display_area_id" value="">
		            	<em></em>
		            	<small>全部</small>
		            </div>
		            <div id="slideHtml-big">
		        	<c:forEach items="${displayAreaList}" var="displayArea" end="4">
			            <div class="swiper-slide ${displayArea.app_css} search_condition" type="display_area" value="${displayArea.display_area}">
			            	<em></em>
			            	<small>${displayArea.display_area}</small>
			            </div>
		            </c:forEach>
		            </div>
		        </div>
		        <!-- Add Pagination -->
		        <div class="swiper-pagination2"></div>
		    </div>
		</div>
		<a href="javascript:void(0);" class="fr-icon"></a>
	</div>
	<div class="nav-left slid-bottom" id="slideHtml-small">
		 <c:forEach items="${displayAreaList}" var="displayArea" begin="5">
            <div class="swiper-slide ${displayArea.app_css} search_condition" type="display_area" value="${displayArea.display_area}">
            	<em></em>
            	<small>${displayArea.display_area}</small>
            </div>
         </c:forEach>
         <div class="nav-right">
			<a href="javascript:void(0);" class="fr-title">
				排序
			</a>
			<ul class="nav-por">
				<li class="orderby sel" value="recommend">推荐</li>
				<li class="orderby" value="attention">关注</li>
				<li class="orderby" value="grade">评分</li>
				<li class="orderby" value="comment">评论</li>
			</ul>
		</div>
	</div>
	<div class="goods">
		<%@include file="/WEB-INF/jsp/mobile/goodsListTpl.jsp"%>
	</div>
	<%@ include file="/WEB-INF/jsp/mobile/common/footer.jsp"%>
</body>
  <script type="text/javascript">
	  //导航JS
	  var swiper = new Swiper('.swiper-container2', {
		pagination: '.swiper-pagination2',
		slidesPerView: 'auto',
		paginationClickable: true,
		spaceBetween: 30
	 });
	 $(function(){
		 
		//搜索记录
		/* $("#search_input").focus(function(){
			$(this).addClass('search-weight');
			$('.search-hstory').show();
			$('body').css('overflow','hidden');
		}).blur(function(){
			$(this).removeClass('search-weight');
			$('.search-hstory').hide();
			$('body').css('overflow','auto');
		}); */
	 	//地区字体颜色
	 	$('.area ul li').click(function(){
	 		$('.area ul li').removeClass('sel');
	 		$(this).addClass('sel');
	 	});
	 	//排序显示隐藏
	  	$('.fr-title').click(function(){
	  		$('.nav-right .nav-por').toggle();
	  		$(this).toggleClass('fr-title-s');
	  	});
	  	$('.nav-por li').click(function(){
	  		$('.nav-por li').removeClass('sel');
			$(this).addClass('sel');
	  		$('.nav-right .nav-por').hide();
	  		search();
	  	});
	  	//地区显示隐藏
	 	$('.area .fr-icon').click(function(){
	 		$('.area-wrap').toggle();
	 		$(this).toggleClass('fr-icon-s');
	 	});
	  	$(".area-wrap, .area").find("li").click(function(){
	  		$('.area-wrap').hide();
	  		$(".area-wrap, .area").find("li").removeClass("sel");
	  		$(this).addClass("sel");
	  		search();
	  	});
	  	//分类显示隐藏
	  	$('.index-nav .fr-icon').click(function(){
	 		$('.slid-bottom').toggle();
	 		$(this).toggleClass('fr-icon-s');
	 	});
	 	//导航切换
	  	$('.swiper-slide').click(function(){
	  		$('.swiper-slide').children('em').removeClass('sel');
			$(this).children('em').addClass('sel');
			$('.swiper-slide').removeClass('sel');
			$(this).addClass('sel');
			search();	//搜索
	  	});
	 	
	  	
	 	$('#slideHtml-small').on('click', '.swiper-slide', function() {
	 		/* var zHtml = $('#slideHtml-big div:eq(1)').prop("outerHTML");//获取大类Html
	 		$('#slideHtml-big div:eq(1)').replaceWith($(this).prop("outerHTML"));
	 		$(this).replaceWith(zHtml); //
	 		$('#slideHtml-small').hide(); */
	 		
	 		var removedNode = $('#slideHtml-big .swiper-slide').eq(4);
	 		var thisNode = $(this);
	 		$('#slideHtml-small').prepend(removedNode);
	 		$('#slideHtml-big').prepend(thisNode);
	 		$('#slideHtml-small').hide();
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
			url: '${ctx}/mobile/goods/queryGoodsListPage?token=${token}&keyword=${keyword}',
			templateId: 'goodsListTpl',
			isLazy: false
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
				//params.keyword = $("#search_input").val();
				return params;
			}
		}
	 });
  </script>
</html>