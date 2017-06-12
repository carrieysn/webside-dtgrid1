<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setAttribute("title", "招商详情");%>
<%@ include file="/WEB-INF/jsp/mobile/common/common.jsp"%>
<body style="background: #ffffff;">
	<header>
		<div class="head">
			<a href="javascript: window.history.go(-1);" class="return-two"><em></em></a>
			<label class="title">招商详情</label>
		</div>
	</header>
	<!-- /header -->
	<div class="goods dingbu40">
		<ul class="god-warp">
			<li>
				<div class="zs-name">${subject.title}</div>
				<p class="zs-nr">${subject.content}</p>
				<p class="zs-date bor-db">
					<span>发布者：${subject.team_name}${subject.user_name}</span>
					<span>发布时间：${subject.created_date}</span>
				</p>
			</li>
		</ul>
		<div class="pinglun-title">
			<span>评论内容</span><a class="icon-pinlun"></a>
		</div>
		<ul class="pinlun-list" id="comment_list_ul"></ul>
		<script type="text/html" id="commentListTpl">
			{{each list as comment i}}
				<li>
					<div class="name">{{comment.team_name}} - {{comment.user_name}}</div>
					<div class="nr">{{comment.content}}</div>
					<div class="date">{{comment.created_date}}</div>
				</li>
			{{/each}}
		</script>
		<div class="txt-pl" style="display: none;">
			<input type="text" placeholder="请最多输入200字评论" name="content" id="content">
			<a href="javascript:void(0);" class="icon-ss" id="send_comment">发送</a>
		</div>
	</div>
	<%@ include file="/WEB-INF/jsp/mobile/common/footer.jsp"%>
</body>
<script>
	$(function() {
		$('.icon-pinlun').click(function(){
			$('.txt-pl').toggle();
		});
		
		var commentListPage = $("#comment_list_ul").mPage({
			url: '${ctx}/mobile/investment/queryCommentListPage?token=${token}&subject_id=${subject.subject_id}',
			templateId: 'commentListTpl',
			isLazy: false
		});
		
		//发送评论
		$('#send_comment').on('click', function(){
			var content = $('#content').val();
			if(content){
				if(content.length > 400){
					M.alert('请最多输入200字评论！');
					return false;
				}
				var loading = layer.load();
				M.post('${ctx}/mobile/investment/publishComment?token=${token}',{'content': content, 'subject_id': '${subject.subject_id}'}, function(result){
					layer.close(loading);
					var data = result.data;
					var html = template('commentListTpl', {list: [data]});
					$('#comment_list_ul').append(html);
					$('#content').val('');
					$('.icon-pinlun').trigger('click');
				}, function(result){
					layer.close(loading);
					M.alert(result.msg || '评论发布失败！');
				});
			}
		});
	});
</script>
</html>