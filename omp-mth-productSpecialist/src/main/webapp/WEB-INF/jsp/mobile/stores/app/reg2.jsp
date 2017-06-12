<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="login-img mar-bot wish-top0">
	<div class="ti">门头<i>(最多5张)</i></div>
</div>
<div class="img-wrap">
	   <input type="hidden" name="new_facade_pic_path" class="image_val" datatype="*" nullmsg="请上传门头照片！">
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
	<div class="ti">内景<i>(最多5张)</i></div>
</div>
<div class="img-wrap">
	   <input type="hidden" name="new_stores_pic_path" class="image_val" datatype="*" nullmsg="请上传内景照片！">
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
<div class="btn-ups padding2">
    <a href="javascript:void(0);" class="cancel" onclick="prev(2);">上一步</a>
	<a href="javascript:void(0);" class="affirmance" onclick="next(2);">下一步</a>
</div>