<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="login-img mar-bot">
	<div class="ti">营业许可证<i>(最多5张)</i></div>
</div>
<div class="img-wrap">
	  <input type="hidden" name="licence_pic_path" class="image_val">
      <div class="box-wrap" style="background:#ffffff;">
      	 <c:forEach begin="1" end="5" varStatus="status">
           <div class="box">
              <a href="javascript: void(0);" class="add"></a>
              <input type="hidden" name="" class="pic-id" />
              <input type="hidden" name="" class="pic-desc" />
           </div>
         </c:forEach>
      </div>
</div>
<div class="login-img">
	<div class="ti">法人身份证<i>(正反各一张)</i></div>
</div>
<div class="img-wrap">
	 <input type="hidden" name="id_card_pic_path" class="image_val">
     <div class="box-wrap" style="background:#ffffff;">
	 	<c:forEach begin="1" end="5" varStatus="status">
          <div class="box">
              <a href="javascript: void(0);" class="add"></a>
              <input type="hidden" name="" class="pic-id" />
          </div>
        </c:forEach>
      </div>
</div>
<div class="btn-ups padding2">
    <a href="javascript:void(0);" class="cancel" onclick="prev(3);">上一步</a>
	<a href="javascript:void(0);" class="affirmance" onclick="submitReg();">完成注册</a>
</div>
<script type="text/javascript">
	$(function(){
		$('.img-wrap .box .add').click(function(){
			var _this = this;
			uploadImgFormNative(function(data){
				showImg(_this, data);
			});
			//showImg(_this,{picId: 'xx'});
		});
		
		function showImg(_this, data){
			 // 显示图片
            $(_this).parent().append(createPicDom(data.picId));
            // 填入图片ID
            $(_this).parent().find('.pic-id').val(data.picId);
            // 隐藏上传按钮
            $(_this).parent().find('.add').hide();
            var picArray = new Array();
            $(_this).closest('.img-wrap').find('.pic-id').each(function(){
            	if(this.value){
            		picArray.push(this.value);
            	}
            });
            $(_this).closest('.img-wrap').find('.image_val').val(picArray.join('|'));
		}
		
		// 创建图片DOM
		function createPicDom(picId) {
		    var $wrap = $('<div class="wrap"></div>');
		    // 这里的src是根据id获取图片url的controller方法
		    var $img = $('<img src="${ctx}/file/preview.do?token=${token}&doc_id=' + picId + '" />');
		    var $del = $('<a href="javascript: void(0);" class="del"></a>');
		    $wrap.append($img).append($del);
		    return $wrap;
		}
		
		 // 移除图片
	    $('.img-wrap .box').on('click', '.del', function() {
	    	$(this).parent().parent().find('.add').show();
	        $(this).parent().parent().find('.pic-id').val('');
	    	//更新数据
	        var picArray = new Array();
            $(this).closest('.img-wrap').find('.pic-id').each(function(){
            	if(this.value){
            		picArray.push(this.value);
            	}
            });
	        $(this).closest('.img-wrap').find('.image_val').val(picArray.join('|'));
	        
	        //删除图片
	        $(this).parent().remove();
	    });
	});
</script>