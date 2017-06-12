<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setAttribute("title", "招商详情");%>
<%@ include file="/WEB-INF/jsp/mobile/common/common.jsp"%>
<body>
	<header>
		<div class="head">
			<a href="${ctx}/mobile/investment/index?token=${token}" class="return-two"><em></em></a>
			<label class="title">招商创建</label>
		</div>
	</header>
	<!-- /header -->
	<form id="investmentForm">
		<input type="hidden" name="subject_id" value="${subject.subject_id}">
		<ul class="chuangjian-hd">
			<li>
				<span class="name">标题：</span> 
				<span><input type="text" placeholder="请填写内容" name="title" value="${subject.title}" datatype="*" nullmsg="请输入标题！"></span>
			</li>
			<li class="nr">
				<span class="name">内容：</span> 
				<span><textarea name="content" placeholder="请填写内容" datatype="*" nullmsg="请输入标题！">${subject.content}</textarea></span>
			</li>
			<li class="txt-color">
				<span class="name">有效时间：</span>
				<span><input type="date"placeholder="请填写内容" name="expiration_date" value="${subject.expiration_date}" datatype="*" nullmsg="请输入有效时间！"></span>
			</li>
			<li class="txt-color">
				<span class="name">发布人：</span>
				<span>${user_name}</span>
			</li>
		</ul>
		
		<div class="zs-but-list">
			<a href="${ctx}/mobile/investment/index?token=${token}">取消</a>
			<a href="javascript:void(0);" class="fb" id="submit_btn">发布</a>
		</div>
	</form>
	<%@ include file="/WEB-INF/jsp/mobile/common/footer.jsp"%>
</body>
<script type="text/javascript" src="${static_resource_server}/common/third-part/Validform_v5.3.2.js"></script>
<script type="text/javascript">
	$(function(){
		var vf = $("#investmentForm").Validform({
	    	tipSweep: true,
	    	tiptype:function(msg,o,cssctl){
	    		if(o.type == 3){
	    			console.info(2);
	    			M.alert(msg);
	    		}
	    	},
	    	beforeSubmit: function(curform){
	    		var params = M.formatElement('#investmentForm');
	    		layer.confirm('是否确认发布？', {icon: 3}, function(index){
		  			layer.close(index);
		  			var loading = layer.load();
		  			M.post(ctx + '/mobile/investment/save?token=${token}', params, function(result){
		  				layer.close(loading);
		  				M.alert('发布成功！', function(){
		  					var subject_id = result.data;
		  					window.location.href = '${ctx}/mobile/investment/index?token=${token}';
		  				});
		  			}, function(result){
		  				layer.close(loading);
		  				M.alert(result.msg || '发布失败！');
		  			});
		  		});
	    		return false;
	    	}
	    });
	    
	  	//发布
		$("#submit_btn").on('click', function () {
			//验证并初始化一些表单信息
			vf.submitForm(false);
		});
	});
</script>
</html>