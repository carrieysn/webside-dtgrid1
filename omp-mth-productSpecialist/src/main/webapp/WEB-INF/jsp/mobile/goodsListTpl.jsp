<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ul class="god-warp noneTop-li" id="goods_list_ul"></ul>
<script id="goodsListTpl" type="text/html">
	{{each list as goods i}}
			<li>
				<div class="god">
					<a href="${ctx}/mobile/goods/detail?token=${token}&goods_id={{goods.goods_id}}" class="god-img"><img src="{{goods.pic_info | imgFormat}}"></a>
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
							<p>有效期：{{goods.valid_thru}}</p>
							<!--<p class="g-announcer">发布者：{{goods.user_name}} {{goods.team_name}}</p>
							<p>发布时间：{{goods.created_date}}</p>-->
							<p class="g-announcer"><span>¥<i>{{goods.discount_price}}</i></span><em>¥{{goods.market_price}}</em>规格：{{goods.specification}}</p>
						</div>
					</a>
				</div>
				<ul class="g-TabBarBox">
					<li class="tj">推荐<small>{{goods.recommend_num}}</small></li>
					<li class="gz">关注<small>{{goods.attention_num}}</small></li>
					<li class="pf">评分<small>{{goods.grade_avg ? goods.grade_avg : 0}}</small></li>
					<li class="pl">评论<small>{{goods.comment_num}}</small></li>
				</ul>
			</li>
	{{/each}}
</script>
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
		template.helper('imgFormat', function (picArray) {
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