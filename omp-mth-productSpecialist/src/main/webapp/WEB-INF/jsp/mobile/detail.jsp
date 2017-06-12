<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setAttribute("title", "商品详情");%>
<%@ include file="/WEB-INF/jsp/mobile/common/common.jsp"%>
<body>
	<div class="head-fixed" style="width:96%;">
		<a href="javascript: window.history.go(-1);" class="return"></a>
		<a href="javascript:void(0);" class="new-icon">
			<c:choose>
				<c:when test="${empty goods.category}">其它</c:when>
				<c:when test="${goods.category eq '试样'}">返利</c:when>
				<c:otherwise>${goods.category}</c:otherwise>
			</c:choose>
		</a>
	</div>
	<!--banner-->
	<div class="device detail-bar">
	    <div class="swiper-container">
	        <div class="swiper-wrapper">
	        	<c:forEach items="${picInfoList}" var="picInfo">
			        <div class="swiper-slide"> 
			        	<img src="${ctx}/file/preview.do?token=${token}&doc_id=${picInfo.path_id}">
			        	<p class="name">${picInfo.title}</p> 
			        </div>
		        </c:forEach>
	        </div>
	    </div>
	    <div class="pagination"></div>
	</div>
	<!--banner -->
	<div class="detail-info">
		<p class="d-name">${goods.title}</p>
		<p class="d-count">
			<small>起订： ${goods.min_buy_qty}</small>
			<small>限购：${goods.max_buy_qty}</small>
			<small>库存： ${goods.sale_qty}</small>
			<small>有效期：${goods.valid_thru}</small>
		</p>
		<p class="d-money">
			<small class="price"><em>¥</em>${goods.discount_price}</small>
			<small class="d-price">¥${goods.market_price}</small>
			<small class="d-gg">规格： ${goods.specification}</small>
			<span style="display: block; margin-top:0.3rem;">
				<small class="d-gg">浏览：<label id="view_label">0</label></small>
				<small class="d-gg">销量：<label id="sell_label">0</label></small>
			</span>
		</p>
		<p class="d-count">
			<small class="title">支付方式：</small>
			<small class="nr">
				<c:choose>
					<c:when test="${goods.payment_way eq 'online'}">在线支付</c:when>
					<c:when test="${goods.payment_way eq 'offline'}">货到付款</c:when>
				</c:choose>
			</small>
		</p>
		<p class="d-count">
			<small class="title">配送范围：</small>
			<small class="nr">${empty goods.delivery_area_path ? '全国' : goods.delivery_area_path}</small>
		</p>
		<p class="d-count">
			<small class="title">特殊说明：</small>
			<small class="nr">${empty goods.remark ? '无' : goods.remark}</small>
		</p>
	</div>
	<div class="bewrite-ti">
		商品描述
	</div>
	<div class="bewrite-info">
		${goods.desc1}
	</div>
	<div class="tag-info">
		<p class="tag-ti">类目</p>
		<p class="tag-a">
			<small>${goods.display_area}</small>
		</p>
	</div>
	<div class="tag-info">
		<p class="tag-ti">标签</p>
		<p class="tag-a">
			<c:forEach items="${labels}" var="label">
				<a href="javascript:void(0);">${label}</a>
			</c:forEach>
		</p>
	</div>
	<ul class="plan-info">
		<li>联系人<small>${goods.contact_person}</small></li>
		<li>手机<small>${goods.contact_tel}</small></li>
	</ul>
	<%-- <ul class="plan-info">
		<li>发布人<small>${goods.user_name}</small></li>
		<li>所属公司<small>${goods.team_name}</small></li>
		<li>发布时间<small>${goods.created_date}</small></li>
	</ul> --%>
	<div class="bewrite-ti">
		详情
	</div>
	<div class="detail-img">
		<c:forEach items="${picDetailInfoList}" var="picInfo">
			<p>
			<span class="name">${picInfo.title}</span>
			<img src="${ctx}/file/preview.do?token=${token}&doc_id=${picInfo.path_id}">
			</p>
		</c:forEach>
	</div>
	<div class="footer-fixed">
		<ul class="fl">
			<li class="tj-li">
				<p>${goods.recommend_num}</p>
				<c:choose>
					<c:when test="${isRecommend eq true}">
						<p class="tj sel-tj">推荐</p>
					</c:when>
					<c:otherwise>
						<p id="recommend_a" class="tj">推荐</p>
					</c:otherwise>
				</c:choose>
			</li>
			<li class="gz-li">
				<p>${goods.attention_num}</p>
				<c:choose>
					<c:when test="${isAttentioned eq true}">
						<p class="gz sel-gz">关注</p>
					</c:when>
					<c:otherwise>
						<p id="attention_a" class="gz">关注</p>
					</c:otherwise>
				</c:choose>
			</li>
			<li class="score-li">
				<p>${goods.grade_num}</p>
				<p class="pf">评分</p>
			</li>
			<li class="review-li">
				<p>${goods.comment_num}</p>
				<p class="pl">评论</p>
			</li>
		</ul>
		<c:choose>
			<c:when test="${goods.owner_id eq sessionUser.user_id}">
				<div class="fr edit-btn">
					<a href="${ctx}/mobile/goods/edit?token=${token}&goods_id=${goods.goods_id}" class="bj">编辑</a>
					<a href="javascript:deleteGoods();" class="sc">删除</a>
				</div>
			</c:when>
			<c:otherwise>
				<a href="javascript:void(0);" class="fr stock-btn">立即采购</a>
			</c:otherwise>
		</c:choose>
	</div>

	<div class="score-ups">
		<div class="abs">
			<ul class="fraction">
				<li class="total" level="0">
					<p class="ti">总分</p>
					<p class="number">
						<c:forEach begin="0" end="4">
							<a href="javascript:void(0);"></a>
						</c:forEach><em>0分</em>
					</p>
				</li>
				<li class="grade-li" name="brand_grade"><p class="ti">品牌</p><p class="number"></p></li>
				<li class="grade-li" name="supplier_grade"><p class="ti">供应商</p><p class="number"></p></li>
				<li class="grade-li" name="pack_grade"><p class="ti">包装</p><p class="number"></p></li>
				<li class="grade-li" name="cost_performance_grade"><p class="ti">性价比</p><p class="number"></p></li>
				<li class="grade-li" name="quality_grade"><p class="ti">质量</p><p class="number"></p></li>
				<li class="grade-li" name="competitiveness_grade"><p class="ti">竞争力</p><p class="number"></p></li>
				<li class="grade-li" name="transportation_grade"><p class="ti">运输</p><p class="number"></p></li>
				<li class="grade-li" name="appearance_grade"><p class="ti">外观</p><p class="number"></p></li>
			</ul>
		</div>
		<div class="btn-ups">
			<a href="javascript:void(0);" class="cancel">取消</a>
			<a href="javascript:void(0);" class="affirmance">确认</a>
		</div>
		
	</div>
	<div class="review-ups">
		<div class="review-abs">
			<ul class="review-info" id="comment_list_ul" template="commentListTpl" url="${ctx}/mobile/goods/queryGoodsCommentList?token=${token}&goods_id=${goods.goods_id}"></ul>
			<script id="commentListTpl" type="text/html">
				{{each list as comment i}}
					<li>
						<p class="ti">
							{{comment.user_name}}<small>{{comment.created_date}}</small>
						</p>
						<p class="n">{{comment.content}}</p>
					</li>
				{{/each}}
			</script>
			<label class="review-content">
				<textarea id="caseContent" placeholder="输入评论内容" maxlength="80"></textarea>
				<small><em id="number-em">0</em>/80</small>
			</label>
		</div>
		<div class="btn-ups">
			<a href="javascript:void(0);" class="cancel">取消</a>
			<a href="javascript:void(0);" class="affirmance">发布</a>
		</div>
	</div>
