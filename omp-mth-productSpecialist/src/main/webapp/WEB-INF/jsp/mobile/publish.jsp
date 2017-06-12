<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setAttribute("title", "商品编辑");%>
<%@ include file="/WEB-INF/jsp/mobile/common/common.jsp"%>
<link type="text/css" href="${ctx}/static/mobile${empty bt ? '' : '/'.concat(bt)}/css/publish.css?v=0.11" rel="stylesheet" />
<body>
	<div class="top-bar">
        <!-- <a href="javascript: window.history.go(-1);" class="close"></a> -->
        <span id="tmp">产品发布</span>
    </div>
    <form id="goodsForm" method="post" onsubmit="return false;">
		<div class="img-wrap bt">
	       	<input type="hidden" name="pic_info" id="pic_info" value="${goods.pic_info}">
	        <p>图片</p>
	        <div class="box-wrap">
	        	<c:forEach begin="1" end="5" varStatus="status">
		            <div class="box">
		                <a href="javascript: void(0);" class="add" id="add${status.index}"></a>
		                <input type="hidden" name="" class="pic-id" />
		                <input type="hidden" name="" class="pic-desc" />
		            </div>
	        	</c:forEach>
	        </div>
		</div>
		<div class="img-wrap bt">
	       	<input type="hidden" name="pic_detail_info" id="pic_detail_info" value="${goods.pic_detail_info}">
	        <p>详情</p>
	        <div class="box-wrap">
	            <c:forEach begin="11" end="20" varStatus="status">
		            <div class="box">
		                <a href="javascript: void(0);" class="add" id="add${status.index}"></a>
		                <input type="hidden" name="" class="pic-id" />
		                <input type="hidden" name="" class="pic-desc" />
		            </div>
	        	</c:forEach>
	        </div>
		</div>
	    <div class="top-tab">
	        <ul>
	            <li class="on" target="goods-info">产品信息</li>
	            <li target="sales-strategy">销售方案</li>
	        </ul>
	    </div>
   		<!-- 产品信息 -->
    	<input type="hidden" name="goods_id" value="${goods.goods_id}">
		<input type="hidden" name="created_date" value="${goods.created_date}">
		<input type="hidden" id="ladder_price" name="ladder_price" value="{}">
	    <div id="goods-info" class="tab-wrap" style="display: block;">
	        <div class="main-wrap">
	            <div class="tit-wrap bt">
	                <textarea name="title" class="tit" maxlength="40" placeholder="请输入产品名称" datatype="*" nullmsg="请输入产品名称！">${goods.title}</textarea>
	                <span class="words-num">${empty goods.title ? 0 : goods.title.length()}/40</span>
	            </div>
	            <div class="desc-wrap bt">
	                <textarea class="desc" name="desc1" placeholder="请输入产品描述" datatype="*" nullmsg="请输入产品描述！">${goods.desc1}</textarea>
	            </div>
	            <div class="line-wrap bt">
					<span>类目</span>
	                <input type="text" class="ipt-val" name="display_area" value="${goods.display_area}" id="displayAreaIpt" 
	                	placeholder="选择类目" readonly="readonly" datatype="*" nullmsg="请选择类目"/>
	            </div>
	            <div class="line-wrap">
	                <span>规格</span>
	                <input type="text" class="ipt-val" name="specification" value="${goods.specification}" placeholder="请输入规格" datatype="*" nullmsg="请请输入规格"/>
	            </div>
	            <div class="line-wrap">
	                <span>库存</span>
	                <input type="number" class="ipt-val" name="sale_qty" value="${goods.sale_qty}" placeholder="请输入库存量" datatype="n" nullmsg="请输入库存量"/>
	            </div>
	            <div class="line-wrap">
	                <span>厂家</span>
	                <input type="text" class="ipt-val" name="manufacturer" value="${goods.manufacturer}" placeholder="请输入厂家"/>
	            </div>
	           <%--  <div class="line-wrap">
	                <span>经销商</span>
	                <input type="text" class="ipt-val" id="proJXS" name="supplier" value="${goods.supplier}" placeholder="请输入经销商" datatype="*" nullmsg="请选择经销商"/>
	                <input type="hidden" name="supplier_id" value="${goods.supplier_id}"/>
	            </div> --%>
	            <div class="line-wrap bt">
	                <span>标签</span>
	                <input type="text" class="ipt-val" name="label" value="${goods.label}" placeholder="请用逗号分隔" />
	            </div>	           
	             <div class="line-wrap bt">
	                <span>联系人</span>
	                <input type="text" class="ipt-val" name="contact_person" value="${goods.contact_person}" placeholder="请输入联系人" datatype="*" nullmsg="请输入联系人！"/>
	            </div>
	            <div class="line-wrap">
	                <span>电话</span>
	                <input type="tel" class="ipt-val" name="contact_tel" value="${goods.contact_tel}" placeholder="请输入手机号" datatype="m" nullmsg="请输入联系人！" errormsg="请输入正确的手机号！"/>
	            </div>
	        </div>
	    </div>
	    <!-- 销售方案 -->
	    <div id="sales-strategy" class="tab-wrap" style="display: none;">
	        <%@ include file="goodsext.jsp" %>
	    </div>
    </form>
    <div class="btn-ups padding2">
	    <a href="javascript:window.history.go(-1);" class="cancel">取消</a>
		<a href="javascript:void(0);" class="affirmance" id="submit_btn">确定</a>
	</div>
    <!-- 遮罩层 -->
    <div id="mask"></div>
    <!-- 选择类目 -->
    <div class="prompt" id="displayAreaWrap">
    	<ul>
    		<c:forEach items="${displayAreaList}" var="displayArea">
				<li><label><input type="checkbox" name="display_area_ck" value="${displayArea.display_area}" />${displayArea.display_area}</label></li>
    		</c:forEach>
    	</ul>
        <a href="javascript: void(0);" id="areaConfirmBtn">确定</a>
    </div>
    <div class="prompt-jxs" id="supplier_display_div" >
    	<div class="cxtj">
    		<input type="text" class="search_input"><a class="pro-cx search_btn">查询</a>
    	</div>
    	<ul class="supplier_list"></ul>
    	<a href="javascript: void(0);" class="ok_btn">确定</a>
    </div>
