<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setAttribute("title", "门店查询");%>
<%@ include file="/WEB-INF/jsp/mobile/common/common.jsp"%>
<body>
	<header>
		<div class="head" id="search_contianer">
			<a href="${ctx}/mobile/user/center?token=${token}" class="return-two"><em></em></a>
			<form onsubmit="$('#search_btn').trigger('click'); return false;">
				<label class="md-search"><input type="search" name="condition" placeholder="名称、联系人、电话"></label>
				<a class="but-grabble" id="search_btn">搜索</a>
			</form>
		</div>
	</header>
	<!-- header -->
	<ul class="store-info" id="stores_list_ul"></ul>
	<script id="storesListTpl" type="text/html">
		{{each list as stores i}}
			<li stores_id="{{stores.stores_id}}">
				<div class="name">{{stores.stores_name}}</div>
				<div>
					<em>联系人</em>
					<span>{{stores.contact_person}}</span>
				</div>
				<div>
					<em>联系电话</em>
					<span>{{stores.contact_tel}}</span>
				</div>
				<div>
					<em>所在地区</em>
					<span>{{stores.area_path}}{{stores.path}}</span>
				</div>
				<div>
					<em>类型</em>
					<span>
						{{if stores.business_type_key == 'MDLX_01'}}便利店 
						{{else if stores.business_type_key == 'MDLX_02'}}酒店
						{{else if stores.business_type_key == 'MDLX_03'}}药店
						{{else if stores.business_type_key == 'MDLX_04'}}美容店
						{{else if stores.business_type_key == 'MDLX_05'}}餐饮店
						{{else if stores.business_type_key == 'MDLX_06'}}KTV
						{{else if stores.business_type_key == 'MDLX_07'}}水店
						{{else if stores.business_type_key == 'MDLX_09'}}健身房
						{{else if stores.business_type_key == 'MDLX_10'}}汽车服务
						{{else if stores.business_type_key == 'MDLX_11'}}百货店
						{{else if stores.business_type_key == 'MDLX_13'}}干洗店
						{{else if stores.business_type_key == 'MDLX_14'}}家政服务
						{{else if stores.business_type_key == 'MDLX_15'}}五金店
						{{else if stores.business_type_key == 'MDLX_16'}}咖啡厅
						{{else if stores.business_type_key == 'MDLX_17'}}棋牌室
						{{else if stores.business_type_key == 'MDLX_18'}}酒吧
						{{else if stores.business_type_key == 'MDLX_19'}}理发店
						{{else if stores.business_type_key == 'MDLX_21'}}水果店
						{{else if stores.business_type_key == 'MDLX_22'}}其它
						{{else if stores.business_type_key == 'MDLX_23'}}电影院
						{{else if stores.business_type_key == 'MDLX_25'}}花店
						{{else if stores.business_type_key == 'MDLX_26'}}商超
						{{else if stores.business_type_key == 'MDLX_27'}}诊所
						{{else if stores.business_type_key == 'MDLX_28'}}会所
						{{else if stores.business_type_key == 'MDLX_29'}}旅馆
						{{else if stores.business_type_key == 'MDLX_30'}}家电维修
						{{/if}}
					</span>
				</div>
				<div>
					<em>助教客服</em>
					<span>{{stores.assistant_name}}&nbsp;{{stores.assistant}}</span>
				</div>
			</li>
		{{/each}}
	</script>
	<script type="text/javascript">
		$(function(){
			var page = $("#stores_list_ul").mPage({
				url: '${ctx}/mobile/stores/queryStoresListPage?token=${token}',
				templateId: 'storesListTpl',
				isLazy: true
			});
			
			/**加载店东助手相关的门店**/
			page.mPage('reload', {'assistant_id': '${assistant_id}' || '-1'});
			
			/**搜索条件**/
			$("#search_btn").click(function(){
				var condition = $("#search_contianer input[name='condition']").val();
		 		page.mPage('reload', {'condition': condition});		
			});
			
			$('#stores_list_ul').on('click','li', function(){
				var stores_id = $(this).attr('stores_id');
				if(stores_id){
					window.location.href = '${ctx}/mobile/stores/detail?stores_id=' + stores_id;
				}
			});
		});
	</script>
	<%@ include file="/WEB-INF/jsp/mobile/common/footer.jsp"%>
</body>
</html>