</body>
  
  <script type="text/javascript">
  	$(function() {
	    FastClick.attach(document.body);
	});
	  //branner图JS
	  var mySwiper = new Swiper('.swiper-container',{
	    pagination: '.pagination',
	    loop:true,
	    grabCursor: true,
	    paginationClickable: true
	  });

	/**********H5与Native交互**********/
	// init for ios
	function setupWebViewJavascriptBridge(callback) {
	    if (window.WebViewJavascriptBridge) {
	        return callback(WebViewJavascriptBridge);
	    }
	    if (window.WVJBCallbacks) {
	        return window.WVJBCallbacks.push(callback);
	    }
	    window.WVJBCallbacks = [callback];
	    var WVJBIframe = document.createElement('iframe');
	    WVJBIframe.style.display = 'none';
	    WVJBIframe.src = 'wvjbscheme://__BRIDGE_LOADED__';
	    document.documentElement.appendChild(WVJBIframe);
	    setTimeout(function() {
	        document.documentElement.removeChild(WVJBIframe)
	    }, 0)
	}
	  
	$(function(){
		if(typeof window.jsObj == 'undefined') {    // IOS
	        setupWebViewJavascriptBridge(function(bridge) {
	            // 关闭当前页
	            $('.return').click(function(e) {
	                //e.preventDefault();
	                bridge.callHandler('returnPage', {}, function(response) {});
	            });
	        });
	    } else {    // Android
	        // 关闭当前页
	        $('.return').click(function(e) {
	            window.jsObj.returnPage();
	        });
	    }
		
		//textarea控制字数
		$("#caseContent").keyup(function(){
		   var len = $(this).val().length;
		   if(len > 80){
			 $(this).val($(this).val().substring(0,80));
		   }
		   var num = 80 - len;
		   $("#number-em").text(num); 
		});

		//评分，显示评分页面
		$('.score-li').click(function(){
			$('.score-ups').show();
			$('body').bind('touchmove', function(event) {
				event.preventDefault();
			});
		});
		//textarea光标事件
		$('#caseContent').focus(function(){
			$('body').unbind('touchmove');
		})
		$('#caseContent').blur(function(){
			$('body').bind('touchmove', function(event) {
				event.preventDefault();
			});
		})
		//评分取消
		$('.score-ups .cancel').click(function(){
			$('.score-ups').hide();
			$('body').unbind('touchmove');
		});
		//评论
		$('.review-li').bind('click', function(){
			$('.review-ups').show();
			
		});
		//评论取消
		$('.review-ups .cancel').click(function(){
			$('.review-ups').hide();
			
		});
	})
  </script>
