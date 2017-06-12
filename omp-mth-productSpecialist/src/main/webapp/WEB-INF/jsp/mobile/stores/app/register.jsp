<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setAttribute("title", "申请注册新店");%>
<%@ include file="/WEB-INF/jsp/mobile/common/common.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/mobile/css/publish.css">
<body style="background-color:#f4f4f4">
	<form id="regForm" onsubmit="return false;">
		<div id="reg0">
			<div class="wth-qp">
				<div class="wish-title wish-top0">
					<div class="fl">合作意愿</div>
					<div class="fr xg-fl" id="hylx_check">
						<input type="hidden" name="stores_type_key" value="HYLX_02" />
						<span class="hide">加盟</span>
						<span class="sel" hylx="HYLX_02">联盟</span>
					</div>
				</div>
			</div>
			<ul class="login-step top0">
				<li>
					<span class="fl">业务员</span>
					<span class="fr">
						<input type="number" name="business_developer" id="business_developer" placeholder="请输入业务员手机" datatype="m" nullmsg="请输入业务员手机！" errormsg="请输入正确的业务员手机！">
					</span>
				</li>
			</ul>
			<div class="btn-ups padding2">
				<a href="javascript:void(0);" id="home_next_btn" class="affirmance" onclick="next(0);" style="width:100%;">下一步</a>
			</div>
			<div class="gaoshi top50">
				<p class="ti">加盟</p>
				<p>实体店，加入每天惠全国连锁品牌，授权管理本地会员资源和营销计划，免费使用进销存管理系统。</p>
				<p class="ti">联盟</p>
				<p>实体店，加入每天惠本地商家联盟，共享会员资源和营销计划，免费使用进销存管理系统。</p>
				<i class="icon-gs"></i>
			</div>
			<div class="gaoshi-tongyi">
				<input type="checkbox" checked="checked" id="agreement_checkbox">
				<label>我已阅读并同意<a href="http://help.meitianhui.com/?id=1007">《店东助手服务协议》</a></label>
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
		M.post("${ctx}/mobile/stores/app/submitReg", $('#regForm').serialize(), function(result){
			layer.close(loading);
			window.location.href = '${ctx}/mobile/stores/app/regSuccess';
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
	        	bridge.callHandler('uploadImg', {
                    id : imgId
                }, function(response) {
                	fn(response);
                });
	        });
	     } else {    // Android
            _imgFn = fn;
	        // 上传图片
            window.jsObj.uploadImg('callback');
	     }
	}
	
	var _imgFn = undefined;
	/**加调方法**/
    function callback(response){
		if(typeof response == 'string'){
			response = JSON.parse(response);
		}
 		_imgFn(response);
    }
	
	//下一步
	function next(index){
		//首先检测是否同意了协议
		var agreement_checked = $('#agreement_checkbox').prop('checked');
		if(!agreement_checked){
			return false;
		}
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
			if(index == 0){	//第一步，检测业务员手机
				var loading = layer.load(2);
				var business_developer = $('#business_developer').val();
				M.post(ctx + '/mobile/stores/app/checkStoresAssistantInfo', {'business_developer': business_developer}, function(result){
					layer.close(loading);
					var data = result.data;
					if(data == 'no'){
						M.alert('业务员手机不存在！');
					} else{
						showNext();
					} 
				}, function(result){
					layer.close(loading);
					M.alert(result.msg || '业务员手机验证失败！');
				});
			} else if(index == 1){	//检测联系电话是否存在
				var loading = layer.load(2);
				var mobile = $('#contact_tel_input').val();
				M.post(ctx + '/mobile/stores/app/checkMobileInfo', {'mobile': mobile}, function(result){
					layer.close(loading);
					var data = result.data;
					if(data == 'yes'){
						M.alert('联系电话已存在！');
					} else{
						showNext();
					} 
				}, function(result){
					layer.close(loading);
					M.alert(result.msg || '联系电话验证失败！');
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
		
		$('#agreement_checkbox').prop('checked', true).trigger('change');
		//同意选项框切换事件
		$('#agreement_checkbox').on('change', function(){
			var checked  = $(this).prop('checked');
			if(checked){
				$('#home_next_btn').addClass('affirmance').removeClass('affirmance-disabled');
			} else{
				$('#home_next_btn').addClass('affirmance-disabled').removeClass('affirmance');
			}
		});
	});
</script>
</html>