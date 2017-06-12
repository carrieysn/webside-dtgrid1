<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setAttribute("title", "买手");
	request.setAttribute("menu_name", "index");
%>
<%@ include file="/WEB-INF/jsp/mobile/common/common.jsp"%>
<body>
	<%-- <div class="head-fixed">
		<a href="${ctx}/mobile/user/center?token=${token}" class="cart"></a>
	</div> --%>
	<!--banner-->
	<div class="device">
		<a class="arrow-left" href="javascript:void(0);"></a> <a class="arrow-right" href="javascript:void(0);"></a>
		<div class="swiper-container">
			<div class="swiper-wrapper">
				<div class="swiper-slide">
					<img src="${ctx}/static/mobile/image/banner01.png?v=0.12">
				</div>
				<div class="swiper-slide">
					<img src="${ctx}/static/mobile/image/banner02.png?v=0.11">
				</div>
				<div class="swiper-slide">
					<img src="${ctx}/static/mobile/image/banner03.png?v=0.11">
				</div>
			</div>
		</div>
		<div class="pagination"></div>
	</div>
	<!--banner -->
	<div class="icon-nav-wrap">
		<ul class="icon-nav">
			<li>
				<div class="ti">会员</div>
				<div class="number" id="consumer_info">0</div>
			</li>
			<li>
				<div class="ti">门店</div>
				<div class="number" id="stores_info">0</div>
			</li>
			<li>
				<div class="ti">产品</div>
				<div class="number" id="goods_info">0</div>
			</li>
			<li>
				<div class="ti">订单</div>
				<div class="number" id="order_info">0</div>
			</li>
		</ul>
	</div>
	<ul class="title-list">
		<li class="sel"><a>最新</a></li>
		<li><a>热卖</a></li>
		<li><a>推荐</a></li>
		<li><a>招商</a></li>
	</ul>
	<div class="goods-list">
		<div class="goods">
			<ul class="god-warp noneTop-li" id="newest_goods_list_ul"></ul>
		</div>
		<!-- <div class="icon-jaz">加载中</div> -->
		<div class="goods" style="display:none;">
			<ul class="god-warp noneTop-li" id="sales_goods_list_ul"></ul>
		</div>
		<div class="goods" style="display:none;">
			<ul class="god-warp noneTop-li" id="recommend_goods_list_ul"></ul>
		</div>
		<div class="goods investment" style="display:none;">
			<ul class="god-warp" id="investment_list_ul"></ul>
		</div>
		<script id="investmentListTpl" type="text/html">
			{{each list as investment i}}
				<li subject_id="{{investment.subject_id}}">
					<div class="zs-name">{{investment.title}}</div>
					<p class="zs-nr">{{investment.content}}</p>
					<p class="zs-date">
						<span>发布者：{{investment.team_name}} - {{investment.user_name}}</span>     
						<span>有效期：{{investment.expiration_date}}</span>
					</p>
				</li>
			{{/each}}
		</script>
		<script id="salesGoodsListTpl" type="text/html">
			{{each list as goods i}}
					<li>
						<div class="god">
							<a href="${ctx}/mobile/goods/detail?token=${token}&goods_id={{goods.goods_id}}" class="god-img"><img src="{{goods.pic_info | salesImgFormat}}"></a>
							<a href="${ctx}/mobile/goods/detail?token=${token}&goods_id={{goods.goods_id}}">
								<div class="god-right">
									<p class="g-name">{{goods.title}}</p>
									<!--<p>{{goods.desc1}}</p>
									<!--<p>标签：{{goods.label | labelFormat}}</p>-->
									<p class="goods-number">
										<span>起订：{{goods.min_buy_qty}}</span>
										<span>限购：{{goods.max_buy_qty}}</span>
										<span>库存：{{goods.sale_qty}}</span>
									</p>
									<!-- <p>有效期：{{goods.valid_thru}}</p>
									<p class="g-announcer">发布者：{{goods.user_name}} {{goods.team_name}}</p>
									<p>发布时间：{{goods.created_date}}</p> -->
									<%-- <p class="goods-number">
										<span>【浏览：{{goods.view}}</span>
										<span>销量：{{goods.sell}}】</span>
									</p> --%>
									<p class="g-announcer"><span>¥<i>{{goods.discount_price}}</i></span><em>¥{{goods.market_price}}</em>规格：{{goods.specification}}</p>
								</div>
							</a>
						</div>
					</li>
			{{/each}}
		</script>
	</div>
	<div>
		<%@include file="/WEB-INF/jsp/mobile/goodsListTpl.jsp"%>
	</div>
	<%@ include file="/WEB-INF/jsp/mobile/common/footer.jsp"%>
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
		e.preventDefault();
		mySwiper.swipeNext();
	})

	$(function() {
		//首页固定顶部标题
		$(window).scroll( function (){ 
		    var h_num=$("body").scrollTop();
		    var proHeight = $('.goods-list').offset().top;   
		    var navHeight = $('.title-list').height();
		         if (h_num > (proHeight-navHeight)){
		         	$('.title-list').addClass('fixer-title');
		        } else {   
		            $('.title-list').removeClass('fixer-title');
		        }    
		           
		    }); 
		//推荐特卖切换
		$('.title-list li').click(function(){
			$('.title-list li').removeClass('sel');
			$(this).addClass('sel');
			$('.goods-list .goods').hide();
			$('.goods-list .goods').eq($(this).index('.title-list li')).show();
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
		
		loadNewestGoodsList();
		//加载最新商品列表
		function loadNewestGoodsList(){
			var newestPage = $("#newest_goods_list_ul").mPage({
				url: '${ctx}/mobile/goods/loadNewestGoodsList?token=${token}',
				templateId: 'salesGoodsListTpl',
				isLazy: false
			});
		}
		
		loadSalesGoodsList();
		//加载热销商品列表
		function loadSalesGoodsList(){
			var salesPage = $("#sales_goods_list_ul").mPage({
				url: '${ctx}/mobile/goods/loadSalesGoodsList?token=${token}',
				templateId: 'salesGoodsListTpl',
				isLazy: false
			});
		}
		
		loadRecommendGoodsList();
		//加载推荐商品信息
		function loadRecommendGoodsList(){
			$.post('${ctx}/mobile/goods/loadRecommendGoodsList?token=${token}', {}, function(result){
				if(result.status == '1'){
					var data = result.data;
					var html = template('goodsListTpl', {list: data});
					$("#recommend_goods_list_ul").html(html);
				} else{
					M.alert(result.msg || '系统繁忙！');
				}
			}, "json");
		}
		
		loadOrderDashBoard();
		/**加载订单相关统计信息**/
		function loadOrderDashBoard(){
			$.post('${ctx}/mobile/order/loadOrderDashBoard?token=${token}', {}, function(result){
				if(result.status == '1'){
					var data = result.data;
					$("#consumer_info").text(data.consumer_qty || "0");
					$("#stores_info").text(data.stores_qty || "0");
					$("#goods_info").text(data.goods_qty || "0");
					$("#order_info").text(data.order_qty || "0");
				}
			}, "json");
		}
		
		/**加载招商列表**/
		loadInvesetmentList();
		function loadInvesetmentList(){
			var investmentPage = $("#investment_list_ul").mPage({
				url: '${ctx}/mobile/investment/queryInvestmentListPage?token=${token}',
				templateId: 'investmentListTpl',
				isLazy: false
			});
		}
		//跳转进入详情页面
		$('#investment_list_ul').on('click', 'li', function(){
			var subject_id = $(this).attr('subject_id');
			if(subject_id){
				var url = '${ctx}/mobile/investment/detail?token=${token}&subject_id=' + subject_id;
				window.location.href = url;
			}
		});
		
		//格式化图片信息
		template.helper('salesImgFormat', function (picArray) {
			if(typeof(picArray) == 'string'){
				picArray = JSON.parse(picArray || '[]');
			}
			if($.isArray(picArray) && picArray[0]){
				return '${ctx}/file/preview.do?token=${token}&doc_id=' + picArray[0].path_id;
			}
			return '';
		});
	});
</script>
</html>