</body>
<script type="text/javascript">
	$(function(){
		initGradeLevel();
		//初始化评分等级
		function initGradeLevel(){
			var gradeHtml = "";
			for(var i = 0;i < 5;i++){
				gradeHtml += '<a href="javascript:void(0);"></a>';
			}
			$(".grade-li .number").append(gradeHtml);
			$(".grade-li .number a").click(function(){
				//选择评分
				var index = $(this).index() + 1;
				var scoreEl = $(this).closest("li").attr("level", index).find(".number a").removeClass('sel');
				for(var i = 0;i < index;i++){
					$(scoreEl[i]).addClass('sel');
				}
				calcGrade();
			});
			
			//计算综合评分
			function calcGrade(){
				var count = 0, countLevel = 0;
				$(".grade-li").each(function(){
					var level = $(this).attr("level");
					if(level){
						countLevel += parseInt(level);
					}
					count++;
				});
				//计算评分
				var avgLevel = (countLevel / count).toFixed(1);
				var index = Math.ceil(avgLevel);
				$(".fraction .total").attr("level", avgLevel);
				var scoreEl = $(".fraction .total .number a").removeClass("sel");
				for(var i = 0;i < index;i++){
					$(scoreEl[i]).addClass('sel');
				}
				$(".fraction .total .number em").first().text(avgLevel + "分");
			}
		}
		
		//进行评分操作
		$(".score-ups .affirmance").click(function(){
			var total_grade = parseFloat($(".fraction .total").attr("level") || 0);
			if(total_grade > 0){
				var form = {goods_id: "${goods.goods_id}", total_grade: total_grade};
				$(".grade-li").each(function(){
					var name = $(this).attr("name");
					var level = parseInt($(this).attr("level") || 0);
					form[name] = level;
				});
				$.post("${ctx}/mobile/goods/gradeGoods?token=${token}",form, function(result){
					if(result.status == '1'){
						M.alert('评分成功！');
						window.location.reload();
					} else{
						M.alert('评分失败！');
					}
				},"json");
			} else{
				M.alert('请先评分！');
			}
		});
		
		//推荐
		$("#recommend_a").bind("click", function(){
			var _this = this;
			$.post("${ctx}/mobile/goods/recommendGoods?token=${token}", {goods_id: "${goods.goods_id}", display_area_id: "${display_area_id}"}, function(result){
				if(result.status == '1'){
					var oldNum = parseInt($(_this).prev("p").text() || 0);
					$(_this).prev("p").text(++oldNum);
					$(_this).unbind("click").addClass("sel-tj");
				} else{
					M.alert(result.msg || '推荐失败！')
				}
			}, "json");
		});
		
		//关注
		$("#attention_a").bind("click", function(){
			var _this = this;
			$.post("${ctx}/mobile/goods/attentionGoods?token=${token}", {goods_id: "${goods.goods_id}"}, function(result){
				if(result.status == '1'){
					var oldNum = parseInt($(_this).prev("p").text() || 0);
					$(_this).prev("p").text(++oldNum);
					$(_this).unbind("click").addClass("sel-gz");
				} else{
					M.alert(result.msg || '关注失败！')
				}
			}, "json");
		});
		
		loadCommentList();
		/**加载评论列表**/
		function loadCommentList(){
			var table = $("#comment_list_ul");
			var url = $(table).attr("url");
			var templateId = $(table).attr("template");
			$.post(url, {}, function(result){
				if(result.status == '1'){
					var html = template(templateId, {list: result.data});
	                $(table).html(html);
				}
			}, "json");
		}
		
		//发布评论
		$(".review-ups .affirmance").bind('click', function(){
			var content = $("#caseContent").val();
			if(content && content.length > 0){
				var _this = this;
				$.post("${ctx}/mobile/goods/commentGoods?token=${token}", {goods_id: "${goods.goods_id}", comment_content: content}, function(result){
					if(result.status == '1'){
						$(".review-li").unbind('click').find("p").first().text(parseInt($(".review-li p").first().text() || 0) + 1)
						$(_this).unbind('click');
						$('.review-ups').hide();
						M.alert('评论成功！');
					} else{
						M.alert(result.msg || '评论失败！')
					}
				}, "json");
			} else{
				M.alert('请输入评论内容！');
			}
		});
		
		//加载商品的浏览、销量信息
		loadGoodsSellInfo();
		function loadGoodsSellInfo(){
			$.post("${ctx}/mobile/goods/queryGoodsSellInfo?token=${token}",{'goods_id': '${goods.goods_id}'}, function(result){
				if(result && result.data){
					var data = result.data;
					$("#view_label").text(data.view || "0");
					$("#sell_label").text(data.sell || "0");
				}
			}, 'json');
		}
	})
</script>
<c:if test="${goods.owner_id eq sessionUser.user_id}">
	<script type="text/javascript">
		//删除产品信息
		function deleteGoods(){
			var flag = window.confirm("是否确定删除产品？");
			if(flag){
				$.post("${ctx}/mobile/goods/delete?token=${token}", {goods_id: "${goods.goods_id}"}, function(result){
					if(result.status == '1'){
						M.alert('删除成功！');
						window.location.href="${ctx}/mobile/user/goodsList?token=${token}&statistics_type=publish";
					} else{
						M.alert(result.msg || "删除失败！");
					}
				}, "json");
			}
		}
	</script>
</c:if>
</html>