</body>
<script type="text/javascript" src="${ctx}/common/third-part/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/common/third-part/Validform_v5.3.2.js"></script>
<script type="text/javascript" src="${ctx}/common/static/js/application.js"></script>
<script type="text/javascript" src="${ctx}/common/static/js/areaSelector.js"></script>
<script type="text/javascript">
$(function() {
    $(function() {
        FastClick.attach(document.body);
        M.area(".selectContainer");
    });

    // 切换tab
    $('.top-tab li').click(function() {
        $('.top-tab li').removeClass('on');
        $(this).addClass('on');
        var target = $(this).attr('target');
        $('.tab-wrap').hide();
        $('#'+target).show();
    });
    // 计算剩余数字
    $('.tit').keyup(function() {
        var wordsNum = $(this).val().length;
        if(wordsNum > 40) {
            wordsNum = 40;
            $(this).val($(this).val().substring(0,40));
        }
        $('.words-num').text(wordsNum + '/40');
    });
    // 下拉选择
    $('.tp-select').change(function() {
    	var text = $(this).find('option:checked').text();
        $(this).parent().find('.ipt-txt').val(text);
        $(this).parent().find('.ipt-val').val($(this).val());
    });
    
    // 日期选择
    $('.tp-ipt').change(function() {
        $(this).parent().find('.ipt-val').val($(this).val());
    });
    // 选择类目
    $('#displayAreaIpt').click(function() {
        $('#mask').show();
        $('#displayAreaWrap').show();
        $('body').bind('touchmove', function(e) {
            e.preventDefault();
        });
    });
 	// 选择经销商
    $('#proJXS').click(function() {
        $('#mask').show();
        $('.prompt-jxs').show();
        $('body').bind('touchmove', function(e) {
            e.preventDefault();
        });
    });
    // 点击遮罩层关闭
    $('#mask').click(function() {
        $('#displayAreaWrap').hide();
        $('#supplier_display_div').hide();
        $(this).hide();
        $('body').unbind('touchmove');
    });
    // 点击确定按钮
    $('#areaConfirmBtn').click(function() {
        var arr = [];
        $('#displayAreaWrap input:checked').each(function() {
            arr.push($(this).val());
        });
        $('#displayAreaIpt').val(arr.join(','));
        $('#displayAreaWrap').hide();
        $('#mask').hide();
        $('body').unbind('touchmove');
    });
    // 模拟选择图片（浏览器中测试使用，集成到APP中后需删除这一段）
/*     $('.box .add').click(function() {
        // 显示图片
        $(this).parent().append(createPicDom('xxx'));
        // 填入图片ID
        $(this).parent().find('.pic-id').val('xxx');
        // 填入图片描述
        $(this).parent().find('.pic-desc').val('desc');
        // 隐藏上传按钮
        $(this).parent().find('.add').hide();
    }); */
    // 移除图片
    $('.box').on('click', '.del', function() {
        $(this).parent().parent().find('.add').show();
        $(this).parent().parent().find('.pic-id').val('');
        $(this).parent().remove();
    });
});
// 创建图片DOM
function createPicDom(picId) {
    var $wrap = $('<div class="wrap"></div>');
    // 这里的src是根据id获取图片url的controller方法
    var $img = $('<img src="${ctx}/file/preview.do?token=${token}&doc_id=' + picId + '" />');
    var $del = $('<a href="javascript: void(0);" class="del"></a>');
    $wrap.append($img).append($del);
    return $wrap;
}

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

