<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setAttribute("title", "商户注册");%>
<%@ include file="/WEB-INF/jsp/mobile/common/common.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/mobile/css/publish.css">
<body style="background-color:#f4f4f4">
	<header>
		<div class="head">
			<a href="${ctx}/mobile/user/center?token=${token}" class="return-two"><em></em></a>
			<label class="title">商户注册</label>
		</div>
	</header><!-- /header -->
	<form id="regForm" onsubmit="return false;">
		<div id="reg0">
			<div class="wish-title">
				<div class="fl">合作意愿</div>
				<div class="fr" id="hylx_check">
					<input type="hidden" name="stores_type_key" value="HYLX_02" />
					<span class="hide">加盟</span>
					<span class="sel" hylx="HYLX_02">联盟</span>
					<span hylx="HYLX_04">创业</span>
				</div>
			</div>
			<div class="btn-ups padding2">
				<a href="javascript:void(0);" class="affirmance" onclick="next(0);" style="width:100%;">下一步</a>
			</div>
			<div class="gaoshi top100">
				<p class="ti">加盟</p>
				<p>实体店，加入每天惠全国连锁品牌，授权管理本地会员资源和营销计划，免费使用进销存管理系统。</p>
				<p class="ti">联盟</p>
				<p>实体店，加入每天惠本地商家联盟，共享会员资源和营销计划，免费使用进销存管理系统。</p>
				<i class="icon-gs"></i>
				<p class="ti">创业</p>
				<p>虚拟店，加入每天惠本地商家联盟，共享会员资源和营销计划，免费使用优质订货平台。</p>
				<i class="icon-gs"></i>
			</div>
		</div>
		<div id="reg1" style="display: none;">
			<%@include file="reg1.jsp"%>
		</div>
		<div id="reg2" style="display: none;">
			<%@include file="reg2.jsp"%>
		</div>
		<div id="reg3" style="display: none;">
			<%@include file="reg3.jsp"%>
		</div>
	</form>
</body>
<script type="text/javascript" src="${ctx}/common/third-part/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/common/third-part/Validform_v5.3.2.js"></script>
<script type="text/javascript" src="${ctx}/common/static/js/application.js"></script>
<script type="text/javascript" src="${ctx}/common/static/js/areaSelector.js"></script>
<script type="text/javascript">
	/* $(function(){
		$('.wish-title .fr span').click(function(){
			$('.wish-title .fr span').removeClass('sel');
			$(this).addClass('sel');
		});
	}); */
	
	/***表单验证***/
	var regForm = $("#regForm").Validform({
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
	
	/***提交表单**/
	function submit(){
		var loading = layer.load(2);
		M.post("${ctx}/mobile/stores/submitReg?token=${token}", $('#regForm').serialize(), function(result){
			layer.close(loading);
			window.location.href = '${ctx}/mobile/stores/regSuccess?token=${token}';
		}, function(result){
			layer.close(loading);
			M.alert(result.msg || "提交商户注册审核失败！");
		});
		return false;
	}
	
	/**提交注册审核**/
	function submitReg(){
		regForm.submitForm(false);
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
	
	/***native上传图片方法**/
	function uploadImgFormNative(fn){
		 var imgId = 'img1';
		 if(typeof window.jsObj == 'undefined') {    // IOS
	        setupWebViewJavascriptBridge(function(bridge) {
	        	bridge.callHandler('uploadRawImg', {
                    id : imgId
                }, function(response) {
                	fn(response);
                });
	        });
	     } else {    // Android
            _imgFn = fn;
	        // 上传图片
            window.jsObj.uploadRawImg('callback');
	     }
	}
	
	var _imgFn = undefined;
	/**加调方法**/
    function callback(response){
		var data = JSON.parse(response);
 		_imgFn({picId: data.doc_id});
    }
	
	//下一步
	function next(index){
		var next = parseInt(index) + 1;
		var curReg = '#reg' + index;
		var isValid = true;
		$(curReg).find('[datatype]').each(function(){
			isValid = regForm.check(false, this);
			if(!isValid){
				if($(this).val()){
					M.alert($(this).attr('errormsg'));
				} else{
					M.alert($(this).attr('nullmsg') ||'不能为空！');
				}
				return false;
			}
		});
		if(isValid){
			if(index == 1){	//第一步，检测手机相关信息
				var loading = layer.load(2);
				var mobile = $('#contact_tel_input').val();
				M.post(ctx + '/mobile/stores/checkMobileInfo?token=${token}', {'mobile': mobile}, function(result){
					layer.close(loading);
					var data = result.data;
					if(data == '2'){
						M.alert('该门店已经存在，不需要重新注册。');
					} else if(data == '3'){
						layer.confirm('该门店已经存在，但需要激活使用。', {icon: 3}, function(index){
							layer.close(index);
							showNext();
						});
					} else{
						showNext();
					} 
				}, function(result){
					layer.close(loading);
					M.alert(result.msg || '信息验证失败！');
				});
			} else{
				showNext();
			}
		}
		
		//显示下一步信息
		function showNext(){
			$(curReg).hide();
			$('#reg' + next).show();
		}
	}
	
	/**上一步**/
	function prev(index){
		var prev = parseInt(index) - 1;
		$('#reg' + index).hide();
		$('#reg' + prev).show();
	}
	
	/**会员类型选择**/
	$(function(){
		$('#hylx_check span').not('.hide').on('click', function(){
			var hylx = $(this).attr('hylx');
			if(hylx){
				$('#hylx_check span').removeClass('sel');
				$(this).addClass('sel');
				$('#regForm input[name="stores_type_key"]').val(hylx);
			}
		});
	});
</script>
</html>