// callback for android
function callback(id, picId, desc) {
    // 显示图片
    $('#'+id).parent().append(createPicDom(picId));
    // 填入图片ID
    $('#'+id).parent().find('.pic-id').val(picId);
    // 填入图片描述
    $('#'+id).parent().find('.pic-desc').val(desc);
    // 隐藏上传按钮
    $('#'+id).parent().find('.add').hide();
}

$(function() {
    if(typeof window.jsObj == 'undefined') {    // IOS
        setupWebViewJavascriptBridge(function(bridge) {
            // 上传图片
            $('.box .add').click(function(e) {
                e.preventDefault()
                bridge.callHandler('uploadImg', {
                    id : $(this).attr('id')
                }, function(response) {
                    // 显示图片
                    $('#'+response.id).parent().append(createPicDom(response.picId));
                    // 填入图片ID
                    $('#'+response.id).parent().find('.pic-id').val(response.picId);
                    // 填入图片描述
                    $('#'+response.id).parent().find('.pic-desc').val(response.desc);
                    // 隐藏上传按钮
                    $('#'+response.id).parent().find('.add').hide();
                });
            });
        });
    } else {    // Android
        // 上传图片
        $('.box .add').click(function(e) {
            window.jsObj.uploadImg($(this).attr('id'),'callback');
        });
    }
    
    initImage();
    /**初始化图片信息*/
    function initImage(){
		var picArray = '${empty goods.pic_info ? "{}" : goods.pic_info}';
		var picDetailArray = '${empty goods.pic_detail_info ? "{}" : goods.pic_detail_info}';
		loadImage(picArray, 0);
		loadImage(picDetailArray, 1);
		function loadImage(picArray, index){
			if(picArray){
				picArray = JSON.parse(picArray);
				for(var i = 0;i < picArray.length;i++){
					var pic = picArray[i];
					var picEl =  $(".box-wrap:eq(" + index + ") .box").eq(i);
					 // 显示图片
	                picEl.append(createPicDom(pic.path_id));
	                // 填入图片ID
	                picEl.find('.pic-id').val(pic.path_id);
	                // 填入图片描述
	                picEl.find('.pic-desc').val(pic.title);
	                // 隐藏上传按钮
	                picEl.find('.add').hide();
				}
			}
		}
    }
    
    initDisplayArea();
    /**初始化类目信息**/
    function initDisplayArea(){
    	var displayArea = '${goods.display_area}';
    	if(displayArea){
    		displayArea = displayArea.split(',');
    		for(var i = 0;i < displayArea.length;i++){
    			var value = displayArea[i];
    			$("#displayAreaWrap input[name='display_area_ck'][value='" + value + "']").attr('checked', true);
    		}
    	}
    }
    
    var vf = $("#goodsForm").Validform({
    	tipSweep: true,
    	tiptype:function(msg,o,cssctl){
    		if(o.type == 3){
    			M.alert(msg);
    		}
    	},
    	beforeSubmit: function(curform){
    		submit();
    		return false;
    	}
    });
    
  	//发布
	$("#submit_btn").on('click', function () {
		//验证并初始化一些表单信息
		vf.submitForm(false);
	});
    
    //提交表单
    function submit(){
		//验证成功、将上传的图片数据进行格式化
		var goodsImage = getImageArray(0);
		if(goodsImage.length < 1){
			M.alert('请至少上传一张图片');
			return false;
		} else{
			$("#pic_info").val(JSON.stringify(goodsImage));
		}
		$("#pic_detail_info").val(JSON.stringify(getImageArray(1)));
		//发布产品信息
		publish();
		function publish(){
			layer.confirm('是否确认发布？', {icon: 3}, function(index){
				layer.close(index);
				formatDeliveryArea();
				var loading = layer.load(2);
				$.post("${ctx}/mobile/goods/save?token=${token}", $("#goodsForm").serialize() , function(result){
					layer.close(loading);
					if(result.status == '1'){
						M.alert('发布成功！', function(){
							var goods_id = result.data; //产品编号
							window.location.href = ctx + "/mobile/goods/detail?token=${token}&goods_id=" + goods_id; 
						});
					} else{
						M.alert(result.msg || '发布失败！');
					}
				}, "json");
			});
		}
		//格式化已选择的地区
		function formatDeliveryArea(){
			var areaArray = new Array();
			$(".selectContainer").each(function(){
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
		}
		
		//格式化图片
		function getImageArray(index){
			var imageArray = new Array();
			$('.img-wrap:eq(' + index + ') .box').each(function(){
				var path_id = $(this).find(".pic-id").val();
				var title = $(this).find('.pic-desc').val();
				if(path_id){
					imageArray.push({path_id: path_id, title: title});
				}
			});
			return imageArray;
		}
    }
    
    //供应商查询
    /* $('#supplier_display_div .search_btn').on('click', function(){
    	var search_input =  $('#supplier_display_div .search_input').val();
    	$.post('${ctx}/mobile/stores/querySupplierList?token=${token}',{'search_input': search_input}, function(result){
    		var html = '';
    		$.each(result.data, function(index, supplier){
    			html += '<li><label><input type="radio" name="supplier_text_id" value="' + supplier.id + '" text="' + supplier.text + '">' + supplier.text + '</label></li>';
    		});
    		$('#supplier_display_div .supplier_list').html(html);
    	}, 'json');
    });
    
    $('#supplier_display_div .ok_btn').on('click', function(){
    	var selected = $('#supplier_display_div .supplier_list :radio:checked');
    	if(selected.length > 0){
    		var supplier_id = $(selected).val();
    		var supplier_name = $(selected).attr('text');
    		$('#goodsForm input[name="supplier_id"]').val(supplier_id);
    		$('#goodsForm input[name="supplier"]').val(supplier_name);
    	}
    	$('#supplier_display_div').hide();
    	$('#mask').hide();
        $('body').unbind('touchmove');
    }); */
});
</script>
</body>
</html>